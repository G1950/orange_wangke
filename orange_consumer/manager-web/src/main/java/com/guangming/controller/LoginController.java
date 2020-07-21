package com.guangming.controller;

import com.guangming.client.AuthUtils;
import com.guangming.commons.Token;
import com.guangming.pojo.User;
import com.guangming.service.IAuthServerFeignService;
import com.guangming.utils.Captcha;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController {
    @Resource
    private IAuthServerFeignService authServerFeignService;

    @GetMapping("login")
    public String toLogin() {
        return "login";
    }

    @GetMapping("register")
    public String toRegister() {
        return "register";
    }

    @GetMapping("forget")
    public String toForget() {
        return "forget";
    }

    @PostMapping("login")
    @ResponseBody
    public Result login(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("captcha") String code, HttpServletResponse resp, HttpSession session) {
        if (code.isEmpty() || !code.equals(session.getAttribute("code")))
            return Result.build(ResultEnum.CAPTCHA_ERROR);
        //向授权中心申请token
        Map<String, String> token = Token.getToken(account, password, authServerFeignService);

        User user = AuthUtils.getUser(token.get("Authorization")); //处理获取认证中的用户数据
        if (user == null)
            return Result.build(ResultEnum.USER_LOGIN_FAIL);

        //token加入头部
        resp.setHeader("Authorization", token.get("Authorization"));
        session.setAttribute("Authorization", token.get("Authorization"));
        //refresh_token加入头部
        resp.setHeader("RefreshToken", token.get("RefreshToken"));
        session.setAttribute("RefreshToken", token.get("RefreshToken"));

        return Result.build(ResultEnum.USER_LOGIN_SUCCESS, user.getId());
    }

    @GetMapping("signout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("captcha")
    public void getCode(HttpServletResponse resp, HttpSession session) throws IOException {
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        String verifyCode = Captcha.generateVerifyCode(5).toLowerCase();
        session.setAttribute("code", verifyCode);
        Captcha.outputImage(125, 38, resp.getOutputStream(),
                verifyCode);
    }

    @GetMapping("info")
    public String getInfo(HttpSession session) {
        session.invalidate();
        return "me";
    }

}
