package com.dss.sorm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.dss.pool.DBConnPool;
import com.dss.sorm.bean.Configuration;

/**
 * 根据配置信息，维持链接对象的管理(增加连接池功能)
 * @author 杜森森
 *
 */
public class DBManager {
	private static Configuration conf;
	/**
	 * 连接池对象
	 */
	private static  DBConnPool pool ;
	static {  //静态代码块
		Properties pros = new Properties();
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		conf = new Configuration();
		conf.setDriver(pros.getProperty("driver"));
		conf.setPoPackage(pros.getProperty("poPackage"));
		conf.setPwd(pros.getProperty("pwd"));
		conf.setSrcPath(pros.getProperty("srcPath"));
		conf.setUrl(pros.getProperty("url"));
		conf.setUser(pros.getProperty("user"));
		conf.setUsingDB(pros.getProperty("usingDB"));
		conf.setQueryClass(pros.getProperty("queryClass"));
		conf.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
		conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));
		/**
		 * 加载tablecontext
		 */
		System.out.println(TableContext.class);
}
	
	
	/**
	 * 创建新的connection对象
	 * @return
	 */
	public static Connection createConn(){
		try {
			Class.forName(conf.getDriver());
			return DriverManager.getConnection(conf.getUrl(),
					conf.getUser(),conf.getPwd());     //直接建立连接，后期增加连接池处理，提高效率！！！
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取connection对象
	 * @return
	 */
	public static Connection getConn(){
		if(pool==null) {			
			pool=new DBConnPool();
		}
		return pool.getConnection();
	}
	
	/**
	 * 关闭的方法
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public static void close(ResultSet rs,Statement ps,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(ps!=null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		try {
//			if(conn!=null){
//				conn.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		pool.close(conn);
	}
	
	public static void close(Statement ps,Connection conn){
		try {
			if(ps!=null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pool.close(conn);
	}
	public static void close(Connection conn){
		
			pool.close(conn);
		
	}
	
	/**
	 * 返回Configuration对象
	 * @return
	 */
	public static Configuration getConf(){
		return conf;
	}
	
	
}