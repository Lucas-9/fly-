package top.lucas9.lblog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.common.Result;
import top.lucas9.lblog.entity.User;
import top.lucas9.lblog.entity.AccountInfo;
import top.lucas9.lblog.service.UserService;
import top.lucas9.lblog.utils.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lucas
 */
@RestController
@Api(tags = "登录、认证、授权等操作")
public class AuthController {
    @Autowired
    private UserService userService;


    /**
     * 生成图像验证码
     * @param response response请求对象
     * @throws IOException
     */
    @ApiOperation(value = "验证码",produces = "image/jpeg")
    @GetMapping("captcha")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //定义图形验证码的长和宽
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        request.getSession().setAttribute(Constant.CAPTCHA_SESSION_KEY, captcha.getCode());
        //输出浏览器
        OutputStream out=response.getOutputStream();
        captcha.write(out);
        out.flush();
        out.close();
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody User user, String repass, String vercode, HttpServletRequest request) {
        if(!user.getPassword().equals(repass)) {
            return Result.failed("两次输入密码不相同");
        }

        String captcha = (String) request.getSession().getAttribute(Constant.CAPTCHA_SESSION_KEY);
        if(vercode == null || !vercode.equalsIgnoreCase(captcha)) {
            return Result.failed("验证码输入不正确");
        }
        return userService.register(user);
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result<AccountInfo> login(String username, String password, HttpServletResponse response) {
        AccountInfo accountInfo = userService.login(username, password, response);
        Assert.notNull(accountInfo, "用户名或密码错误");
        return Result.success("登录成功", accountInfo);
    }

    @ApiOperation(value = "退出", notes = "认证后操作")
    @RequiresAuthentication
    @PostMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success("退出成功");
    }

    @ApiOperation(value = "测试授权", notes = "需要admin权限")
    @RequiresRoles("admin")
    @PostMapping("/test")
    public Result test() {
        return Result.success("测试授权");
    }
}
