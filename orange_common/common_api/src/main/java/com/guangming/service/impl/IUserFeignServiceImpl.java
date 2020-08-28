package com.guangming.service.impl;


import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.service.IUserFeignService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IUserFeignServiceImpl implements FallbackFactory<IUserFeignService> {
    @Override
    public IUserFeignService create(Throwable throwable) {
        throwable.printStackTrace();
        return new IUserFeignService() {
            @Override
            public Result save(User user) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result query(User user) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result update(User user) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result delete(List<String> id) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result save(Authority authority) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result update(Authority authority) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result query(Authority authority) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result queryByAccountAndPassword(String account, String password) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result deleteById(List<String> id) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result queryById(String id) {
                return Result.build(ResultEnum.SYS_ERROR);
            }
        };
    }


}
