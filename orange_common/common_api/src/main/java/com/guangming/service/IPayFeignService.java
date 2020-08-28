package com.guangming.service;

import com.guangming.configbeans.FeignConfig;
import com.guangming.pojo.Orders;
import com.guangming.pojo.Wallet;
import com.guangming.service.impl.IUserFeignServiceImpl;
import com.guangming.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "ORANGE-PROVIDER-PAY", configuration = FeignConfig.class, fallbackFactory = IUserFeignServiceImpl.class)
public interface IPayFeignService {
    //创建订单
    @PostMapping("/orders")
    Result createOrder(@RequestBody Orders orders);

    //获取所有订单，管理员才能访问
    @GetMapping("/orders")
    Result query(Orders order);

    //获取用户相应的订单
    @GetMapping("/orders/user/{userId}/{orderId}")
    Result query(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId);

    //跳转支付宝支付页面
    @GetMapping(value = "/pay/alipay/{orderId}", produces = "text/html; charset=UTF-8")
    String pay(@PathVariable("orderId") String orderId);

    //获取支付订单状态
    @GetMapping(value = "/orders/pay/{orderId}")
    Result payOrder(@PathVariable("orderId") String orderId);

    //更新订单
    @PutMapping("/orders")
    Result update(Orders order);


    //更新订单
    @PutMapping("/orders/{orderId}")
    Result cancelOrder(@PathVariable("orderId") String id);

    //删除用户相应的订单
    @DeleteMapping("/orders/user/{userId}/{orderId}")
    Result delete(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId);

    //支付宝他同步通知
    @GetMapping("/pay/returnNotice")
    Result return_notice();

    //支付宝异步通知
    @PostMapping("/pay/notifyNotice")
    String alipayNotifyNotice();


    //查询钱包信息，用户Id
    @GetMapping("/wallet/{userId}/info")
    Result getWalletInfo(@PathVariable("userId") String userId);


    @PutMapping("/wallet")
    Result updateWallet(@RequestBody Wallet wallet);
}
