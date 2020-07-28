package com.guangming.controller;

import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.service.IAuthorityService;
import com.guangming.service.IUserService;
import com.guangming.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IAuthorityService authorityService;

    //添加用户
    @PostMapping("/info")
    public Result save(User user) {
        return userService.saveUserInfo(user);
    }

    //查询用户
    @GetMapping("/info/{id}")
    public Result queryById(@PathVariable("id") String id) {
        return userService.queryUserInfoById(id);
    }

    //查询用户
    @GetMapping("/info")
    public Result query(User user) {
        return userService.queryUserInfo(user);
    }

    //修改用户
    @PutMapping("/info")
    public Result update(User user) {
        return userService.updateUserInfo(user);
    }


    //删除用户
    @DeleteMapping("/info")
    public Result delete(@RequestBody List<String> id) {
        return userService.deleteUserInfoById(id);
    }


    //添加权限用户,用户注册第一步
    @PostMapping("/auth")
    public Result save(Authority authority) {
        return authorityService.save(authority);
    }

    //修改权限用户
    @PutMapping("/auth")
    public Result update(Authority authority) {
        return authorityService.update(authority);
    }

    //查询权限用户
    @GetMapping("/auth")
    public Result query(Authority authority) {
        return authorityService.query(authority);
    }

    //根据account,password查询
    @PostMapping("")
    public Result queryByAccountAndPassword(@RequestParam("account") String account, @RequestParam("password") String password) {
        return authorityService.queryByAccountAndPassword(account, password);
    }

    //删除权限用
    @DeleteMapping("/auth")
    public Result deleteById(@RequestBody List<String> id) {
        return authorityService.deleteById(id);
    }


}
