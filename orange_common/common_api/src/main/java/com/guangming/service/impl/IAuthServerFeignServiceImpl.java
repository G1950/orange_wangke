package com.guangming.service.impl;

import com.guangming.service.IAuthServerFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
public class IAuthServerFeignServiceImpl implements FallbackFactory<IAuthServerFeignService> {
    @Override
    public IAuthServerFeignService create(Throwable throwable) {
        return parameters -> {
            throwable.printStackTrace();
            String exception = throwable.getMessage();
            OAuth2AccessToken accessToken = new OAuth2AccessToken() {
                @Override
                public Map<String, Object> getAdditionalInformation() {
                    return null;
                }

                @Override
                public Set<String> getScope() {
                    return null;
                }

                @Override
                public OAuth2RefreshToken getRefreshToken() {
                    return null;
                }

                @Override
                public String getTokenType() {
                    return null;
                }

                @Override
                public boolean isExpired() {
                    return false;
                }

                @Override
                public Date getExpiration() {
                    return null;
                }

                @Override
                public int getExpiresIn() {
                    return 0;
                }

                @Override
                public String getValue() {
                    String msg;
                    if (exception == null || exception.contains("401")) {
                        msg = "401";
                    } else {
                        msg = "500";
                    }
                    return msg;
                }
            };
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cache-Control", "no-store");
            headers.set("Pragma", "no-cache");
            headers.set("Content-Type", "application/json;charset=UTF-8");
            return new ResponseEntity(accessToken, headers, HttpStatus.OK);
        };
    }
}
