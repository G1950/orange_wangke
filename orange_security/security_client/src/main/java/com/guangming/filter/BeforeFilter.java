package com.guangming.filter;

import com.alibaba.fastjson.JSONObject;
import com.guangming.utils.IpUtils;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class BeforeFilter extends OncePerRequestFilter {

    @Value("#{'${white.list}'.split(',')}")
    private List<String> list;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        //判断是否有token
        String access_token = (String) request.getSession().getAttribute("Authorization");
        String refreshToken = (String) request.getSession().getAttribute("RefreshToken");
        access_token = access_token == null ? request.getHeader("Authorization") : access_token;
        refreshToken = refreshToken == null ? request.getHeader("RefreshToken") : refreshToken;

        boolean ajax = false;
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            ajax = true;
        }
        //判断白名单
        if (UrlUtils(uri)) {
            filterChain.doFilter(request, response);
        } else {

            if ((uri.equals("/search/cx") || uri.contains("/tm/index/")) && (access_token == null || !access_token.startsWith("Bearer"))) {
                String ip = IpUtils.getIpAddr(request);
                //判断redis是否存在数据
                Integer integer = redisTemplate.opsForValue().get(("ip:" + ip));
                if (integer == null && uri.equals("/search/cx"))  //不存在数据
                {
                    redisTemplate.opsForValue().set(("ip:" + ip), 20);
                    filterChain.doFilter(request, response);
                } else if (integer != null && integer == 0 && uri.equals("/search/cx")) {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(JSONObject.toJSONString(Result.build(ResultEnum.NOT_ENOUGH_MONENY)));
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                if (access_token == null || !access_token.startsWith("Bearer")) {
                    if (ajax) {
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(JSONObject.toJSONString(Result.build(ResultEnum.UNAUTHORIZED_ACCESS)));
                    } else {
                        response.sendRedirect("/");
                    }
                } else {
                    HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
                    requestWrapper.addHeader("Authorization", access_token);
                    filterChain.doFilter(requestWrapper, response);
                }
            }
        }
    }

    private Boolean UrlUtils(String url) {
        if (url.equals("/"))
            return true;
        for (String s : list) {
            if (url.contains(s))
                return true;
        }
        return false;
    }

    @Slf4j
    public static class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
        /**
         * construct a wrapper for this request
         *
         * @param request
         */
        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        private Map<String, String> headerMap = new HashMap<>();

        /**
         * add a header with given name and value
         *
         * @param name
         * @param value
         */
        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            log.info("getHeader --->{}", name);
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }

        /**
         * get the Header names
         */
        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            names.addAll(headerMap.keySet());
            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            log.info("getHeaders --->>>>>>{}", name);
            List<String> values = Collections.list(super.getHeaders(name));
            log.info("getHeaders --->>>>>>{}", values);
            if (headerMap.containsKey(name)) {
                log.info("getHeaders --->{}", headerMap.get(name));
                values = Collections.singletonList(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }
    }
}