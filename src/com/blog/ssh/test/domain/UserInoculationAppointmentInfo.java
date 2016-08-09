package com.blog.ssh.test.domain;

import com.sun.jna.platform.win32.Netapi32Util.UserInfo;

/**
 * UserInoculationAppointmentInfo entity. @author MyEclipse Persistence Tools
 */

public class UserInoculationAppointmentInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String sort;
	private String appointmentCode;
	private String measuringTemperature;
	private String nextVaccinationDate;
	private String status;
	private String optTime;
	
	//查询条件用
	private String openBeginDate;
	private String openEndDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getAppointmentCode() {
		return appointmentCode;
	}
	public void setAppointmentCode(String appointmentCode) {
		this.appointmentCode = appointmentCode;
	}
	public String getMeasuringTemperature() {
		return measuringTemperature;
	}
	public void setMeasuringTemperature(String measuringTemperature) {
		this.measuringTemperature = measuringTemperature;
	}
	public String getNextVaccinationDate() {
		return nextVaccinationDate;
	}
	public void setNextVaccinationDate(String nextVaccinationDate) {
		this.nextVaccinationDate = nextVaccinationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOptTime() {
		return optTime;
	}
	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}
	public String getOpenBeginDate() {
		return openBeginDate;
	}
	public void setOpenBeginDate(String openBeginDate) {
		this.openBeginDate = openBeginDate;
	}
	public String getOpenEndDate() {
		return openEndDate;
	}
	public void setOpenEndDate(String openEndDate) {
		this.openEndDate = openEndDate;
	}
}