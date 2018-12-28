package com.sun.util;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}
	
	private static final String salt="1a2b3c4d";
	
	public static String inputPassword(String inputPass) {
		String str=""+salt.charAt(3)+salt.charAt(1)+inputPass+salt.charAt(5) ;
		return md5(str);
	}
	
	/**
	 * java加密
	 * @param str
	 * @return
	 */
	public static String formPassword(String formPass,String salt) {
		String str=""+salt.charAt(3)+salt.charAt(1)+formPass+salt.charAt(5) ;
		return md5(str);
	}
	
	public static String dbPassword(String str,String salt) {
		return formPassword(inputPassword(str), salt);
	}
	
	
	public static void main(String[] args) {
		System.out.println(formPassword("b51605e3de7db2945551263153eb8ab7", salt));
		
	}
	
}
