package lk.dmg.talcacheredismanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisDemoConfiguration {
    public static Logger LOGGER = LoggerFactory.getLogger(RedisDemoConfiguration.class);

    @Value("${redis.server}")
    private String redisServer;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.redisPassword}")
    private String redisPassword;

    @Value("${redis.redisPoolMaxTotal}")
    private int redisPoolMaxTotal;

    @Value("${redis.redisPoolMinIdle}")
    private int redisPoolMinIdle;

    @Value("${redis.redisPoolMaxIdle}")
    private int redisPoolMaxIdle;

	@Bean
	JedisConnectionFactory getJedisConnectionFactory() {
		/*OLD CONNECTION - TESTED
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisServer);
		jedisConnectionFactory.setPort(redisPort);
		return jedisConnectionFactory;*/
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisPoolMaxTotal);
        poolConfig.setMinIdle(redisPoolMinIdle);
        poolConfig.setMaxIdle(redisPoolMaxIdle);

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisServer, redisPort);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return factory;

		/*CONNEDCTION POOLING -- NEED TO BE TEST
		JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
		factory.setHostName(redisServer);
		factory.setUsePool(true);
		factory.setPort(redisPort);
		return factory;*/
	}

	@Bean
    RedisTemplate<String, ?> getRedisTemplate() {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(getJedisConnectionFactory());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}
}

