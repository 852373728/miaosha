package com.sun.config;

import com.sun.entity.User;

public class UserContext {
	
	private static ThreadLocal<User> tlu=new ThreadLocal<User>();
	
	public static void setUser(User user) {
		tlu.set(user);
	}
	
	public static User getUser() {
		return tlu.get();
	}
	
	
}
