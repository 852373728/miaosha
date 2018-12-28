package com.sun.redis;

public class OrderKey extends BasePrefix{

	public OrderKey(String prefix) {
		super(prefix);
	}
	

	public static OrderKey orderkey=new OrderKey("goods");
	
	public static OrderKey goodsOver=new OrderKey("goodsOver");

}
