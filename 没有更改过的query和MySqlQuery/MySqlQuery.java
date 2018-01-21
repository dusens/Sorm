package com.dss.sorm.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.dss.po.Emp;
import com.dss.sorm.bean.ColumnInfo;
import com.dss.sorm.bean.TableInfo;
import com.dss.sorm.utils.JDBCutils;
import com.dss.sorm.utils.ReflectUtils;
import com.dss.sorm.utils.StringUtils;
import com.dss.vo.EmpVo;
/**
 * 负责针对MySQL数据库的查询(对外提供服务的核心类)
 * @author 杜森森
 *
 */
@SuppressWarnings("all")
public class MySqlQuery implements Query {
	
	public static void testDML() {
		Emp e = new Emp();
		
//		 new MySqlQuery().delete(e);
		e.setEmpname("lily");
		e.setBirthday(new java.sql.Date(System.currentTimeMillis()));
		e.setSalary(3000.8);
		e.setAge(15);
		e.setId(4);
//		new MySqlQuery().insert(e);
		new MySqlQuery().update(e,new String[]{"empname","age","salary"});
	}
	public static void main(String[] args) {
//		EmpVo s = new EmpVo();
		//这里在查询的时候如果下边报
		//java.lang.NullPointerException
		//则证明数据上边的数据不完整，有的为null;
//		List<Emp> list =new MySqlQuery().queryRows("select id,empname,age from emp where age>? and salary>?",
//				Emp.class, new Object[]{10,2000});
//		for(Emp e:list) {
//			System.out.println(e.getEmpname());
//		}
//		String sql2 = "select e.id,e.empname,salary+bonus 'xinshui',age,d.dname 'deptName',d.address 'deptAddr' from emp e "
//				+"join dept d on e.deptId=d.id ";
//		List<EmpVo> list2 = new MySqlQuery().queryRows(sql2,EmpVo.class,null);
//						
//		for(EmpVo e:list2){
//				System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getXinshui());
//			}
					
		Object obj = new MySqlQuery().queryValue("select count(*) from emp where salary>?", new Object[] {1000});
		System.out.println((Number)obj);
		}
	
	@Override
	public int executeDML(String sql, Object[] params) {
		Connection conn = DBManager.getConn();
		int count = 0;
		
		PreparedStatement ps = null;;
		
		try {
			ps = conn.prepareStatement(sql);
			System.out.println(ps);
			JDBCutils.handleParams(ps, params);
			
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(ps, conn);
		}
		
		
		
		return count ;
	
	}

	@Override
	public void insert(Object obj) {
		//obj-->表中。             insert into 表名  (id,uname,pwd) values (?,?,?)
				Class c = obj.getClass();
				List<Object> params = new ArrayList<Object>();   //存储sql的参数对象
				TableInfo tableInfo = TableContext.poClassTableMap.get(c);
				StringBuilder sql  = new StringBuilder("insert into "+tableInfo.getTname()+" (");
				int countNotNullField = 0;   //计算不为null的属性值
				Field[] fs = c.getDeclaredFields();
				for(Field f:fs){
					String fieldName = f.getName();
					Object fieldValue = ReflectUtils.invokeGet(fieldName, obj);
					
					if(fieldValue!=null){
						countNotNullField++;
						sql.append(fieldName+",");
						params.add(fieldValue);
					}
				}
				
				sql.setCharAt(sql.length()-1, ')');
				sql.append(" values (");
				for(int i=0;i<countNotNullField;i++){
					sql.append("?,");
				}
				sql.setCharAt(sql.length()-1, ')');
				
				executeDML(sql.toString(), params.toArray());
			}

	@Override
	public void delete(Class clazz, Object id) {
		//Emp.class,2-->delete from emp where id=2
				//通过Class对象找TableInfo
				TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
				//获得主键
				ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
				
				String sql = "delete from "+tableInfo.getTname()+" where "+onlyPriKey.getName()+"=? ";
				
				executeDML(sql, new Object[]{id});
		
	}

	@Override
	public void delete(Object obj) {
		Class c = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();  //主键
		
		//通过反射机制，调用属性对应的get方法或set方法
		Object priKeyValue = ReflectUtils.invokeGet(onlyPriKey.getName(), obj);

		delete(c, priKeyValue);
	}

	
	
	

	@Override
	public int update(Object obj, String[] fieldNames) {
		//obj{"uanme","pwd"}-->update 表名  set uname=?,pwd=? where id=?
				Class c = obj.getClass();
				List<Object> params = new ArrayList<Object>();   //存储sql的参数对象
				TableInfo tableInfo = TableContext.poClassTableMap.get(c);
				ColumnInfo  priKey = tableInfo.getOnlyPriKey();   //获得唯一的主键
				StringBuilder sql  = new StringBuilder("update "+tableInfo.getTname()+" set ");
				//这里的update少了a报错
				
				for(String fname:fieldNames){
					Object fvalue = ReflectUtils.invokeGet(fname,obj);
					params.add(fvalue);
					sql.append(fname+"=?,");
				}
				sql.setCharAt(sql.length()-1, ' ');
				sql.append(" where ");
				sql.append(priKey.getName()+"=? ");
				
				params.add(ReflectUtils.invokeGet(priKey.getName(), obj));    //主键的值
				
				return executeDML(sql.toString(), params.toArray()); 
			}

	@Override
	public List queryRows(String sql, Class clazz, Object[] params) {
		Connection conn = DBManager.getConn();
		List list = null;
		PreparedStatement ps = null;;
		ResultSet rs = null;
		try {
			
			
			ps = conn.prepareStatement(sql);
			System.out.println(ps);
			JDBCutils.handleParams(ps, params);
			
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();//里面包含了列的信息
			while(rs.next()) {
				if(list==null) {
					list = new ArrayList();
				}
				Object rowObj = clazz.newInstance(); //javabean的无参构造器
				//多列 select username,pwd,age from user where id>? and age>18
				
				for(int i = 0;i<metaData.getColumnCount();i++){
					String columnName = metaData.getColumnLabel(i+1);//username
					Object columnValue = rs.getObject(i+1);
					
					//调用rowObj对象的setUsername(String uname)方法,将columnValue的值射进去
					ReflectUtils.invokeSet(rowObj, columnName, columnValue);
				}
				list.add(rowObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(ps, conn);
		}
		
		
		
		return list;
	}
	/**
	 * 只有一个元素取出一个元素来
	 */
	@Override
	public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
		List list = queryRows(sql, clazz, params);
		return(list==null&&list.size()>0)?null:list.get(0);
		
	}

	@Override
	public Object queryValue(String sql, Object[] params) {
		
		Connection conn = DBManager.getConn();
		Object value = null; //存储查询结果的对象
		PreparedStatement ps = null;;
		ResultSet rs = null;
		try {
			
			
			ps = conn.prepareStatement(sql);
			System.out.println(ps);
			JDBCutils.handleParams(ps, params);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				//select count(*) from user
				
				value = rs.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(ps, conn);
		}
		
		
		
		return value;
	}

	@Override
	public Number queryNumber(String sql, Object[] params) {
		
		return (Number)queryValue(sql, params);
	}

}