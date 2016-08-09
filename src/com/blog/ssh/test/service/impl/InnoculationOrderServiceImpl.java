package com.blog.ssh.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.blog.ssh.test.dao.InnoculationOrderDao;
import com.blog.ssh.test.domain.UserInoculationAppointmentInfo;
import com.blog.ssh.test.service.InnoculationOrderService;

@Service(InnoculationOrderService.SERVER_NAME)
public class InnoculationOrderServiceImpl implements InnoculationOrderService{

	@Resource
	private InnoculationOrderDao innoculationOrderDao;
	
	@Override
	public void updateOrderStatus(
			UserInoculationAppointmentInfo userInoculationAppointmentInfo) {
		if(userInoculationAppointmentInfo.getId()!=null){
			innoculationOrderDao.update(userInoculationAppointmentInfo);
		}
	}

	@Override
	public UserInoculationAppointmentInfo getUserInoculationAppointmentInfo(
			Long id) {
		return innoculationOrderDao.findObjectById(id);
	}
}
