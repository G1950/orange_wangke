package com.guangming.controller;

import com.guangming.client.AuthUtils;
import com.guangming.pojo.User;
import com.guangming.service.ISolrFeignService;
import com.guangming.utils.IpUtils;
import com.guangming.utils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
public class SearchController {

    @Resource
    private ISolrFeignService solrFeignService;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

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
        //判断redis是否存在数据
        String ip = IpUtils.getIpAddr(req);
        Integer integer = redisTemplate.opsForValue().get(("ip:" + ip));
        Result result = solrFeignService.indexQuery(problem);
        if (integer != null && integer > 0 && result.getCode() == 1)  //不存在数据
            redisTemplate.opsForValue().set(("ip:" + ip), integer - 1);
        return solrFeignService.indexQuery(problem);
    }

    @GetMapping("/search/cx/{problem}")
    @ResponseBody
    public Result getSearch(@PathVariable("problem") String problem) {
        return solrFeignService.indexQuery(problem);
    }

}
