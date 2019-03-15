package com.nhsoft.share.mybatis.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSourceConfig {

    @Autowired
    private  DataSourceProperties dataSourceProperties;

    @Autowired
    private MybatisProperties properties;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    private  Interceptor[] interceptors = null;

    private  DatabaseIdProvider databaseIdProvider = null;

    private List<ConfigurationCustomizer> configurationCustomizers = new ArrayList<>();

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUser());
        dataSource.setPassword(dataSourceProperties.getPassword());
        return dataSource;
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setVfs(SpringBootVFS.class);
//        if (StringUtils.hasText(this.properties.getConfigLocation())) {
//            factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
//        }
//        applyConfiguration(factory);
//        if (this.properties.getConfigurationProperties() != null) {
//            factory.setConfigurationProperties(this.properties.getConfigurationProperties());
//        }
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            factory.setPlugins(this.interceptors);
//        }
//        if (this.databaseIdProvider != null) {
//            factory.setDatabaseIdProvider(this.databaseIdProvider);
//        }
//        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
//            factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
//        }
//        if (this.properties.getTypeAliasesSuperType() != null) {
//            factory.setTypeAliasesSuperType(this.properties.getTypeAliasesSuperType());
//        }
//        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
//            factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
//        }
//
//        Resource mapperResources = this.resourceLoader.getResource(this.properties.getMapperLocations()[0]);
//
//        factory.setMapperLocations(new Resource[]{mapperResources});
//
//        return factory.getObject();
//    }


    private void applyConfiguration(SqlSessionFactoryBean factory) {
        org.apache.ibatis.session.Configuration configuration = this.properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(this.properties.getConfigLocation())) {
            configuration = new org.apache.ibatis.session.Configuration();
        }
        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
                customizer.customize(configuration);
            }
        }
        factory.setConfiguration(configuration);
    }

}
