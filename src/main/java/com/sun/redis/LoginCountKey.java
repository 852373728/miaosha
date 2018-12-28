package com.sun.redis;

/**
 * 登录次数key
 * @author sunqilin
 *
 */

public class LoginCountKey extends BasePrefix{

	private LoginCountKey(int expireSecond, String prefix) {
		super(expireSecond, prefix);
		// TODO Auto-generated constructor stub
	}
	
	public static LoginCountKey lc(int seconds) {
		return new LoginCountKey(seconds, "dlcs");
	}
	

}
