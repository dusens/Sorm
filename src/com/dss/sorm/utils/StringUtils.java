package com.dss.sorm.utils;
/**
 * 封装了字符串常用的操作
 * @author 杜森森
 *
 */
public class StringUtils {
	
	public static String firstChar2UpperCase(String str) {
		//abcd-->Abcd
				//abcd-->ABCD-->Abcd
				return str.toUpperCase().substring(0, 1)+str.substring(1);
			}
			
	
	
}
