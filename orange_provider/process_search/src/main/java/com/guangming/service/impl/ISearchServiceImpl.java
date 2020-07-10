package com.guangming.service.impl;

import com.guangming.mapper.QuestionMapper;
import com.guangming.pojo.Question;
import com.guangming.service.ISearchService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ISearchServiceImpl
 * @Description 查题接口具体实现
 * @Author Gm
 * @Date 2020/6/28/028 10:14
 * @Version 1.0
 **/

@Slf4j
@Service
public class ISearchServiceImpl implements ISearchService {

    @Value("${solr.type}")
    private String type;
    @Value("${solr.entity}")
    private String entity;

    @Resource
    private SolrClient client;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public Result save(List<Question> questions) {
        try {
            List<Question> list = new ArrayList<>();
            for (Question question : questions) {
                Result result = search(question.getProblem());
                if ((result.getData() == null) || result.getCode() == 1 )
                    continue;
                list.add(question);
            }
            questionMapper.save(list);
            return Result.build(ResultEnum.SAVE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.SAVE_ERROR);
        }


    }

    @Override
    public Result update(List<Question> questions) {
        try {
            for (Question question : questions) {
                questionMapper.update(question);
            }
            return Result.build(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.UPDATE_ERROR);
        }

    }

    @Transactional
    @Override
    public Result deleteById(List<Integer> id) {
        try {
            questionMapper.deleteById(id);
            return Result.build(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }

    @Override
    public Result query(String query) {
        try {
            return Result.build(ResultEnum.QUERY_SUCCESS, questionMapper.query(query));
        } catch (Exception e) {
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result queryById(Integer id) {
        try {
            return Result.build(ResultEnum.QUERY_SUCCESS, questionMapper.queryById(id));
        } catch (Exception e) {
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result search(String problem) {
        try {
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.add("q", problem); //需要查询的问题，模糊查询
            params.add("start", "0"); //起始位置偏移量
            params.add("rows", "1");    //显示查询到的题数
            QueryResponse query = client.query(params);
            SolrDocumentList results = query.getResults();
            if (results.getNumFound() != 0) {
                List<Question> lists = new ArrayList<>();
                for (SolrDocument lis : results) {
                    Question question = new Question();
                    question.setId(Integer.valueOf((String) lis.get("id")));
                    question.setProblem((String) lis.get("wangke_problem"));
                    question.setXx((String) lis.get("wangke_xx"));
                    question.setAnswer((String) lis.get("wangke_answer"));
                    lists.add(question);
                }
                log.info("查题成功");
                return Result.build(ResultEnum.QUERY_SUCCESS, lists);
            } else {
                log.info("查无此题：" + problem);
                return Result.build(ResultEnum.QUERY_NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("查题异常：" + e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void autoQuartzUpdate() {
        try {
            if (!buildIndex(type))
                log.error("索引更新失败");
        } catch (Exception e) {
            log.error("索引更新失败：" + e.getMessage());
        }
    }

    @Override
    public Result quartzUpdate(String solrType) {
        try {
            type = solrType == null ? type : solrType;
            if (buildIndex(type))
                return Result.build(ResultEnum.UPDATE_SUCCESS);
            else
                return Result.build(ResultEnum.UPDATE_ERROR);
        } catch (Exception e) {
            log.error("索引更新失败：" + e.getMessage());
            return Result.build(ResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public Result getCount() {
        try {
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.add("q", "*:*"); //需要查询的问题，模糊查询
            QueryResponse query = client.query(params);
            return Result.build(ResultEnum.QUERY_SUCCESS, query.getResults().getNumFound());
        } catch (Exception e) {
            log.error("查询索引数量失败：" + e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    private Boolean buildIndex(String type) throws SolrServerException, IOException {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("commit", "true");
        params.add("optimize", "false");
        params.add("index", "false");
        params.add("entity", entity);
        params.add("debug", "false");
        params.add("wt", "json");
        type = type.toUpperCase();
        switch (type) {
            case "DELTA_IMPORT":    /*增量索引*/
                params.add("command", "delta-import");
                params.add("clean", "false");
                break;
            case "FULL_IMPORT":    /*重建索引*/
                params.add("command", "full-import");
                params.add("clean", "true");
                break;
        }
        SolrRequest<QueryResponse> request = new QueryRequest(params);
        request.setPath("/dataimport");
        client.request(request);
        log.info((type.equals("DELTA_IMPORT") ? "增量" : "全量") + "索引更新成功");
        return true;
    }
}
