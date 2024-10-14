package com.isliujiao.ssoappserver.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Jwt配置类
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    /**
     * 过期时间-分钟
     */
    private Integer expireTime;

    /**
     * refreshToken时间
     */
    private Integer refreshTime;

    /**
     * 密钥
     */
    private String secret;
}
