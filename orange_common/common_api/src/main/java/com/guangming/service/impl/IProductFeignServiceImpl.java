package com.guangming.service.impl;

import com.guangming.pojo.Product;
import com.guangming.service.IProductFeignService;
import com.guangming.utils.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName IProductFeignServiceImpl
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/27/027 9:51
 * @Version 1.0
 **/
@Component
public class IProductFeignServiceImpl implements FallbackFactory<IProductFeignService> {
    @Override
    public IProductFeignService create(Throwable throwable) {
        throwable.printStackTrace();
        return new IProductFeignService() {

            @Override
            public Result queryProducts(Product product) {
                return null;
            }

            @Override
            public Result queryProducts(String id) {
                return null;
            }

        };
    }
}
