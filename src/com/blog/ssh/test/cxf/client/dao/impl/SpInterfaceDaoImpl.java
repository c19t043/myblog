package com.blog.ssh.test.cxf.client.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.ssh.base.dao.impl.CommonDaoImpl;
import com.blog.ssh.test.cxf.client.dao.SpInterfaceDao;
import com.blog.ssh.test.cxf.client.domain.SpAppointmentSchedule;

@Repository
public class SpInterfaceDaoImpl extends CommonDaoImpl<SpAppointmentSchedule> implements SpInterfaceDao{

	@Override
	public void saveAppointmentSchedule(List<SpAppointmentSchedule> list) throws Exception {
		for (SpAppointmentSchedule spAppointmentSchedule : list) {
			this.save(spAppointmentSchedule);
		}
	}
	
}
