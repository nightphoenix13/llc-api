package com.cooksys.llc.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class AppConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(5);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		JedisConnectionFactory ob = new JedisConnectionFactory(poolConfig);
		ob.setUsePool(true);
		ob.setHostName("localhost");
		ob.setPort(6379);
		ob.afterPropertiesSet();
		return ob;
	}
}