package com.guangming.service;

import com.guangming.pojo.User;
import com.guangming.utils.Result;

import java.util.List;

//用户接口
public interface IUserService {

    //添加用户信息
    Result saveUserInfo(User user);

    //修改用户信息
    Result updateUserInfo(User user);

    //查询用户信息
    Result queryUserInfoById(String id);

    //查询用户信息
    Result queryUserInfo(User user);

    //删除用户信息
    Result deleteUserInfoById(List<String> id);
}
