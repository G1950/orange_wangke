package com.guangming.controller;

import com.guangming.pojo.Orders;
import com.guangming.service.IPayService;
import com.guangming.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName PayController
 * @Description 订单
 * @Author Gm
 * @Date 2020/7/4/004 22:13
 * @Version 1.0
 **/
@Slf4j
@RestController
public class PayController {

    @Resource
    private IPayService payService;


    //创建订单
    @PostMapping("/orders")
    public Result createOrder(@RequestBody Orders orders) {
        return payService.createOrder(orders);
    }

    //获取所有订单，管理员才能访问
    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result query(Orders order) {
        return payService.query(order);
    }

    //获取用户相应的订单
    @GetMapping("/orders/user/{userId}/{orderId}")
    public Result query(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId) {
        return payService.queryByUserIdOrderId(userId, orderId);
    }

    //跳转支付宝支付页面
    @GetMapping(value = "/pay/alipay/{orderId}", produces = "text/html; charset=UTF-8")
    public String pay(@PathVariable("orderId") String orderId) {
        return payService.getAliPay(orderId);
    }

    //获取订单
    @GetMapping(value = "/orders/pay/{orderId}")
    public Result payOrder(@PathVariable("orderId") String orderId) {
        return payService.queryById(orderId);
    }

    //更新订单
    @PutMapping("/orders")
    public Result update(Orders order) {
        return payService.update(order);
    }

    //取消订单
    @PutMapping("/orders/{orderId}")
    public Result cancelOrder(@PathVariable("orderId") String id) {
        return payService.cancelOrder(id);
    }


    //删除用户相应的订单
    @DeleteMapping("/orders/user/{userId}/{orderId}")
    public Result delete(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId) {
        return payService.deleteByUserIdOrderId(userId, orderId);
    }

    //支付宝他同步通知
    @GetMapping("/pay/returnNotice")
    public Result return_notice(HttpServletRequest request) {
        return payService.returnNotice(request);
    }

    //支付宝异步通知
    @PostMapping("/pay/notifyNotice")
    public String alipayNotifyNotice(HttpServletRequest request) {
        return payService.notifyNotice(request);
    }

    //查询钱包信息，用户Id
    @GetMapping("/wallet/{userId}/info")
    public Result getWalletInfo(@PathVariable("userId") String userId){
        return payService.queryWalletByUserId(userId);
    }

}
