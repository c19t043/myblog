package com.blog.ssh.test.cxf.client.domain;

public class SPAppointmentSchedule {
	
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
