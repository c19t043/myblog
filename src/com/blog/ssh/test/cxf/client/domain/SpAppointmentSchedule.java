package com.blog.ssh.test.cxf.client.domain;

import java.io.Serializable;

public class SpAppointmentSchedule implements Serializable{
	
	private Long id;
	private String registerID;//安排ID
	private String registerNO;//安排号
	private String registerName;//安排名
	private String typeID;//类别编码
	private String typeName;//类别名称
	private String depID;//科室ID
	private String depName;//科室名称
	private String doctorID;//医生ID
	private String doctor;//医生名称
	private String exTime;//出诊时间
	private String money;//金额
	
	//----------------------查询条件
	private String sp_OperType;
	private String sp_OrgCode;
	private String sp_QueryString;
	//----------------------查询条件
	
	public Long getId() {
		return id;
	}
	public String getSp_OperType() {
		return sp_OperType;
	}
	public void setSp_OperType(String sp_OperType) {
		this.sp_OperType = sp_OperType;
	}
	public String getSp_OrgCode() {
		return sp_OrgCode;
	}
	public void setSp_OrgCode(String sp_OrgCode) {
		this.sp_OrgCode = sp_OrgCode;
	}
	public String getSp_QueryString() {
		return sp_QueryString;
	}
	public void setSp_QueryString(String sp_QueryString) {
		this.sp_QueryString = sp_QueryString;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRegisterID() {
		return registerID;
	}
	public void setRegisterID(String registerID) {
		this.registerID = registerID;
	}
	public String getRegisterNO() {
		return registerNO;
	}
	public void setRegisterNO(String registerNO) {
		this.registerNO = registerNO;
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDepID() {
		return depID;
	}
	public void setDepID(String depID) {
		this.depID = depID;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDoctorID() {
		return doctorID;
	}
	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getExTime() {
		return exTime;
	}
	public void setExTime(String exTime) {
		this.exTime = exTime;
	}
}
