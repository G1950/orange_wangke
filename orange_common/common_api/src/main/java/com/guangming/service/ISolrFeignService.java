package com.guangming.service;


import com.guangming.pojo.Question;
import com.guangming.service.impl.ISolrFeignServiceImpl;
import com.guangming.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ORANGE-PROVIDER-SEARCH", fallbackFactory = ISolrFeignServiceImpl.class)
public interface ISolrFeignService {

    //添加题目
    @PostMapping("/tm")
    Result save(@RequestBody List<Question> questions);


    //查询题目
    @GetMapping("/tm/type/{types}")
    Result query(@PathVariable("types") Integer type, Question question);

    //修改题目
    @PutMapping("/tm")
    Result update(@RequestBody List<Question> questions);

    //删除题目
    @DeleteMapping("/tm")
    Result delete(@RequestBody List<Integer> id);

    //通过索引查询题目
    @GetMapping("/tm/index/{tm}")
    Result indexQuery(@PathVariable("tm") String tm);

    //更新索引,全量更新，增量更新
    @GetMapping("/tm/index/types/{types}")
    Result indexUpdate(@PathVariable("types") String type);

    //查询索引数量
    @GetMapping("/tm/index/count")
    Result indexAccount();
}
