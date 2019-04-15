package com.nhsoft.share.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.SafeEncoder;

import java.time.Duration;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-04-14 21:42
 * @Modified By：
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean(name = "jedisKeySerializer")
    public StringRedisSerializer jedisKeySerializer(){
        return new StringRedisSerializer();
    }

    @Bean(name = "jedisValueSerializer")
    public MyJackson2JsonRedisSerializer jedisValueSerializer(){

        MyJackson2JsonRedisSerializer redisSerializer = new MyJackson2JsonRedisSerializer();
        return  redisSerializer;
    }

    /**
     * 重写Redis序列化方式，使用Json方式:
     * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
     * Spring Data JPA为我们提供了下面的Serializer：
     * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
     * 在此我们将自己配置RedisTemplate并定义Serializer。
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(createJedisConnectionFactory());
        redisTemplate.setKeySerializer(jedisKeySerializer());
        redisTemplate.setValueSerializer(jedisValueSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    private JedisPoolConfig createJedisPoolConfig(){

        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(redisProperties.getTestOnBorrow());  //定时对线程池中空闲的链接进行validateObject校验
        config.setTestOnReturn(redisProperties.getTestOnReturn());  //在进行returnObject对返回的connection进行validateObject校验
        config.setMaxIdle(redisProperties.getMaxIdle());            //最大空闲书
        config.setMinIdle(redisProperties.getMinIdle());            //最小空闲数
        config.setMaxTotal(redisProperties.getMaxTotal());          //最大连接数
        config.setMaxWaitMillis(redisProperties.getMaxWaitMillis());//最大等待时间
        config.setTestWhileIdle(redisProperties.getTestWhileIdle());
        return config;
    }



    private JedisConnectionFactory createJedisConnectionFactory(){

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setDatabase(redisProperties.getDatabase());
        configuration.setHostName(redisProperties.getHostName());
        configuration.setPort(redisProperties.getPort());
        configuration.setPassword(redisProperties.getPassword());

        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder jedisClientConfiguration
                = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(redisProperties.getConnectTimeoutMills()));
        jedisClientConfiguration.usePooling();
        jedisClientConfiguration.poolConfig(createJedisPoolConfig());

        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(configuration, jedisClientConfiguration.build());

        return redisConnectionFactory;
    }


}
