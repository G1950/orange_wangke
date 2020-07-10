package com.guangming.controller;

import com.guangming.pojo.Question;
import com.guangming.service.ISearchService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SearchController
 * @Description 查题接口
 * @Author Gm
 * @Date 2020/6/28/028 10:20
 * @Version 1.0
 **/
@RestController
public class SearchController {
    @Resource
    private ISearchService searchService;

    //添加题目
    @PostMapping("/tm")
    public Result save(@RequestBody List<Question> questions) {
        return searchService.save(questions);
    }


    //查询题目
    @GetMapping("/tm/type/{types}")
    public Result query(@PathVariable("types") Integer type, Question question) {
        if (type == 1)
            return searchService.queryById(question.getId());
        if (type == 0)
            return  searchService.query(question.getProblem());
        else
            return Result.build(ResultEnum.QUERY_TYPE_ERROR);
    }

    //修改题目
    @PutMapping("/tm")
    public Result update(@RequestBody List<Question> questions) {
        return searchService.update(questions);
    }

    //删除题目
    @DeleteMapping("/tm")
    public Result delete(@RequestBody List<Integer> id) {
        return searchService.deleteById(id);
    }

    //通过索引查询题目
    @GetMapping("/tm/index/{tm}")
    public Result indexQuery(@PathVariable("tm") String tm) {
        return searchService.search(tm);
    }

    //更新索引,全量更新，增量更新
    @GetMapping("/tm/index/types/{types}")
    public Result indexUpdate(@PathVariable("types") String type) {
        return searchService.quartzUpdate(type);
    }

    //查询索引数量
    @GetMapping("/tm/index/count")
    public Result indexAccount() {
        return searchService.getCount();
    }
}
