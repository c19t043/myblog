package com.blog.ssh.test.cxf.client.domain;

import java.io.Serializable;

public class SpDoctorInfo implements Serializable{
	private static final long serialVersionUID = -6119019134392751709L;
	
	private Long id;
	private String doctorID;//医生ID
	private String doctor;//医生名称
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}
