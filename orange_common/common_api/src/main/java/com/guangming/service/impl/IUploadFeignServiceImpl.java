package com.guangming.service.impl;

import com.guangming.service.IUploadFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class IUploadFeignServiceImpl implements FallbackFactory<IUploadFeignService> {

    @Override
    public IUploadFeignService create(Throwable throwable) {
        return null;
    }
}
