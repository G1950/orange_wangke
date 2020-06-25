package com.guangming.service;


import com.guangming.service.impl.ISolrFeignServiceImpl;
import com.guangming.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ORANGE-PROVIDER-SOLR", fallbackFactory = ISolrFeignServiceImpl.class)
public interface ISolrFeignService {

    //post请求查询
    @PostMapping("/api/cx")
    Result postSearch(@RequestParam("problem") String problem, @RequestParam("rows") Integer rows);

    //get请求查询
    @GetMapping("/api/cx/{problem}")
    Result getSearch(@PathVariable("problem") String problem);

    //手动更新索引
    @PostMapping("/api/index")
    Result quartzUpdate(@RequestParam("solrType") String solrType);

    //获取索引数量
    @GetMapping("/api/count")
    Result getCount();
}
