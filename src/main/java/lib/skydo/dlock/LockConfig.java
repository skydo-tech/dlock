package lib.skydo.dlock;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

@Configuration
public class LockConfig {

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }

    @Value("${dlock.lock_registery}")
    private String dlockRegistery;

    @Value("${dlock.host}")
    private String host;

    @Value("${dlock.port}")
    private String port;

    @Bean
    @Qualifier("redis")
    public LockRegistry getLockRegistry(RedisConnectionFactory connectionFactory)  {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName();
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        config.setHostName();
//        RedisConnectionFactory factory = new RedisConnectionFactory();
        return new RedisLockRegistry(connectionFactory, "lock-key");
    }
}
