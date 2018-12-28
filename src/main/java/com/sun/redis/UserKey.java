package com.sun.redis;

public class UserKey extends BasePrefix{

	private UserKey(int expireSecond, String prefix) {
		super(expireSecond, prefix);
	}

	private static final int expireSecond=3600*24*15;
	
	public static UserKey getBytoken=new UserKey(expireSecond,"token");

}
