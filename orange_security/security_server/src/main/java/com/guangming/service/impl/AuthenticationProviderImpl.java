package com.guangming.service.impl;

import com.guangming.pojo.Authority;
import com.guangming.service.IOauthService;
import com.guangming.utils.EncryptionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Resource
    private IOauthService oauthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //判断是否输入账号密码
        if (authentication.getName() == null || authentication.getCredentials() == null)
            throw new NullPointerException("请输入账号密码！");
        String username = authentication.getName();
        String password = EncryptionUtils.md5Hex(authentication.getCredentials().toString()); //md5加密
        UserDetailsService userDetailsService = account -> {
            // 通过用户名获取用户信息
            Authority oauth = oauthService.getAuthorityInfo(account, password);
            if (oauth != null) {
                //创建spring security安全用户和对应的权限（从数据库查找）
                if (oauth.getStatus() == 0) {
                    throw new BadCredentialsException("帐号已锁定！");
                }
                return new User(oauth.getAuthority_id(), oauth.getAuthority_pass(),
                        AuthorityUtils.createAuthorityList(oauth.getAuthority_role()));
            } else {
                throw new BadCredentialsException("用户账号不存在或密码错误！");
            }
        };
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}