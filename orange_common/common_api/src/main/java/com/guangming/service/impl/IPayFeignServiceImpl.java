package com.guangming.service.impl;

import com.guangming.pojo.Orders;
import com.guangming.service.IPayFeignService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName IPayFeignServiceImpl
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/24/024 9:39
 * @Version 1.0
 **/
@Component
public class IPayFeignServiceImpl implements FallbackFactory<IPayFeignService> {
    @Override
    public IPayFeignService create(Throwable throwable) {
        return new IPayFeignService() {
            @Override
            public Result createOrder(Orders orders) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result query(Orders order) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result query(String userId, String orderId) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public String pay(String orderId) {
                return null;
            }

            @Override
            public Result payOrder(String orderId) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result update(Orders order) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result cancelOrder(String id) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result delete(String userId, String orderId) {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public Result return_notice() {
                return Result.build(ResultEnum.SYS_ERROR);
            }

            @Override
            public String alipayNotifyNotice() {
                return null;
            }

            @Override
            public Result getWalletInfo(String userId) {
                return Result.build(ResultEnum.SYS_ERROR);
            }
        };
    }
}
