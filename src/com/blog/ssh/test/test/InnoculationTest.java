package com.blog.ssh.test.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.ssh.test.domain.UserInoculationAppointmentInfo;
import com.blog.ssh.test.service.InnoculationOrderService;

public class InnoculationTest {
	
	@Test
	public void testUpdateOrderStatus(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		InnoculationOrderService innoculationOrderService = 
				(InnoculationOrderService) context.getBean(InnoculationOrderService.SERVER_NAME);
		
		
		UserInoculationAppointmentInfo  userInoculationAppointmentInfo = 
				innoculationOrderService.getUserInoculationAppointmentInfo(57L);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		userInoculationAppointmentInfo.setOptTime(df.format(new Date()));
		userInoculationAppointmentInfo.setStatus("已登记");
		
		innoculationOrderService.updateOrderStatus(userInoculationAppointmentInfo);
		System.out.println("操作完成");
	}
}
