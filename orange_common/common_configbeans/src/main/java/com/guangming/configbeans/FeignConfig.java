package com.guangming.configbeans;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null)
            throw new IllegalArgumentException();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader("Authorization");
        if (header == null) {
            header = (String) request.getSession().getAttribute("Authorization");
        }
        requestTemplate.header("Authorization", header);
    }
}