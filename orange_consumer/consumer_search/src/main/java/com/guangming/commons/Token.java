package com.guangming.commons;

import com.guangming.client.AuthUtils;
import com.guangming.pojo.User;
import com.guangming.service.IAuthServerFeignService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

public class Token {

    public static Map<String, String> getToken(@RequestParam("account") String account, @RequestParam("password") String password, IAuthServerFeignService authServerFeignService) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("username", account);
        parameter.put("password", password);
        parameter.put("grant_type", "password");
        ResponseEntity<OAuth2AccessToken> token = authServerFeignService.getToken(parameter);
        String access_token;
        String refreshToken;
        OAuth2AccessToken tokenBody = token.getBody();
        if (tokenBody != null) {
             access_token = tokenBody.getValue();
            System.out.println(access_token);
            if (access_token == null || access_token.isEmpty())
                throw new AccessDeniedException("请检查账号密码");
            if (access_token.contains("401")) {
                throw new AccessDeniedException("未经授权或授权失败！");
            }
            if (access_token.contains("500")) {
                throw new RuntimeException("服务器繁忙，请稍后重试");
            }
            User user = AuthUtils.getUser(access_token);
            System.out.println(user.getNickname());
            if (user == null) {
                throw new RuntimeException("服务器繁忙，请稍后重试");
            }
            OAuth2RefreshToken refresh_token = tokenBody.getRefreshToken();
            refreshToken = refresh_token.getValue();
        } else {
            throw new AccessDeniedException("未经授权或授权失败！");
        }
        parameter.clear();
        parameter.put("Authorization", "Bearer " + access_token);
        parameter.put("RefreshToken", refreshToken);
        return parameter;
    }
}
