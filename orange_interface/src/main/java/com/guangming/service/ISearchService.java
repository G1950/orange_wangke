package com.guangming.service;

import com.guangming.pojo.Question;
import com.guangming.utils.Result;

import java.util.List;

//题目接口
public interface ISearchService {
    //添加题目
    Result save(List<Question> question);

    //修改题目
    Result update(List<Question> question);

    //删除题目
    Result deleteById(List<Integer> id);

    //查询题目
    Result query(String query);

    //根据id查询
    Result queryById( Integer id);

    /*通过索引操作*/

    //题目查询
    Result search(String problem);

    //定时任务更新索引
    void autoQuartzUpdate();

    //手动更新索引
    Result quartzUpdate(String solrType);

    //获取索引数量
    Result getCount();

}
