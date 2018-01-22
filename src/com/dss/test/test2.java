package com.dss.test;

import java.util.List;

import com.dss.sorm.core.Query;
import com.dss.sorm.core.QueryFactory;
import com.dss.vo.EmpVo;

public class test2 {
	/**
	 * 正常查询
	 */
	public static void test01() {
		Query q =QueryFactory.createQuery();
		String sql2 = "select e.id,e.empname,salary+bonus 'xinshui',age,d.dname 'deptName',d.address 'deptAddr' from emp e "
				+"join dept d on e.deptId=d.id ";
		List<EmpVo> list2 = q.queryRows(sql2,EmpVo.class,null);				
		for(EmpVo e:list2){
				System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getXinshui());
			}
	}
	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		for(int i = 0;i<3000;i++) {
			test01();
		}
		long b = System.currentTimeMillis();
		
		System.out.println((b-a));//不加连接池5708毫秒
		//加连接池为1565毫秒
	}
}
