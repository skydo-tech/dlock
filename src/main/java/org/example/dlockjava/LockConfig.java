package org.example.dlockjava;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

@Configuration
public class LockConfig {

    @Bean
    @Qualifier("redis")
    public LockRegistry getLockRegistry()  {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.afterPropertiesSet();
        return new RedisLockRegistry(jedisConnectionFactory, "lock-key");
    }
}
