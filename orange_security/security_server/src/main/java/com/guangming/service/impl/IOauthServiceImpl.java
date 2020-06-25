package com.guangming.service.impl;

import com.guangming.mapper.AuthorityMapper;
import com.guangming.mapper.UserMapper;
import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.service.IOauthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IOauthServiceImpl implements IOauthService {
    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private UserMapper userMapper;


    @Override
    public Authority getAuthorityInfo(String account, String password) {
        return authorityMapper.findAuthorityInfoByAccountPwd(account, password);
    }

    @Override
    public User getUserInfo(String id) {
        return userMapper.findUserInfoById(id);
    }
}
