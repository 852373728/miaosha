package com.sun.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
	
	@Autowired
	JedisPool jedisPool;
	
	/**
	 * 获得值
	 * @param keyPrefix
	 * @param clazz
	 * @return
	 */
	public <T> T get(KeyPrefix keyPrefix,String key,Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis=jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			String string = jedis.get(realKey);
			T t= stringToBean(string,clazz);
			return t;
		} finally {
			returnToPool(jedis);
		}
	}
	
	//字符串转换Bean
	@SuppressWarnings("unchecked")
	public static <T> T stringToBean(String string, Class<T> clazz) {
		if(string==null || string.length()<=0 || clazz==null) {
			return null;
		}
		if(clazz==int.class || clazz==Integer.class) {
			return (T) Integer.valueOf(string);
		}else if(clazz==String.class) {
			return (T) string;
		}else if(clazz==long.class || clazz==Long.class) {
			return (T) Long.valueOf(string);
		}else if(clazz==float.class || clazz==Float.class) {
			return (T) Float.valueOf(string);
		}else if(clazz==double.class || clazz==Double.class) {
			return (T) Double.valueOf(string);
		}else {
			return JSON.toJavaObject(JSON.parseObject(string), clazz);
		}
	}

	/**
	 * 设置值
	 * @param keyPrefix
	 * @param value
	 * @return
	 */
	public <T> boolean set(KeyPrefix keyPrefix,String key,T value) {
		Jedis jedis = null;
		try {
			jedis=jedisPool.getResource();
			String str= BeanToString(value);
			if(str==null || str.length()<=0) {
				return false;
			}
			int sec=keyPrefix.expireSeconds();
			String realKey=keyPrefix.getPrefix()+key;
			if(sec<=0) {
				jedis.set(realKey, str);
			}else {
				jedis.setex(realKey, sec, str);
			}
			return true;
		} finally {
			returnToPool(jedis);
		}
	}
	
	//bean转换string
	public static <T> String BeanToString(T value) {
		if(value==null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz==int.class || clazz==Integer.class) {
			return String.valueOf(value);
		}else if(clazz==String.class) {
			return (String) value;
		}else if(clazz==long.class || clazz==Long.class) {
			return String.valueOf(value);
		}else if(clazz==float.class || clazz==Float.class) {
			return String.valueOf(value);
		}else if(clazz==double.class || clazz==Double.class) {
			return String.valueOf(value);
		}else {
			return JSON.toJSONString(value);
		}
	}

	//返回连接池
	private void returnToPool(Jedis jedis) {
		if(jedis!=null) {
			jedis.close();
		}
	}
	
	/**
	 * 判断Key是否存在
	 * @param keyPrefix
	 * @return boolean
	 */
	public <T> boolean exist(KeyPrefix keyPrefix,String key){
		Jedis jedis = null;
		try {
			jedis=jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			return jedis.exists(realKey);
		} finally {
			returnToPool(jedis);
		}
		
	}
	
	/**
	 * 值为数字类型，增加1，其他类型返回数值1
	 * @param keyPrefix
	 * @return Long
	 */
	public <T> Long incr(KeyPrefix keyPrefix,String key){
		Jedis jedis = null;
		try {
			jedis=jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			return jedis.incr(realKey) ;
		} finally {
			returnToPool(jedis);
		}
	}
	
	/**
	 * 值为数字类型，值减1，其他类型返回数值-1
	 * @param keyPrefix
	 * @return Long
	 */
	public <T> Long decr(KeyPrefix keyPrefix,String key){
		Jedis jedis = null;
		try {
			jedis=jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			return jedis.decr(realKey);
		} finally {
			returnToPool(jedis);
		}
	}
	
}
