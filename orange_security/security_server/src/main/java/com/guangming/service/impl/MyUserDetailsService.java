package com.guangming.service.impl;

import com.guangming.pojo.Authority;
import com.guangming.service.IOauthService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName MyUserDetailsService
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/8/008 10:11
 * @Version 1.0
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private IOauthService oauthService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 通过id获取用户信息
        Authority oauth = oauthService.getAuthorityInfoById(id);
        if (oauth != null) {
            oauth = oauthService.getAuthorityInfo(oauth.getAuthority_account(), oauth.getAuthority_pass());
            if (oauth == null)
                throw new BadCredentialsException("用户账号不存在或密码错误！");
            //创建spring security安全用户和对应的权限（从数据库查找）
            if (oauth.getStatus() == 0) {
                throw new BadCredentialsException("帐号已锁定！");
            }
            return new User(oauth.getAuthority_id(), oauth.getAuthority_pass(),
                    AuthorityUtils.createAuthorityList(oauth.getAuthority_role()));
        } else {
            throw new BadCredentialsException("用户账号不存在或密码错误！");
        }
    }
}
