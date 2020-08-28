package com.guangming.service;

import com.guangming.pojo.Authority;
import com.guangming.utils.Result;

import java.util.List;

//权限接口
public interface IAuthorityService {

    //添加权限用户
    Result save(Authority authority) throws Exception;

    //修改权限用户
    Result update(Authority authority);

    //查询权限用户
    Result query(Authority authority);

    //根据account,password查询
    Result queryByAccountAndPassword(String account,String password);

    //删除权限用
    Result deleteById(List<String> id);

}
