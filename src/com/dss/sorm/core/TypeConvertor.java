package com.dss.sorm.core;
/**
 * 负责java数据类型和数据库数据类型的互相转换
 * @author 杜森森
 *
 */
public interface TypeConvertor {
	/**
	 * 将数据库数据类型转化成java的数据类型
	 * @param columnType
	 * @return
	 */
	public String databaseType2JavaType(String columnType);
	/**
	 * 负责将java数据类型转化为数据库数据类型
	 * @param javaDataType
	 * @return
	 */
	
	public String javaType2DatabaseType(String javaDataType);
}
