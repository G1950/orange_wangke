package com.guangming.configbeans;

import com.guangming.utils.EncryptionUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthFeignConfig implements RequestInterceptor {

    @Value("${client.id}")
    private String client_Id; //客户端id
    @Value("${client.secret}") //密钥
    private String client_secret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String authorization = client_Id + ":" + client_secret;
        //Base64编码
        System.out.println(authorization);
        String encode = EncryptionUtils.base64Encode(authorization);
        authorization = "Basic " + encode;
        System.out.println(authorization);
        requestTemplate.header("Authorization", authorization);
    }
}