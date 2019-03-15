package com.nhsoft.share.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
        prefix = "nhsoft.datasource.jdbc",
        ignoreInvalidFields = true)
@Component
@Data
public class DataSourceProperties {

    private String driverClassName;
    private String url;
    private String user;
    private String password;
}
