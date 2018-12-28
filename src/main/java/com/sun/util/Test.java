package com.sun.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class Test {
	
	public static void main(String[] args) {
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		String jsonString = JSON.toJSONString(list);
		System.out.println(jsonString);
	}

}
