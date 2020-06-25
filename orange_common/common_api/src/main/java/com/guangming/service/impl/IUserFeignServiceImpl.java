package com.guangming.service.impl;


import com.guangming.service.IUserFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class IUserFeignServiceImpl implements FallbackFactory<IUserFeignService> {
    @Override
    public IUserFeignService create(Throwable throwable) {
        return null;
    }


}
