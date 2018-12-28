package com.sun.controller;



import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.rabbitMq.MQSender;
import com.sun.result.Result;
import com.sun.service.UserService;
import com.sun.vo.LoginUser;



@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	MQSender mQSender;
	
	
	@RequestMapping("/to_login")
	public String to_login() {
		return "login";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public Result<String> login(@Valid LoginUser loginUser,HttpServletResponse response){
		String yzLogin = userService.yzLogin(loginUser,response);
		return Result.success(yzLogin);
	}
	
	
	/*@RequestMapping("/mq")
	@ResponseBody
	public Result<String> mq(){
		mQSender.sendtopic("你好");
		return Result.success("成功");
	}*/
	
}
