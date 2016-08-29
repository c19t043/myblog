package com.blog.ssh.test.cxf.client.domain;

/**
 * SpVisitRecord entity. @author MyEclipse Persistence Tools
 */

public class SpVisitRecord implements java.io.Serializable {

	// Fields

	private Long id;
	private String eventID;
	private String registeNo;
	private String residentID;
	private String residentName;
	private String sexCD;
	private String birthDay;
	private String paperNum;
	private String visitTime;
	private String doctorID;
	private String doctor;
	private String state;

	//--------------------------------查询信息
	private String sp_OperType;//操作类型编号
	private String sp_OrgCode;//机构ID
	private String sp_ResidentID;//个人ID
	private String sp_BeginDT;//开始时间
	private String sp_EndDT;//结束时间
	//--------------------------------
	// Constructors

	/** default constructor */
	public SpVisitRecord() {
	}


	// Property accessors

	public Long getId() {
		return this.id;
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

	public String getSp_ResidentID() {
		return sp_ResidentID;
	}

	public void setSp_ResidentID(String sp_ResidentID) {
		this.sp_ResidentID = sp_ResidentID;
	}

	public String getSp_BeginDT() {
		return sp_BeginDT;
	}

	public void setSp_BeginDT(String sp_BeginDT) {
		this.sp_BeginDT = sp_BeginDT;
	}

	public String getSp_EndDT() {
		return sp_EndDT;
	}

	public void setSp_EndDT(String sp_EndDT) {
		this.sp_EndDT = sp_EndDT;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getRegisteNo() {
		return this.registeNo;
	}

	public void setRegisteNo(String registeNo) {
		this.registeNo = registeNo;
	}


	public String getResidentName() {
		return this.residentName;
	}

	public void setResidentName(String residentName) {
		this.residentName = residentName;
	}

	public String getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getPaperNum() {
		return this.paperNum;
	}

	public void setPaperNum(String paperNum) {
		this.paperNum = paperNum;
	}

	public String getVisitTime() {
		return this.visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getDoctor() {
		return this.doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getEventID() {
		return eventID;
	}


	public void setEventID(String eventID) {
		this.eventID = eventID;
	}


	public String getResidentID() {
		return residentID;
	}


	public void setResidentID(String residentID) {
		this.residentID = residentID;
	}


	public String getSexCD() {
		return sexCD;
	}


	public void setSexCD(String sexCD) {
		this.sexCD = sexCD;
	}


	public String getDoctorID() {
		return doctorID;
	}


	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}

}