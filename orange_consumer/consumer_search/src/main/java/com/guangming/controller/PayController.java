package com.guangming.controller;

import com.guangming.pojo.Orders;
import com.guangming.service.IPayFeignService;
import com.guangming.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName PayController
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/26/026 10:17
 * @Version 1.0
 **/
@RestController
public class PayController {
    @Autowired
    private IPayFeignService payFeignService;

    //创建订单
    @PostMapping("/orders")
    Result createOrder(@RequestBody Orders orders) {
        return payFeignService.createOrder(orders);
    }

    //获取所有订单，管理员才能访问
    @GetMapping("/orders")
    Result query(Orders order) {
        return null;
    }

    //获取用户相应的订单
    @GetMapping("/orders/user/{userId}/{orderId}")
    Result query(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId) {
        return null;
    }

    //跳转支付宝支付页面
    @GetMapping(value = "/pay/alipay/{orderId}", produces = "text/html; charset=UTF-8")
    String pay(@PathVariable("orderId") String orderId) {
        return payFeignService.pay(orderId);
    }

    //获取支付订单状态
    @GetMapping(value = "/orders/pay/{orderId}")
    Result payOrder(@PathVariable("orderId") String orderId) {
        return null;
    }

    //更新订单
    @PutMapping("/orders")
    Result update(Orders order) {
        return null;
    }


    //删除用户相应的订单
    @DeleteMapping("/orders/user/{userId}/{orderId}")
    Result delete(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId) {
        return null;
    }

    //查询钱包信息，用户Id
    @GetMapping("/wallet/{userId}/info")
    Result getWalletInfo(@PathVariable("userId") String userId) {
        return null;
    }
}
