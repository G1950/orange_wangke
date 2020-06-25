package com.guangming.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guangming.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName AuthUtils
 * @Description 通过Jwt获取用户信息工具
 * @Author Gm
 * @Date 2020/6/24/024 15:50
 * @Version 1.0
 **/
public class AuthUtils {

    public static User getReqUser(String header) {
        if (header == null || !header.startsWith("Bearer")) {
            return null;
        }
        String token = StringUtils.substringAfter(header, "Bearer");    /*截取token*/
        return getUser(token);
    }

    public static User getUser(String token) {
        Claims claims;
        try {
            //解密
            claims = Jwts.parser().setSigningKey("SigningKey".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        // 拿到当前用户，返回用户信息
        JSONObject jsonObject = JSON.parseObject((String) claims.get("userInfo"));
        return JSONObject.toJavaObject(jsonObject, User.class);
    }
}