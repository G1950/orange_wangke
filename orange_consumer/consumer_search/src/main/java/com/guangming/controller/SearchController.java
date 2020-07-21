package com.guangming.controller;

import com.guangming.client.AuthUtils;
import com.guangming.pojo.User;
import com.guangming.service.ISolrFeignService;
import com.guangming.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@Api(value = "用户查题接口列表", tags = "用户查题接口列表")
public class SearchController {

    @Resource
    private ISolrFeignService solrFeignService;


    @ApiOperation(value = "用户查题首页", hidden = true)
    @GetMapping("/")
    public String index(HttpServletRequest req, Model model, HttpServletResponse resp, HttpSession session) {
        String header = req.getHeader("Authorization");
        if (header == null) {
            header = (String) session.getAttribute("Authorization");
        }
        User user = null;
        if (header != null) {
            user = AuthUtils.getUser(header); //处理获取认证中的用户数据
            String userName = AuthUtils.getUserName(header);
            System.out.println(userName);
        }
        model.addAttribute("user", user);
        if (user != null) {
            resp.setHeader("Authorization", header);
            resp.setHeader("RefreshToken", (String) session.getAttribute("RefreshToken"));
        }
        return "index";
    }

    @PostMapping("/search/cx")
    @ResponseBody
    public Result postSearch(@RequestParam("problem") String problem) {
        return solrFeignService.indexQuery(problem);
    }

    @GetMapping("/search/cx/{problem}")
    @ResponseBody
    public Result getSearch(@PathVariable("problem") String problem) {
        return solrFeignService.indexQuery(problem);
    }

}
