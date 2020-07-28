package com.guangming.service;


import com.guangming.configbeans.FeignConfig;
import com.guangming.pojo.Product;
import com.guangming.service.impl.IProductFeignServiceImpl;
import com.guangming.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ORANGE-PROVIDER-PRODUCT", configuration = FeignConfig.class, fallbackFactory = IProductFeignServiceImpl.class)
public interface IProductFeignService {
    //查询所有或特定产品
    @PostMapping("/product")
    Result queryProducts(@RequestBody Product product);

    //查询单个产品
    @GetMapping("/products/{id}")
    Result queryProducts(@PathVariable("id") String id);

}
