package lib.skydo.dlock;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Pool;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPoolConfig;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Optional;

@Configuration
public class LockConfig {

    @Value("${dlock.lock_registery}")
    private String dlockRegistery;

    @Value("${dlock.host}")
    private String host;

    @Value("${dlock.port}")
    private Integer port;

    @Value("${dlock.process.timeout}")
    private Integer timeout;

    @Value("${dlock.pool_size}")
    private Integer poolSize;

    @Bean
    @Qualifier("redis")
    public LockRegistry getLockRegistry()  {

        return new RedisLockRegistry(getJedisConnectionFactory(), "lock-key", timeout);
    }

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory () {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        redisStandaloneConfiguration.setHostName(host);

        redisStandaloneConfiguration.setPort(port);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());

//        jedisConnectionFactory.getPoolConfig();

//        jedisConnectionFactory.getPoolConfig().setMaxIdle(poolSize);

        jedisConnectionFactory.afterPropertiesSet();

        return jedisConnectionFactory;
    }

    @Bean
    public JedisClientConfiguration getJedisClientConfiguration() {

        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();

//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxIdle(poolSize);

//        genericObjectPoolConfig.setMaxTotal(poolSize);

        return builder.usePooling().poolConfig(jedisPoolConfig).build();
    }


}














