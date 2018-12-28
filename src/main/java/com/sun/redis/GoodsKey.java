package com.sun.redis;

public class GoodsKey extends BasePrefix{

	private GoodsKey(String prefix) {
		super(prefix);
	}
	
	public static GoodsKey goodsStock=new GoodsKey("goodsStock");

}
