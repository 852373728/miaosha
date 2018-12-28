package com.sun.zhujie;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.sun.config.UserContext;
import com.sun.entity.User;
import com.sun.redis.LoginCountKey;
import com.sun.redis.RedisService;
import com.sun.result.CodeMsg;
import com.sun.result.Result;
import com.sun.service.UserService;

@Service
public class Interceptor extends HandlerInterceptorAdapter{

	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			User user = getUser(request, response);
			UserContext.setUser(user);
			
			
			HandlerMethod hm=(HandlerMethod) handler;
			Dlcs dlcs = hm.getMethodAnnotation(Dlcs.class);
			if(dlcs==null) {
				return true;
			}
			int seconds = dlcs.seconds();
			int maxCount = dlcs.maxCount();
			boolean needLogin = dlcs.needLogin();
			String key = request.getRequestURI();
			if(needLogin) {
				if(user==null) {
					render(response,CodeMsg.ERROR_LOGIN);
					return false;
				}
				key+="_"+user.getId();
			}
		
			Integer count= redisService.get(LoginCountKey.lc(seconds), key,Integer.class);
			if(count==null) {
				redisService.set(LoginCountKey.lc(seconds), key, 1);
			}else if(count<maxCount) {
				redisService.incr(LoginCountKey.lc(seconds), key);
			}else {
				render(response,CodeMsg.DLCS);
				return false;
			}
		}
		return true;
	}
	
	
	private void render(HttpServletResponse response, CodeMsg cm) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		String str = JSON.toJSONString(Result.error(cm));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}


	private User getUser(HttpServletRequest request, HttpServletResponse response) {
		String paramTokenValue = request.getParameter(UserService.TOKEN);
		String cookieTokenValue = getCookie(request,UserService.TOKEN);
		if(StringUtils.isEmpty(paramTokenValue) && StringUtils.isEmpty(cookieTokenValue)) {
			return null;
		}
		String tokenValue=StringUtils.isEmpty(paramTokenValue)?cookieTokenValue:paramTokenValue;
		return userService.getByTokenValue(response, tokenValue);
	}
	
	private String getCookie(HttpServletRequest request, String token) {
		Cookie[] cookies = request.getCookies();
		if(cookies==null || cookies.length<=0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(token)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	
	
}
