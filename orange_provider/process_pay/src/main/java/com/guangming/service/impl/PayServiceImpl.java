package com.guangming.service.impl;

import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.guangming.config.AlipayConfig;
import com.guangming.mapper.OrdersMapper;
import com.guangming.mapper.ProductMapper;
import com.guangming.mapper.UserMapper;
import com.guangming.mapper.WalletMapper;
import com.guangming.pojo.Orders;
import com.guangming.pojo.Product;
import com.guangming.pojo.User;
import com.guangming.pojo.Wallet;
import com.guangming.service.IPayService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import com.guangming.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ClassName PayServiceImpl
 * @Description 订单接口
 * @Author Gm
 * @Date 2020/7/4/004 22:23
 * @Version 1.0
 **/
@Slf4j
@Service
public class PayServiceImpl implements IPayService {
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private WalletMapper walletMapper;

    @Transactional
    @Override
    public Result createOrder(Orders order) {
        try {

            //订单编号
            String orderId = "order_" + SnowFlake.nextId();
            User user = userMapper.findUserInfoById(order.getUser_id());
            Product product = productMapper.queryById(order.getProduct_id());

            //判断用户，产品是否存在
            if (user == null || product == null) {
                log.error("用户或产品不存在");
                return Result.build(ResultEnum.ORDER_ILLEGAL);
            }
            //库存
            if (product.getNums() < order.getNums()) {
                log.error("产品库存不足");
                return Result.build(ResultEnum.ORDER_PRODUCT_NOT_ENOUGH);
            }
            //价格
            if (product.getPrice().compareTo(order.getPrice()) != 0 || product.getDiscount().compareTo(order.getDiscount()) != 0) {
                log.error("价格不相等");
                return Result.build(ResultEnum.ORDER_ILLEGAL);
            }
            BigDecimal nums = new BigDecimal(Integer.toString(order.getNums()));
            BigDecimal Real_price = product.getPrice().multiply(order.getDiscount()).multiply(nums);
            Real_price = Real_price.setScale(2, BigDecimal.ROUND_HALF_UP); //保留两位小数

            //总价判断
            if (Real_price.compareTo(order.getReal_price()) != 0) {
                log.error("总价格异常：" + Real_price + "    " + order.getReal_price());
                return Result.build(ResultEnum.ORDER_ILLEGAL);
            }
            order.setCreate_time(new Date());
            order.setId(orderId);
            ordersMapper.save(order);

            //修改产品库存
            productMapper.updateById(order.getProduct_id(), order.getNums());
            log.info("订单创建成功: " + orderId);
            return Result.build(ResultEnum.ORDER_CREATE_SUCCESS);
        } catch (Exception e) {
            log.error("订单创建异常："+e.getMessage());
            return Result.build(ResultEnum.ORDER_CREATE_FAIL);
        }
    }

