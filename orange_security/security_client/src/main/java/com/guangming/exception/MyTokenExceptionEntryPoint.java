package com.guangming.exception;

import com.alibaba.fastjson.JSONObject;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MyTokenExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        log.error("授权异常：" + e.getMessage());
        e.printStackTrace();
        System.out.println(httpServletRequest.getRequestURL());
        System.out.println(httpServletRequest.getHeader("Authorization"));
        if (httpServletRequest.getHeader("Authorization") == null)
            httpServletResponse.getWriter().write(JSONObject.toJSONString(Result.build(ResultEnum.UNAUTHORIZED_ACCESS)));
        else
            httpServletResponse.getWriter().write(JSONObject.toJSONString(Result.build(ResultEnum.ACCESS_TOKEN_ERROR)));
    }
}