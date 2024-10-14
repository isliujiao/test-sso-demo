package com.isliujiao.ssoappserver.controller;

import com.isliujiao.ssoappserver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author qx
 * @date 2023/7/4
 * @des 验证控制层
 */
@Controller
@RequestMapping("/sso")
public class AuthController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token值
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login(String username, String password) {
        return loginService.login(username, password);
    }

    /**
     * 验证jwt
     *
     * @param token token
     * @return 验证jwt是否合法
     */
    @RequestMapping("/checkJwt")
    @ResponseBody
    public boolean checkJwt(String token) {
        return loginService.checkJwt(token);
    }


    /**
     * 重新生成token和refreshToken
     *
     * @param refreshToken refreshToken
     * @return token和refreshToken
     */
    @RequestMapping("/refreshJwt")
    @ResponseBody
    public Map<String, String> refreshJwt(String refreshToken) {
        return loginService.refreshJwt(refreshToken);
    }

}
