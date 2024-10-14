package com.isliujiao.ssoappserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.isliujiao.ssoappserver.conf.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 生成一个jwt字符串
     *
     * @param username 用户名
     * @return jwt字符串
     */
    public String sign(String username) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        return JWT.create()
                // 设置过期时间30分钟
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpireTime()))
                // 设置负载
                .withClaim("username", username).sign(algorithm);
    }

    /**
     * 生成refreshToken
     *
     * @param username 用户名
     * @return
     */
    public String refreshToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        return JWT.create()
                // 设置更新时间2个小时
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTime()))
                // 设置负载
                .withClaim("username", username).sign(algorithm);
    }


    public static void main(String[] args) {
        Algorithm algorithm = Algorithm.HMAC256("KU5TjMO6zmh03bU3");
        String username = "admin";
        String token = JWT.create()
                // 设置过期时间1个小时
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                // 设置负载
                .withClaim("username", username).sign(algorithm);
        System.out.println(token);
    }

    /**
     * 校验token是否正确
     *
     * @param token token值
     */
    public boolean verify(String token) {
        if (token == null || token.length() == 0) {
            throw new RuntimeException("token为空");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Map<String, Claim> map = decodedJWT.getClaims();
            System.out.println("claims:" + map.get("username").asString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 重新生成token和refreshToken
     *
     * @param refreshToken refreshToken
     * @return 返回token和refreshToken
     */
    public Map<String, String> refreshJwt(String refreshToken) {
        if (refreshToken == null || refreshToken.length() == 0) {
            throw new RuntimeException("refreshToken为空");
        }
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
        Map<String, Claim> map = decodedJWT.getClaims();
        // 获取用户名
        String username = map.get("username").asString();
        Map<String, String> resultMap = new HashMap<>();
        // 重新生成token和refreshToken
        resultMap.put("token", sign(username));
        resultMap.put("refreshToken", refreshToken(username));
        return resultMap;
    }
}
