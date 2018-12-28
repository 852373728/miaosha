package com.sun.result;

public class CodeMsg {

	private int code;
	private String msg;
	
	/**
	 * 成功返回
	 */
	public static CodeMsg SUCCESS=new CodeMsg(0,"success");
	/**
	 * 通用异常
	 */
	public static CodeMsg SERVER_ERROR=new CodeMsg(5001,"未定义正确错误，通用服务异常");
	/**
	 * 字符串格式化异常
	 */
	public static CodeMsg ERROR_STRFORMAT=new CodeMsg(5001,"%S");
	
	
	//用户登录模块
	public static CodeMsg ERROR_LOGIN=new CodeMsg(100100,"密码或账号有误");
	
	public static CodeMsg KCBZ=new CodeMsg(500200,"库存不足");
	public static CodeMsg CFMS=new CodeMsg(500201,"你已秒杀过该商品");
	
	public static CodeMsg DLCS=new CodeMsg(500201,"请求过于频繁");
	
	
	private CodeMsg(int code, String msg) {
		this.code=code;
		this.msg=msg;
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	public CodeMsg format(Object... args) {
		String format = String.format(msg, args);
		return new CodeMsg(code, format);
	}
	
	
	
}
