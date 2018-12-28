package com.sun.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisPoolFactory {

	@Autowired
	RedisConfig redisConfig;
	
	@Bean
	public JedisPool jedisFactory() {
		JedisPoolConfig jpc=new JedisPoolConfig();
		jpc.setMaxIdle(redisConfig.getPoolMaxIdle());
		jpc.setMaxTotal(redisConfig.getPoolMaxTotal());
		jpc.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
		JedisPool jp=new JedisPool(jpc,redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout()*1000,redisConfig.getPassword(),0);
		return jp;
	}
	
}
