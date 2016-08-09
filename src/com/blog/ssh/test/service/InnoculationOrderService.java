package com.blog.ssh.test.service;

import com.blog.ssh.test.domain.UserInoculationAppointmentInfo;

public interface InnoculationOrderService {
	
	public static final String SERVER_NAME = "com.blog.ssh.test.service.impl.InnoculationOrderServiceImpl";
	
	public void updateOrderStatus(UserInoculationAppointmentInfo userInoculationAppointmentInfo);
	public UserInoculationAppointmentInfo getUserInoculationAppointmentInfo(Long id);
}
