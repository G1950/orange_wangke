package com.guangming.service;

import com.guangming.pojo.Orders;
import com.guangming.pojo.Wallet;
import com.guangming.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//订单接口
public interface IPayService {

    //创建订单
    Result createOrder(Orders orders);

    //查询订单，管理员
    Result query(Orders order);

    //查询订单，根据订单号
    Result queryById(String orderId);

    //查询订单，用户id，订单号
    Result queryByUserIdOrderId(String userId, String orderId);

    //修改订单，在订单未完成前均可修改
    Result update(Orders order);

    //删除订单
    Result deleteByUserIdOrderId(String userId, String orderId);

    //支付宝同步通知
    Result returnNotice(HttpServletRequest request);

    //支付宝异步通知
    String notifyNotice(HttpServletRequest request);

    //跳转支付宝支付页面
    String getAliPay(String orderId);


    //查询钱包信息，根据userId
    Result queryWalletByUserId(String userId);

    //删除钱包信息，用户注销时用到
    Result deleteWalletByUserId(String userId);
}
