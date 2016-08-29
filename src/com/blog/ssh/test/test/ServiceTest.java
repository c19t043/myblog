package com.blog.ssh.test.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.ssh.test.cxf.client.service.SPInterfaceService;

public class ServiceTest {
	ApplicationContext context;
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testAdd(){
		SPInterfaceService spInterfaceService = (SPInterfaceService) context.getBean("spInterfaceService");
		try {
			spInterfaceService.getSPAppointmentSchedule();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
