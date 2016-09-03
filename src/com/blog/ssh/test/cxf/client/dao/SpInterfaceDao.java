package com.blog.ssh.test.cxf.client.dao;

import java.util.List;

import com.blog.ssh.base.dao.CommonDao;
import com.blog.ssh.test.cxf.client.domain.SpAppointmentSchedule;
import com.blog.ssh.test.cxf.client.domain.SpDoctorInfo;


public interface SpInterfaceDao extends CommonDao<SpAppointmentSchedule>{
	
	public void saveAppointmentSchedule(List<SpAppointmentSchedule> list)throws Exception;
	public List<SpDoctorInfo> getDoctorInfoofSpappointmentschedule()throws Exception;
	public void saveOrUpdateSpDoctorInfo(SpDoctorInfo spDoctorInfo)throws Exception;
}
