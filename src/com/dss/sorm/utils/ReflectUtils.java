package com.dss.sorm.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
@SuppressWarnings("all")
/**
 * 封装了反射常用的操作 
 * @author 杜森森
 *
 */
public class ReflectUtils {
	/**
	 * 调用obj对象对应属性fieldName的get方法
	 * @param c
	 * @param fieldName
	 * @param obj
	 * @return
	 */
	public static Object invokeGet(String fieldName,Object obj) {
		try {
			Class c = obj.getClass();
			Method m =c.getMethod("get"+StringUtils.firstChar2UpperCase(fieldName), null);
			return m.invoke(obj, null); 
			

		} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	public static void invokeSet(Object obj,String columnName,Object columnValue) {
		try {
			if(columnValue!=null) {
				Method m = obj.getClass().getDeclaredMethod("set"+StringUtils.firstChar2UpperCase(columnName), 
						columnValue.getClass());
				m.invoke(obj, columnValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
	}
}

