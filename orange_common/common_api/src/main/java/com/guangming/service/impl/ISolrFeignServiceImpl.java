package com.guangming.service.impl;

import com.guangming.service.ISolrFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ISolrFeignServiceImpl implements FallbackFactory<ISolrFeignService> {
    @Override
    public ISolrFeignService create(Throwable throwable) {
        return null;
    }
}
