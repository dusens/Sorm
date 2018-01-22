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
public class MySqlQuery extends Query {
	
	public static void testDML() {
		Emp e = new Emp();
		
//		 new MySqlQuery().delete(e);
		e.setEmpname("lily");
		e.setBirthday(new java.sql.Date(System.currentTimeMillis()));
		e.setSalary(3000.8);
		e.setAge(15);
		e.setId(4);
//		new MySqlQuery().insert(e);
//		new MySqlQuery().update(e,new String[]{"empname","age","salary"});
	}
	public static void testQueryRows() {
			List<Emp> list =new MySqlQuery().queryRows("select id,empname,age from emp where age>? and salary>?",
				Emp.class, new Object[]{10,2000});
		for(Emp e:list) {
//			System.out.println(e.getEmpname());
		}
		String sql2 = "select e.id,e.empname,salary+bonus 'xinshui',age,d.dname 'deptName',d.address 'deptAddr' from emp e "
				+"join dept d on e.deptId=d.id ";
		List<EmpVo> list2 = new MySqlQuery().queryRows(sql2,EmpVo.class,null);
						
		for(EmpVo e:list2){
				System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getXinshui());
			}
	}
	public static void main(String[] args) {
//		EmpVo s = new EmpVo();
		//这里在查询的时候如果下边报
		//java.lang.NullPointerException
		//则证明数据上边的数据不完整，有的为null;
//				
		Number obj = (Number)new MySqlQuery().queryValue("select count(*) from emp where salary>?", new Object[] {2000});
		System.out.println(obj.doubleValue());
//		testQueryRows();
		
		}
	@Override
	public Object queryPagenate(int pageNum, int size) {
		// TODO Auto-generated method stub
		return null;
	}
	}
