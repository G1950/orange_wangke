package com.guangming.controller;

import com.guangming.client.AuthUtils;
import com.guangming.commons.Token;
import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.service.IAuthServerFeignService;
import com.guangming.service.IPayFeignService;
import com.guangming.service.IUploadFeignService;
import com.guangming.service.IUserFeignService;
import com.guangming.utils.Captcha;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController {
    private final IAuthServerFeignService authServerFeignService;
    private final IUserFeignService userFeignService;
    private final IPayFeignService payFeignService;
    private final IUploadFeignService uploadFeignService;

    public LoginController(IAuthServerFeignService authServerFeignService, IUserFeignService userFeignService, IPayFeignService payFeignService, IUploadFeignService uploadFeignService) {
        this.authServerFeignService = authServerFeignService;
        this.userFeignService = userFeignService;
        this.payFeignService = payFeignService;
        this.uploadFeignService = uploadFeignService;
    }

    /*跳转登录页面*/
    @GetMapping("login")
    public String toLogin() {
        return "login";
    }

    /*跳转注册页面*/
    @GetMapping("register")
    public String toRegister() {
        return "register";
    }

    /*跳转忘记密码页面*/
    @GetMapping("forget")
    public String toForget() {
        return "forget";
    }

    /*用户登录*/
    @PostMapping("login")
    @ResponseBody
    public Result login(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("captcha") String code, HttpServletResponse resp, HttpSession session) {
        //验证码判断
        if (code.isEmpty() || !code.equals(session.getAttribute("code")))
            return Result.build(ResultEnum.CAPTCHA_ERROR);
        //向授权中心申请token
        Map<String, String> token = Token.getToken(account, password, authServerFeignService);
        User user = AuthUtils.getReqUser(token.get("Authorization")); //处理获取认证中的用户数据
        if (user == null)
            return Result.build(ResultEnum.USER_LOGIN_FAIL);
        session.setAttribute("id", user.getId());
        //token加入头部
        resp.setHeader("Authorization", token.get("Authorization"));
        session.setAttribute("Authorization", token.get("Authorization"));
        session.setAttribute("account", account);
        //refresh_token加入头部
        resp.setHeader("RefreshToken", token.get("RefreshToken"));
        session.setAttribute("RefreshToken", token.get("RefreshToken"));

        return Result.build(ResultEnum.USER_LOGIN_SUCCESS, user.getId());
    }

    /*退出登录*/
    @GetMapping("signout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    /*发送验证码*/
    @GetMapping("captcha")  //图片验证码
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

    /*查询用户信息*/
    @GetMapping("info/{userId}")
    public String getInfo(@PathVariable("userId") String id, Model model, HttpSession session) {
        //1、查询用户信息
        Result user = userFeignService.queryById(id);
        model.addAttribute("user", user.getData());
        //2、查询钱包信息
        Result walletInfo = payFeignService.getWalletInfo(id);
        model.addAttribute("wallet", walletInfo.getData());
        //3、查询所有订单信息
        Result orders = payFeignService.query(id, "0");
        model.addAttribute("orders", orders.getData());
        String account = (String) session.getAttribute("account");
        account = account == null ? "" : account;
        model.addAttribute("account", account);
        return "me";
    }

    /*上传用户头像*/
    @PostMapping("/upload/avatar")
    @ResponseBody
    public Result uploadImg(@RequestPart(value = "file") MultipartFile file, HttpSession session) {
        String id = (String) session.getAttribute("id");
        id = id == null ? "example" : id;
        System.out.println(id);
        return uploadFeignService.uploadImg(file, id);
    }

    /*修改用户信息*/
    @PostMapping("user/info")
    @ResponseBody
    public Result updateInfo(@RequestBody User user) {
        return userFeignService.update(user);
    }

    /*修改密码*/
    @PostMapping("user")
    @ResponseBody
    public Result updateUser(@RequestParam("id") String id, @RequestParam("account") String account, @RequestParam("oldPassword") String password, @RequestParam("repassword") String rePassword, @RequestParam("password") String newPassword) {
        System.out.println(account + "   " + password + "   " + newPassword + "  " + rePassword);
        Result result = userFeignService.queryByAccountAndPassword(account, password);
        if (result.getData() == null)
            return result;
        if (!newPassword.equals(rePassword))
            return Result.build(ResultEnum.PASSWORD_ERROR);
        Authority authority = new Authority();
        authority.setAuthority_id(id);
        authority.setAuthority_account(account);
        authority.setAuthority_pass(newPassword);
        return userFeignService.update(authority);
    }


    /*获取获取用户账号是否存在*/
    @GetMapping("user")
    @ResponseBody
    public Result findUser(@RequestParam("account") String account) {
        Authority authority = new Authority();
        authority.setAuthority_account(account);
        Result result = userFeignService.query(authority);
        if (result.getCode() == 1)
            return Result.build(ResultEnum.EXIST_USER);
        return result;
    }


    /*用户注册*/
    @PostMapping("register")
    @ResponseBody
    public Result register(@RequestBody Authority authority, @RequestParam("code") String code, HttpSession session) {
        //验证码判断
        if (code.isEmpty() || !code.equals(session.getAttribute("code")))
            return Result.build(ResultEnum.CAPTCHA_ERROR);
        return userFeignService.save(authority);
    }
}
