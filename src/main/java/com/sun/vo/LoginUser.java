package com.sun.vo;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class LoginUser {
	
	@NotBlank(message="账号不能为空")
	//@Pattern(regexp="1\\d{10}",message="请输入正确的手机格式")
	private String id;
	@NotBlank(message="密码不能为空")
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