    @Override
    public Result query(Orders order) {
        try {
            List<Orders> orders = ordersMapper.query(order);
            log.info("订单查询成功ByOrder");
            return Result.build(ResultEnum.QUERY_SUCCESS, orders);
        } catch (Exception e) {
            log.error("订单查询异常ByOrder："+e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result queryById(String orderId) {
        try {
            Orders orders = ordersMapper.queryByOrderId(orderId);
            log.info("订单查询成功ByOrderId");
            return Result.build(ResultEnum.QUERY_SUCCESS, orders);
        } catch (Exception e) {
            log.error("订单查询异常："+e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result queryByUserIdOrderId(String userId, String orderId) {
        try {
            //判断用户是否存在
            User user = userMapper.findUserInfoById(userId);
            if (user == null)
            {
                log.error("订单查询失败ByUserIdOrderId：用户不存在");
                return Result.build(ResultEnum.NOT_EXIST_USER);
            }

            //判断用户是查询所有还是单个
            if (orderId.equals("0")) {
                List<Orders> list = ordersMapper.queryByUserId(userId);
                log.info("订单查询成功ByUserIdOrderId");
                return Result.build(ResultEnum.QUERY_SUCCESS, list);
            }

            //判断该订单号对应用户
            Orders order = ordersMapper.queryByOrderId(orderId);
            if (order == null || !(order.getUser_id().equals(userId)))
            {
                log.error("订单查询失败ByUserIdOrderId：订单不存在");
                return Result.build(ResultEnum.ORDER_NOT_EXIST);
            }
            //用户，订单都存在，查询单个订单
            Orders orders = ordersMapper.queryByOrderId(orderId);
            log.info("订单查询成功ByUserIdOrderId");
            return Result.build(ResultEnum.QUERY_SUCCESS, orders);
        } catch (Exception e) {
            log.info("订单查询异常ByUserIdOrderId："+e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result update(Orders order) {
        try {
            Orders orders = ordersMapper.queryByOrderId(order.getId());
            if (orders == null) //订单不存在
            {
                log.error("订单修改失败：订单不存在");
                return Result.build(ResultEnum.ORDER_NOT_EXIST);
            }
            if (orders.getStatus() == -1 || orders.getStatus() == 1) //订单是否完成，或取消
            {
                log.error("订单修改失败：订单"+ (orders.getStatus() == -1 ? "已取消":"已支付" ));
                return Result.build(ResultEnum.ORDER_UPDATE_FAIL);
            }
            ordersMapper.update(order);
            log.info("订单修改成功");
            return Result.build(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("订单修改异常："+e.getMessage());
            return Result.build(ResultEnum.ORDER_UPDATE_FAIL);
        }
    }

    @Override
    public Result deleteByUserIdOrderId(String userId, String orderId) {
        try {
            //判断用户是否存在
            User user = userMapper.findUserInfoById(userId);
            if (user == null)
            {
                log.error("订单删除失败ByUserIdOrderId：用户不存在");
                return Result.build(ResultEnum.NOT_EXIST_USER);
            }

            //判断该订单号对应用户
            Orders order = ordersMapper.queryByOrderId(orderId);
            if (order == null || !(order.getUser_id().equals(userId)))
            {
                log.error("订单删除失败ByUserIdOrderId：订单不存在");
                return Result.build(ResultEnum.ORDER_NOT_EXIST);
            }
            //用户，订单都存在，删除订单
            ordersMapper.deleteById(orderId);
            log.info("订单删除成功ByUserIdOrderId");
            return Result.build(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            log.info("订单删除异常ByUserIdOrderId："+e.getMessage());
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }

    //同步通知
    @Override
    public Result returnNotice(HttpServletRequest request) {
        log.info("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = payNotice(request);
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        //付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        // 修改订单状态为支付成功，已付款; 同时新增支付流水
        log.info("********************** 支付成功(支付宝同步通知) **********************");
        log.info("* 订单号: {}", out_trade_no);
        log.info("* 支付宝交易号: {}", trade_no);
        log.info("* 实付金额: {}", total_amount);
        log.info("***************************************************************");//            mv.addObject("productName", product.getName());

        return Result.build(ResultEnum.PAY_SUCCESS, params);
    }

    private Map<String, String> payNotice(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        return params;
    }

    //异步通知
    @Transactional
    @Override
    public String notifyNotice(HttpServletRequest request) {
        try {
            log.info("支付成功, 进入异步通知接口...");

            //获取支付宝POST过来反馈信息
            Map<String, String> params = payNotice(request);
            System.out.println(params.toString());
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCertCheckV1(params, AlipayConfig.path + AlipayConfig.alipay_cert_path, AlipayConfig.charset, AlipayConfig.sign_type);
            /* 实际验证过程建议商户务必添加以下校验：
            1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
            2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
            3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
            4、验证app_id是否为该商户本身。
            */
            //验证成功
            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

//            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//            } else
                if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知


                    // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                    Orders orders = ordersMapper.queryByOrderId(out_trade_no);
                    orders.setStatus(1);
                    orders.setAlipay_id(trade_no);
                    ordersMapper.update(orders);
                    this.updateWallet(orders);
                    log.info("********************** 支付成功(支付宝异步通知) **********************");
                    log.info("* 订单号: {}", out_trade_no);
                    log.info("* 支付宝交易号: {}", trade_no);
                    log.info("* 实付金额: {}", total_amount);
                    log.info("***************************************************************");
                }
                log.info("支付成功...");
                return "success";
            } else {//验证失败
                log.info("支付, 验签失败...");
                return "fail";
            }
        } catch (Exception e) {
            log.error("支付异常：" + e.getMessage());
            return "fail";
        }
    }

    @Override
    public String getAliPay(String orderId) {
        try {
            //构造client
            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
            certAlipayRequest.setServerUrl(AlipayConfig.gatewayUrl);
            certAlipayRequest.setAppId(AlipayConfig.app_id);
            certAlipayRequest.setFormat("json");
            certAlipayRequest.setCharset(AlipayConfig.charset);
            certAlipayRequest.setSignType(AlipayConfig.sign_type);
            certAlipayRequest.setPrivateKey(AlipayConfig.merchant_private_key);
            certAlipayRequest.setCertPath(AlipayConfig.path + AlipayConfig.app_cert_path);
            certAlipayRequest.setAlipayPublicCertPath(AlipayConfig.path + AlipayConfig.alipay_cert_path);
            certAlipayRequest.setRootCertPath(AlipayConfig.path + AlipayConfig.alipay_root_cert_path);
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
            // AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2016102900776579",AlipayConfig.merchant_private_key,"json","utf-8",AlipayConfig.alipay_public_key,"RSA2");

            //发送API请求
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setReturnUrl(AlipayConfig.return_url);
            request.setNotifyUrl(AlipayConfig.notify_url);

            Orders orders = ordersMapper.queryByOrderId(orderId);
            if (orders == null)
            {
                log.error("支付宝订单创建失败：订单不存在");
                return "非法请求"; //防止非法请求
            }
            BigDecimal totalCount = new BigDecimal(Double.toString(0.00));
            if (orders.getStatus() == 1)
            {log.error("支付宝订单创建失败：订单已支付");
                return "订单已支付"; //订单状态
            }
            if (orders.getStatus() == -1)
            {
                log.error("支付宝订单创建失败：订单已取消");
                return "订单已取消"; //订单状态
            }
            totalCount = totalCount.add(orders.getReal_price());

            //商户订单号，商户网站订单系统中唯一订单号，必填
            //付款金额，必填
            String total_amount = totalCount.toString();
            Product product = productMapper.queryById(orders.getProduct_id());

            //订单名称，必填
            String subject = product.getName();
            //商品描述，可空
            String body = product.getDesc();

            // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
            String timeout_express = "12h";
            request.setBizContent("{\"out_trade_no\":\"" + orderId + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"timeout_express\":\"" + timeout_express + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            log.info("支付宝订单创建成功");
            return alipayClient.pageExecute(request).getBody();
        } catch (Exception e) {
            log.error("支付宝订单创建异常："+e.getMessage());
            return null;
        }
    }


    @Override
    public Result queryWalletByUserId(String userId) {
        try {
            User user = userMapper.findUserInfoById(userId);
            if (user == null)
            {
                log.error("查询钱包信息失败ByUserId：用户不存在");
                return Result.build(ResultEnum.NOT_EXIST_USER);
            }
            Wallet wallet = walletMapper.queryById(userId);
            log.info("查询钱包信息成功ByUserId");
            return Result.build(ResultEnum.QUERY_SUCCESS, wallet);

        } catch (Exception e) {
            log.error("查询钱包信息异常ByUserId："+e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result deleteWalletByUserId(String userId) {
        try {
            User user = userMapper.findUserInfoById(userId);
            if (user == null)
            {
                log.error("删除钱包信息失败ByUserId：用户不存在");
                return Result.build(ResultEnum.NOT_EXIST_USER);
            }
            walletMapper.deleteById(userId);
            log.error("删除钱包信息成功ByUserId");
            return Result.build(ResultEnum.DELETE_SUCCESS);

        } catch (Exception e) {
            log.error("删除钱包信息异常ByUserId："+e.getMessage());
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }

    //修改钱包信息，订单支付成功时
    private void updateWallet(Orders order) {

        //获取购买的产品
        Product product = productMapper.queryById(order.getProduct_id());

        //当前用户钱包信息
        Wallet wallets = walletMapper.queryById(order.getUser_id());

        //用户类型,1包年，0包月，-1次数，-2免费用户
        Integer w_type = wallets.getPay_type();

        //产品类型
        Integer p_type = product.getType();

        //产品包月、年时，product.getTm_months()为产品对应月数
        Integer t_months = product.getTm_months();

        //产品为次数时，product.getTm_nums()为产品包含查题次数。
        Integer t_nums = product.getTm_nums();

        //购买数量
        Integer p_nums = order.getNums();

        //生效时间
        Date createTime = null;
        //失效时间
        Date invalidTime = null;

        GregorianCalendar gc = new GregorianCalendar();
        //产品类型，1为包年，0为包月，-1为按次数
        if (p_type == 1 || p_type == 0) { //包年或者包月
            t_months = t_months * p_nums;
            //生效时间为当前时间
            createTime = new Date();
            gc.setTime(new Date());
            gc.add(Calendar.MONTH, t_months);//表示月份加减
            //失效时间
            invalidTime = gc.getTime();
            if (w_type == 1) {
                createTime = wallets.getCreate_time();
                invalidTime = wallets.getInvalid_time().compareTo(new Date()) < 0 ? new Date() : wallets.getInvalid_time();
                gc.setTime(invalidTime);
                gc.add(Calendar.MONTH, t_months);//表示月份加减
                //失效时间
                invalidTime = gc.getTime();
            }
            if (w_type == 0) {
                t_nums = wallets.getTm_nums();
            }
            p_type = 1;
        }
        if (p_type == -1) //购买为按次数
        {
            p_type = 0;
            t_nums = t_nums * p_nums;  //购买总次数
            if (w_type == 0) //判断原来是否按次数
                t_nums = t_nums + wallets.getTm_nums();
            if (w_type == 1) //判断是否包年或者包月
                p_type = 1;
        }
        Wallet wallet = new Wallet();
        wallet.setUser_id(order.getUser_id());
        wallet.setPay_type(p_type);
        wallet.setTm_nums(t_nums);
        wallet.setCreate_time(createTime);
        wallet.setInvalid_time(invalidTime);
        walletMapper.update(wallet);
    }
}
