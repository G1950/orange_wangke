package com.guangming.service;

import com.guangming.configbeans.FeignConfig;
import com.guangming.service.impl.IUserFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "ORANGE-PROVIDER-USER", configuration = FeignConfig.class, fallbackFactory = IUserFeignServiceImpl.class)
public interface IUserFeignService {

}
