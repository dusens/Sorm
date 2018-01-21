package com.dss.test;

import java.util.List;

import com.dss.sorm.core.MySqlQuery;
import com.dss.sorm.core.Query;
import com.dss.sorm.core.QueryFactory;
import com.dss.vo.EmpVo;
/**
 * 客户端调用测试类
 * @author sensendu
 *
 */
public class Test {
	public static void main(String[] args) {
		Query q =QueryFactory.createQuery();
		String sql2 = "select e.id,e.empname,salary+bonus 'xinshui',age,d.dname 'deptName',d.address 'deptAddr' from emp e "
				+"join dept d on e.deptId=d.id ";
		List<EmpVo> list2 = q.queryRows(sql2,EmpVo.class,null);				
		for(EmpVo e:list2){
				System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getXinshui());
			}
	}
}
