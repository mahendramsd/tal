/*
 *  Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *  *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *    This software product contains information which is proprietary to
 *    and considered a trade secret The Data management Group Ltd .
 *    It is expressly agreed that it shall not be reproduced in whole or part,
 *    disclosed, divulged or otherwise made available to any third party directly
 *    or indirectly.  Reproduction of this product for any purpose is prohibited
 *    without written authorisation from the The Data Management Group Ltd
 *    All Rights Reserved.
 *
 *    E-Mail andyj@datam.co.uk
 *    URL : www.datam.co.uk
 *    Created By : Asanka Anthony
 *
 */
package lk.dmg.talcacheoperational.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
       /* JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisPoolMaxTotal);
        poolConfig.setMinIdle(redisPoolMinIdle);
        poolConfig.setMaxIdle(redisPoolMaxIdle);*/

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
    RedisTemplate<String,?> getRedisTemplate() {
        RedisTemplate<String,?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
