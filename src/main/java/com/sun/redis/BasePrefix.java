package com.sun.redis;

public abstract class BasePrefix implements KeyPrefix{

	private int expireSecond;//过期时间
	
	private String prefix;//key值
	
	
	
	public BasePrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}

	public BasePrefix(int expireSecond, String prefix) {
		this.expireSecond = expireSecond;
		this.prefix = prefix;
	}

	
	/**
	 * 过期时间
	 */
	@Override
	public int expireSeconds() {
		return expireSecond;
	}

	/**
	 * 设置redis的key
	 */
	@Override
	public String getPrefix() {
		String simpleName = getClass().getSimpleName();
		return simpleName+":"+prefix;
	}

}
