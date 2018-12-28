package com.sun.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.dao.UserDao;
import com.sun.entity.User;
import com.sun.exception.MyselfException;
import com.sun.redis.RedisService;
import com.sun.redis.UserKey;
import com.sun.result.CodeMsg;
import com.sun.util.MD5Util;
import com.sun.util.UUIDUtil;
import com.sun.vo.LoginUser;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisService redisService;
	
	public static final String TOKEN="token";
	
	public String yzLogin(LoginUser loginUser,HttpServletResponse response) {
		User user = userDao.login(loginUser.getId());
		if(user==null) {
			throw new MyselfException(CodeMsg.ERROR_LOGIN);
		}
		String password = user.getPassword();
		String dbPassword = MD5Util.formPassword(loginUser.getPassword(), user.getSalt());
		if(!password.equals(dbPassword)) {
			throw new MyselfException(CodeMsg.ERROR_LOGIN);
		}
		String uuid=UUIDUtil.uuid();
		addCookie(uuid,response,user);
		return uuid;
	}
	
	public User getByTokenValue(HttpServletResponse response,String tokenValue) {
		User user = redisService.get(UserKey.getBytoken,tokenValue, User.class);
		if(user==null) {
			return null;
		}
		addCookie(tokenValue, response, user);
		return user;
	}
	
	private void addCookie(String uuid,HttpServletResponse response,User user) {
		redisService.set(UserKey.getBytoken,uuid,user);
		Cookie cookie=new Cookie(TOKEN, uuid);
		cookie.setPath("/");
		cookie.setMaxAge(UserKey.getBytoken.expireSeconds());
		response.addCookie(cookie);
	}
	
	
	
	
	

}
