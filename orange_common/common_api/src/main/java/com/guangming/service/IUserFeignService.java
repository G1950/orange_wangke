package com.guangming.service;

import com.guangming.configbeans.FeignConfig;
import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.service.impl.IUserFeignServiceImpl;
import com.guangming.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ORANGE-PROVIDER-USER", configuration = FeignConfig.class, fallbackFactory = IUserFeignServiceImpl.class)
public interface IUserFeignService {
    //添加用户
    @PostMapping("user/info")
    Result save(User user);

    //查询用户
    @GetMapping("user/info")
    Result query(User user);

    //修改用户
    @PutMapping("user/info")
    Result update(@RequestBody User user);


    //删除用户
    @DeleteMapping("user/info")
    Result delete(@RequestBody List<String> id);


    //添加权限用户,用户注册第一步
    @PostMapping("user/auth")
    Result save(@RequestBody Authority authority);

    //修改权限用户
    @PutMapping("user/auth")
    Result update(@RequestBody Authority authority);

    //查询权限用户
    @PostMapping("user/auths")
    Result query(@RequestBody Authority authority);

    //根据account,password查询
    @PostMapping("user")
    Result queryByAccountAndPassword(@RequestParam("account") String account, @RequestParam("password") String password);

    //删除权限用
    @DeleteMapping("user/auth")
    Result deleteById(@RequestBody List<String> id);

    //查询用户
    @GetMapping("user/info/{id}")
    Result queryById(@PathVariable("id") String id);
}
