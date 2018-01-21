package com.dss.sorm.core;
/**
 * 负责根据配置信息创建query对象
 * 创建Query对象的工厂类
 * @author 杜森森
 *
 */
public class QueryFactory {
	private static Query prototypeObj;//原型对象
	private static QueryFactory factory = new QueryFactory();
	
	static {
		try {
		Class c	= Class.forName(DBManager.getConf().getQueryClass()) ;//加载指定的query类
		prototypeObj = (Query)c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private QueryFactory() {
		
	}
	public static Query createQuery() {
		
		try {
			return (Query) prototypeObj.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
