package com.nhsoft.share.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-04-14 21:38
 * @Modified By：
 */
@ConfigurationProperties(
        prefix = "nhsoft.redis",
        ignoreInvalidFields = true
)
@Component
@Data
public class RedisProperties {

    private Integer port;
    private Integer database;
    private String password;
    private String hostName;

    private Integer minIdle;
    private Integer maxIdle;
    private Integer maxTotal;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Integer connectTimeoutMills;
    private Integer maxWaitMillis;
    private Boolean testWhileIdle;


}

