package com.isliujiao.ssoappserver.service;

import com.isliujiao.ssoappserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务层
 */
@Service
public class LoginService {


    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token值
     */
    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty("username") || StringUtils.isEmpty(password)) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        // 为了测试方便 不去数据库比较密码
        if ("123".equals(password)) {
            // 返回生成的token
            map.put("token", jwtUtil.sign(username));
            map.put("refreshToken", jwtUtil.refreshToken(username));
        }
        return map;
    }

    /**
     * 校验jwt是否成功
     *
     * @param token token值
     * @return 校验是否超过
     */
    public boolean checkJwt(String token) {
        return jwtUtil.verify(token);
    }

    /**
     * 重新生成token和refreshToken
     *
     * @param refreshToken refreshToken
     * @return token和refreshToken
     */
    public Map<String, String> refreshJwt(String refreshToken) {
        return jwtUtil.refreshJwt(refreshToken);
    }
}
