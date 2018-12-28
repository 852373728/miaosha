package com.sun.redis;

public interface KeyPrefix {
	
	//有效期
	public int expireSeconds();
	
	//redis的key值
	public String getPrefix();

}
