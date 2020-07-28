package com.guangming.controller;

import com.alibaba.fastjson.JSON;
import com.guangming.pojo.Orders;
import com.guangming.pojo.Product;
import com.guangming.service.IPayFeignService;
import com.guangming.service.IProductFeignService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * @ClassName ProductController
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/27/027 9:49
 * @Version 1.0
 **/
@Controller
public class ProductController {

    @Autowired
    private IProductFeignService productFeignService;
    @Autowired
    private IPayFeignService payFeignService;


    //查询所有或特定产品
    @GetMapping("/shop/{id}")
    public String queryProducts(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", id);
        Product product = new Product();
        Result result = productFeignService.queryProducts(product);

        model.addAttribute("products", result.getData());
        return "product";
    }

    //查询产品
    @GetMapping("/shop/product/{id}")
    @ResponseBody
    public Result queryProduct(@PathVariable("id") String productId, @RequestParam("nums") Integer nums, Model model, HttpSession session) {
        try {
            String id = (String) session.getAttribute("id");
            if (id == null)
                return Result.build(ResultEnum.ILLEGAL_OPTIONS);
            Result result = productFeignService.queryProducts(productId);
            if (result.getData() == null)
                return result;
            String jsonString = JSON.toJSONString(result.getData());
            Product product = JSON.toJavaObject(JSON.parseObject(jsonString), Product.class);
            /*订单*/
            Orders order = new Orders();
            order.setUser_id(id);
            order.setProduct_id(productId);
            order.setDiscount(product.getDiscount());
            order.setNums(nums);
            order.setPrice(product.getPrice());
            BigDecimal discount = product.getDiscount();
            BigDecimal price = product.getPrice();
            BigDecimal multiply = price.multiply(BigDecimal.valueOf(nums));
            order.setReal_price(multiply.multiply(discount));
            order.setStatus(0);
            /*订单*/
            Result orders = payFeignService.createOrder(order);
            System.out.println(productId);
            return orders.getData() == null ? Result.build(ResultEnum.ORDER_CREATE_FAIL) : Result.build(ResultEnum.ORDER_CREATE_SUCCESS, "/shop/" + id + "/" + orders.getData());
        } catch (Exception e) {
            return Result.build(ResultEnum.ORDER_CREATE_FAIL);
        }
    }

    //查询所有或特定产品
    @GetMapping("/shop/{userId}/{id}")
    public String queryOrder(@PathVariable("userId") String userId, @PathVariable("id") String id, Model model) {
        Result result = payFeignService.payOrder(id);
        model.addAttribute("order", result.getData());
        model.addAttribute("userId", userId);
        return "order";
    }

    //跳转支付宝支付页面
    @GetMapping(value = "/shop/alipay/{orderId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String pay(@PathVariable("orderId") String orderId) {
        return payFeignService.pay(orderId);
    }

    //取消订单
    @GetMapping(value = "/shop/cancel/{orderId}")
    @ResponseBody
    public Result payCancel(@PathVariable("orderId") String orderId) {
        return payFeignService.cancelOrder(orderId);
    }
}
