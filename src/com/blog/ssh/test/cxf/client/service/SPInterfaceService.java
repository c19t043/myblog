package com.blog.ssh.test.cxf.client.service;

import java.util.List;

import com.blog.ssh.test.cxf.client.domain.SpAppointmentSchedule;
import com.blog.ssh.test.cxf.client.domain.SpDoctorInfo;
import com.blog.ssh.test.cxf.client.domain.SpVisitRecord;

public interface SPInterfaceService {
	/**  获取挂号安排  */
	public List<SpAppointmentSchedule> getSPAppointmentSchedule()throws Exception;
	/**  获取医生列表  */
	public List<SpDoctorInfo> getSPDoctorInfos();
	/**  获取就诊记录  */
	public List<SpVisitRecord> getSpVisitRecord();
	/**  获取医嘱信息  */
}
