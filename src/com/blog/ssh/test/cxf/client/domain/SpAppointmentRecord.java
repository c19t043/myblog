package com.blog.ssh.test.cxf.client.domain;

/**
 * SpAppointmentRecord entity. @author MyEclipse Persistence Tools
 */

public class SpAppointmentRecord implements java.io.Serializable {

	// Fields

	private Long id;
	private String registerNo;
	private String eventId;

	//----------------------------------查询条件
	 private String sp_OperType;
	 private String sp_OrgCode;
	 private String sp_RegisterID;
	 private String sp_RegisterName;
	 private String sp_DoctorID;
	 private String sp_Doctor;
	 private String sp_Type;
	 private String sp_IsFree;
	 private String sp_RegisterDate;
	 private String sp_DepID;
	 private String sp_DepName;
	 private String sp_ResidentID;
	 private String sp_ResidentName;
	 private String sp_UserID;
	 private String sp_UserName;
	//----------------------------------
	
	// Constructors

	/** default constructor */
	public SpAppointmentRecord() {
	}

	/** full constructor */
	public SpAppointmentRecord(String registerNo, String eventId) {
		this.registerNo = registerNo;
		this.eventId = eventId;
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

	public String getSp_RegisterID() {
		return sp_RegisterID;
	}

	public void setSp_RegisterID(String sp_RegisterID) {
		this.sp_RegisterID = sp_RegisterID;
	}

	public String getSp_RegisterName() {
		return sp_RegisterName;
	}

	public void setSp_RegisterName(String sp_RegisterName) {
		this.sp_RegisterName = sp_RegisterName;
	}

	public String getSp_DoctorID() {
		return sp_DoctorID;
	}

	public void setSp_DoctorID(String sp_DoctorID) {
		this.sp_DoctorID = sp_DoctorID;
	}

	public String getSp_Doctor() {
		return sp_Doctor;
	}

	public void setSp_Doctor(String sp_Doctor) {
		this.sp_Doctor = sp_Doctor;
	}

	public String getSp_Type() {
		return sp_Type;
	}

	public void setSp_Type(String sp_Type) {
		this.sp_Type = sp_Type;
	}

	public String getSp_IsFree() {
		return sp_IsFree;
	}

	public void setSp_IsFree(String sp_IsFree) {
		this.sp_IsFree = sp_IsFree;
	}

	public String getSp_RegisterDate() {
		return sp_RegisterDate;
	}

	public void setSp_RegisterDate(String sp_RegisterDate) {
		this.sp_RegisterDate = sp_RegisterDate;
	}

	public String getSp_DepID() {
		return sp_DepID;
	}

	public void setSp_DepID(String sp_DepID) {
		this.sp_DepID = sp_DepID;
	}

	public String getSp_DepName() {
		return sp_DepName;
	}

	public void setSp_DepName(String sp_DepName) {
		this.sp_DepName = sp_DepName;
	}

	public String getSp_ResidentID() {
		return sp_ResidentID;
	}

	public void setSp_ResidentID(String sp_ResidentID) {
		this.sp_ResidentID = sp_ResidentID;
	}

	public String getSp_ResidentName() {
		return sp_ResidentName;
	}

	public void setSp_ResidentName(String sp_ResidentName) {
		this.sp_ResidentName = sp_ResidentName;
	}

	public String getSp_UserID() {
		return sp_UserID;
	}

	public void setSp_UserID(String sp_UserID) {
		this.sp_UserID = sp_UserID;
	}

	public String getSp_UserName() {
		return sp_UserName;
	}

	public void setSp_UserName(String sp_UserName) {
		this.sp_UserName = sp_UserName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegisterNo() {
		return this.registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getEventId() {
		return this.eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

}