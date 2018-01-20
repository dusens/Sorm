package com.dss.vo;

public class EmpVo {
//	SELECT e.id,e.empname,salary+bonus'xinshui' ,age,d.dname 'deptName',d.address 'deptName' FROM emp e
//	JOIN dept d on e.deptId=d.id;
	
	private Integer id;
	private String empname;
	
	private Double xinshui;
	
	private Integer age;
	
	private String deptName;
	
	private String deptAddr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Double getXinshui() {
		return xinshui;
	}

	public void setXinshui(Double xinshui) {
		this.xinshui = xinshui;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String depetName) {
		this.deptName = depetName;
	}

	public String getDeptAddr() {
		return deptAddr;
	}

	public void setDeptAddr(String deptAddr) {
		this.deptAddr = deptAddr;
	}

	public EmpVo(Integer id, String empname, Double xinshui, Integer age, String depetName, String deptAddr) {
		super();
		this.id = id;
		this.empname = empname;
		this.xinshui = xinshui;
		this.age = age;
		this.deptName = depetName;
		this.deptAddr = deptAddr;
	}
	public EmpVo() {
	}
}
