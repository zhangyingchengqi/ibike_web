package com.yc.projects.yc74ibike.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisAutoConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPool,
            RedisClusterConfiguration jedisConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory(jedisConfig, jedisPool);
        factory.afterPropertiesSet();
        return factory;
    }

    @Configuration
    public static class JedisConf {
        @Value("${spring.redis.cluster.nodes:192.168.0.200:6379,192.168.0.201:6379,192.168.0.202:6379,192.168.0.203:6379,192.168.0.200:6380,192.168.0.201:6380}")
        private String nodes;
        @Value("${spring.redis.cluster.max-redirects:3}")
        private Integer maxRedirects;
        @Value("${spring.redis.password:}")
        private String password;
        @Value("${spring.redis.database:0}")
        private Integer database;

        //redis联接池相关配置
        @Value("${spring.redis.jedis.pool.max-active:8}")
        private Integer maxActive;
        @Value("${spring.redis.jedis.pool.max-idle:8}")
        private Integer maxIdle;
        @Value("${spring.redis.jedis.pool.max-wait:-1}")
        private Long maxWait;
        @Value("${spring.redis.jedis.pool.min-idle:0}")
        private Integer minIdle;

        @Bean  //联接池的配置
        public JedisPoolConfig jedisPool() {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMinIdle(minIdle);
            return jedisPoolConfig;
        }

        @Bean
        public RedisClusterConfiguration jedisConfig() {
            RedisClusterConfiguration config = new RedisClusterConfiguration();
            String[] sub = nodes.split(",");
            List<RedisNode> nodeList = new ArrayList<>(sub.length);
            String[] tmp;
            for (String s : sub) {
                tmp = s.split(":");
                // fixme 先不考虑异常配置的case
                nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
            }
            config.setClusterNodes(nodeList);
            config.setMaxRedirects(maxRedirects);
            config.setPassword(RedisPassword.of(password));
            return config;
        }
    }
}

