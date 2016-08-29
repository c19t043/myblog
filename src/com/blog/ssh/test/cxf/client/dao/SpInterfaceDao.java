package com.blog.ssh.test.cxf.client.dao;

import java.util.List;

import com.blog.ssh.base.dao.CommonDao;
import com.blog.ssh.test.cxf.client.domain.SpAppointmentSchedule;


public interface SpInterfaceDao extends CommonDao<SpAppointmentSchedule>{
	
	public void saveAppointmentSchedule(List<SpAppointmentSchedule> list)throws Exception;
}
