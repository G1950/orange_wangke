package com.guangming.service;

import com.guangming.configbeans.AuthFeignConfig;
import com.guangming.service.impl.IAuthServerFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "ORANGE-SECURITY-SERVER", configuration = AuthFeignConfig.class, fallbackFactory = IAuthServerFeignServiceImpl.class)
public interface IAuthServerFeignService {

    @PostMapping("/oauth/token")
    ResponseEntity<OAuth2AccessToken> getToken(@RequestParam Map<String, String> parameters);
}
