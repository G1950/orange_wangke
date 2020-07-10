package com.guangming.controller;

import com.guangming.pojo.OauthClientDetails;
import com.guangming.service.IOauthClientDetailsService;
import com.guangming.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName OauthController
 * @Description 资源接口
 * @Author Gm
 * @Date 2020/7/4/004 0:15
 * @Version 1.0
 **/
@RestController
public class OauthController {
    @Resource
    private IOauthClientDetailsService oauthClientDetailsService;


    @PostMapping("/oauth")
    public Result save(OauthClientDetails oauth){
      return  oauthClientDetailsService.save(oauth);
    }
    @DeleteMapping("/oauth/{id}")
    public Result deleteById(@PathVariable("id")String id){
        return  oauthClientDetailsService.deleteById(id);
    }
    @PutMapping("/oauth")
    public Result update(OauthClientDetails oauth){
        return  oauthClientDetailsService.update(oauth);
    }
    @GetMapping("/oauth")
    public Result query(OauthClientDetails oauth){
        return  oauthClientDetailsService.query(oauth);
    }
    @GetMapping("/oauth/{id}")
    public Result queryById(@PathVariable("id") String id){
        return  oauthClientDetailsService.queryById(id);
    }
}
