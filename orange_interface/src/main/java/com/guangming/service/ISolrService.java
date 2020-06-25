package com.guangming.service;

import com.guangming.utils.Result;

//查题接口
public interface ISolrService {
    //题目查询
    Result search(String problem);

    //定时任务更新索引
    void autoQuartzUpdate();

    //手动更新索引
    Boolean quartzUpdate(String solrType);

    //获取索引数量
    Long getCount();
}
