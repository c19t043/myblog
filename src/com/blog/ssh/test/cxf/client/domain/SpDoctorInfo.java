package com.blog.ssh.test.cxf.client.domain;

/**
 * SpDoctorInfo entity. @author MyEclipse Persistence Tools
 */

public class SpDoctorInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String doctorId;
	private String doctorName;
	private String deptId;
	private String deptName;
	private Long localDoctorId;
	private Long localOrgId;
	//--------------查询条件
	private String sp_OperType;
	private String sp_OrgCode;
	private String sp_Type;
	private String sp_QueryString;
	// Constructors

	/** default constructor */
	public SpDoctorInfo() {
	}

	/** full constructor */
	public SpDoctorInfo(String doctorId, String doctorName, String deptId,
			String deptName, Long localDoctorId, Long localOrgId) {
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.deptId = deptId;
		this.deptName = deptName;
		this.localDoctorId = localDoctorId;
		this.localOrgId = localOrgId;
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

	public String getSp_Type() {
		return sp_Type;
	}

	public void setSp_Type(String sp_Type) {
		this.sp_Type = sp_Type;
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

	public String getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return this.doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getLocalDoctorId() {
		return this.localDoctorId;
	}

	public void setLocalDoctorId(Long localDoctorId) {
		this.localDoctorId = localDoctorId;
	}

	public Long getLocalOrgId() {
		return this.localOrgId;
	}

	public void setLocalOrgId(Long localOrgId) {
		this.localOrgId = localOrgId;
	}

}