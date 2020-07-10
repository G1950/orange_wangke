package com.guangming.service.impl;

import com.guangming.mapper.AuthorityMapper;
import com.guangming.mapper.UserMapper;
import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.service.IOauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class IOauthServiceImpl implements IOauthService {
    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private UserMapper userMapper;


    @Override
    public Authority getAuthorityInfo(String account, String password) {
        try {
            return authorityMapper.findAuthorityInfoByAccountPwd(account, password);
        } catch (Exception e) {
            log.error("获取权限信息异常：" + e.getMessage());
            return null;
        }

    }

    @Override
    public Authority getAuthorityInfoById(String id) {
        try {
            return authorityMapper.findAuthorityInfoById(id);
        } catch (Exception e) {
            log.error("获取权限信息异常：" + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserInfo(String id) {
        try {
            return userMapper.findUserInfoById(id);
        } catch (Exception e) {
            log.error("获取用户信息异常：" + e.getMessage());
            return null;
        }

    }
}
