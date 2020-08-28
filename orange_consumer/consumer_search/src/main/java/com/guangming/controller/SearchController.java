package com.guangming.controller;

import com.alibaba.fastjson.JSON;
import com.guangming.client.AuthUtils;
import com.guangming.pojo.User;
import com.guangming.pojo.Wallet;
import com.guangming.service.IPayFeignService;
import com.guangming.service.ISolrFeignService;
import com.guangming.utils.IpUtils;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@CrossOrigin
public class SearchController {

    @Resource
    private ISolrFeignService solrFeignService;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;
    @Autowired
    private IPayFeignService payFeignService;

    @GetMapping("/")
    public String index(HttpServletRequest req, Model model, HttpServletResponse resp, HttpSession session) {
        //从请求头或者session中获取token
        String header = req.getHeader("Authorization");
        if (header == null) {
            header = (String) session.getAttribute("Authorization");
        }
        User user = null;
        if (header != null) {
            user = AuthUtils.getReqUser(header); //处理获取认证中的用户数据
        }
        model.addAttribute("user", user);
        //user不为空，token写入响应头
        if (user != null) {
            resp.setHeader("Authorization", header);
            resp.setHeader("RefreshToken", (String) session.getAttribute("RefreshToken"));
        }
        return "index";
    }

    @PostMapping("/search/cx")
    @ResponseBody
    public Result postSearch(@RequestParam("problem") String problem, HttpServletRequest req) {

        //1、判断是否有Authorization
        String authorization = req.getHeader("Authorization");
        /*用户未登录*/
        if (authorization == null) {
            //判断redis是否存在数据
            String ip = IpUtils.getIpAddr(req);
            Integer integer = redisTemplate.opsForValue().get(("ip:" + ip));
            Result result = solrFeignService.indexQuery(problem);
            if (integer != null && integer > 0 && result.getCode() == 1)  //存在数据
                redisTemplate.opsForValue().set(("ip:" + ip), integer - 1);
            return result;
        }
        //2、根据Authorization获取用户信息
        User user = AuthUtils.getReqUser(authorization);
        /*已登录的用户*/
        if (user != null && user.getId() != null) {


            Result index = solrFeignService.indexQuery(problem);
            if (index.getCode() == 0)/*查题结果是否有效*/
                return index;

            /*从Redis中获取用户查题类型信息及次数信息*/
            Integer pay_type = redisTemplate.opsForValue().get(("search:" + user.getId()));
            System.out.println("支付类型：" + pay_type);
            if (pay_type != null) {
                Integer nums = redisTemplate.opsForValue().get((user.getId() + ":" + pay_type));/*次数是否存在，以及是否为0*/
                if (nums == null || nums <= 0) {
                    redisTemplate.opsForValue().set(("search:" + user.getId()), 2, 1, TimeUnit.MICROSECONDS);
                    redisTemplate.opsForValue().set((user.getId() + ":" + pay_type), 2, 1, TimeUnit.MICROSECONDS);
                    Wallet wallet = new Wallet();
                    wallet.setUser_id(user.getId());

                    if (pay_type == 1) {
                        wallet.setPay_type(0);
                    }
                    if (pay_type == 0) {
                        wallet.setPay_type(-1);
                        wallet.setTm_nums(0);
                    }
                    if (pay_type == -1)
                        wallet.setTm_nums(0);
                    payFeignService.updateWallet(wallet);/*修改查体类型*/
                    /*可以使用消息中间件去通知修改查题类型*/
                    return Result.build(ResultEnum.NOT_ENOUGH_MONENY);
                }
                if (pay_type == 1) {
                    return index;
                }
                if (pay_type == 0 || pay_type == -1)
                    redisTemplate.opsForValue().set((user.getId() + ":" + pay_type), nums - 1);
                return index;
            }

            //3、根据用户信息查询查题类型
            Result result = payFeignService.getWalletInfo(user.getId());
            if (result.getData() == null) {
                return result;
            }
            String jsonString = JSON.toJSONString(result.getData());
            Wallet wallet = JSON.toJavaObject(JSON.parseObject(jsonString), Wallet.class);
            //4、通过类型判断用户查题权限
            pay_type = wallet.getPay_type();

            //5、查题类型，1包月，0按次数，-1免费用户

            /*包月用户*/
            if (pay_type == 1) {
                Date date = new Date();
                Date invalid_time = wallet.getInvalid_time();/*失效时间*/
                //1、判断是否过期
                long s = invalid_time.getTime() - date.getTime();
                long days = s / (1000 * 60 * 60 * 24);
                System.out.println("天数：" + days);
                if (days == 0 && (wallet.getTm_nums() == null || wallet.getTm_nums() == 0)) {
                    return Result.build(ResultEnum.NOT_ENOUGH_MONENY);
                }
                if (days == 0 && wallet.getTm_nums() != null && wallet.getTm_nums() > 0) {
                    pay_type = 0;/*转成按次数查题*/
                    wallet = new Wallet();
                    wallet.setUser_id(user.getId());
                    wallet.setPay_type(pay_type);
                    payFeignService.updateWallet(wallet);/*修改查体类型*/
                }
                if (days > 0) {
                    //记录用户查题剩余次数
                    redisTemplate.opsForValue().set((user.getId() + ":" + pay_type), (int) days, 1, TimeUnit.DAYS);
                }
            }
            if (pay_type == 0) {
                //记录用户查题剩余次数
                redisTemplate.opsForValue().set((user.getId() + ":" + pay_type), wallet.getTm_nums());
            }
            if (pay_type == -1) {
                //记录用户查题剩余次数
                redisTemplate.opsForValue().set((user.getId() + ":" + pay_type), wallet.getTm_nums());
            }
            if (wallet.getTm_nums() == null || wallet.getTm_nums() == 0)
                return Result.build(ResultEnum.NOT_ENOUGH_MONENY);
            //记录用户查题类型
            redisTemplate.opsForValue().set(("search:" + user.getId()), pay_type);
            return index;
        }
        return Result.build(ResultEnum.ILLEGAL_OPTIONS);
    }

    @GetMapping("/search/cx/{problem}")
    @ResponseBody
    public Result getSearch(@PathVariable("problem") String problem) {
        return solrFeignService.indexQuery(problem);
    }
}
