package util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import bean.User;
import test.TestProperties;
@SuppressWarnings("all")
public class DataBaseUtils {

	/**
	 * DML操作
	 * 
	 * @param args
	 */
	public static void update(String sql,Object...objects) {
		Connection conn = JdbcUtils.getMysqlCon();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			for(int i=0; i<objects.length; i++) {
				ps.setObject(i+1, objects[i]);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.close(rs, ps, conn);
		}
	}
	
	/**
	 * 查询出数据，并且list返回
	 * 这个可以实现查询多数据
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static List<Map<String,Object>> queryForList(String sql,Object...objects){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Connection conn = JdbcUtils.getMysqlCon();
//		Connection conn = null 这里出错了，之前忘了先链接数据库，所以会报空指针
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps = conn.prepareStatement(sql);
			
			for(int i = 0;i < objects.length; i++) {
				ps.setObject(i+1, objects[i]);
			}
			
			rs=ps.executeQuery();
			
			while(rs.next()) {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();//得到结果集(rs)的结构，比如字段数、字段名等。
				int count = resultSetMetaData.getColumnCount();//获取列数
				Map<String,Object> map = new HashMap<String,Object>();
				for(int i=0;i<count;i++) {
					map.put(resultSetMetaData.getColumnName(i+1), rs.getObject(resultSetMetaData.getColumnName(i+1)));
				}
				result.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.close(rs, ps, conn);
		}
		return result;
	}
	
	/**
	 * 查询出数据 并用map返回
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static Map<String,Object> queryForMap(String sql,Object...objects){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list =  queryForList(sql,objects);
		if(list.size() !=1) {
			return null;
		}
		result = list.get(0);
		return result;
	} 
	
	/**
	 * 查询出数据，并且返回一个JavaBean
	 * @param sql
	 * @param clazz
	 * @param objects
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static <T>T queryForBean(String sql,Class clazz,Object...objects) throws SQLException{
		T obj = null;
		Map<String,Object> map = null;
		Field field = null;
		try {
			obj = (T) clazz.newInstance();   //创建一个新的Bean实例
			map = queryForMap(sql, objects); //先将结果集放在一个Map中
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if(map == null){
			return null;
		}
		//遍历Map
		for (String columnName : map.keySet()) {
			Method method = null;
			String propertyName = StringUtils.columnToProperty(columnName); //属性名称
			
			try {
				field = clazz.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} 
			//获取JavaBean中的字段
			String fieldType = field.toString().split(" ")[1]; //获取该字段的类型
			//System.out.println(fieldType);
			Object value = map.get(columnName);
			if(value == null){
				continue;
			}
			/**获取set方法的名字* */
			String setMethodName = "set" + StringUtils.upperCaseFirstCharacter(propertyName);
			//System.out.println(setMethodName);
			try {
				/**获取值的类型* */
				String valueType = value.getClass().getName();
				
				/**查看类型是否匹配* */
				if(!fieldType.equalsIgnoreCase(valueType)){
					//System.out.println("类型不匹配");
					if(fieldType.equalsIgnoreCase("java.lang.Integer")){
						value = Integer.parseInt(String.valueOf(value));
					}else if(fieldType.equalsIgnoreCase("java.lang.String")){
						value = String.valueOf(value);
					}else if(fieldType.equalsIgnoreCase("java.util.Date")){
						valueType = "java.util.Date";
						//将value转换成java.util.Date
						String dateStr = String.valueOf(value);
						Timestamp ts = Timestamp.valueOf(dateStr);
						Date date = new Date(ts.getTime());
						value = date;
					}
				}
				
				/**获取set方法* */
				//System.out.println(valueType);
				method = clazz.getDeclaredMethod(setMethodName,Class.forName(fieldType));
				/**执行set方法* */
				method.invoke(obj, value);
			}catch(Exception e){
				/**如果报错，基本上是因为类型不匹配* */
				e.printStackTrace();
			}
		}
		//System.out.println(obj);
		return obj;
	}

	
	
	
	
	/**
	 * 测试
	 * @param args
	 * @throws SQLException 
	 */
	
	public static void main(String[] args) throws SQLException {
		Connection conn = JdbcUtils.getMysqlCon();
//		System.out.println(conn);
//		String id = UUID.randomUUID() + "";
//	String createTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//	update("INSERT INTO t_user(id,username,password,sex,create_time,is_delete,address,telephone) "
//		        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", id,"张三",123456,0,createTime,0,"保密","保密");
//		Map map = DataBaseUtils.queryForMap("select * from t_user");
//		System.out.println(map);
		User user = DataBaseUtils.queryForBean("select * from t_user  limit 1", User.class);
		System.out.println(user);
	}
}
