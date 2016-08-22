package com.blog.ssh.test.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.ssh.test.domain.TestTable;
import com.blog.ssh.test.service.ITestTableService;

public class ServiceTest {
	ApplicationContext context;
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testAdd(){
		ITestTableService iTestTableService = (ITestTableService) context.getBean(ITestTableService.SERVICE_NAME);
		
		TestTable testTable = new TestTable();
		testTable.setName("测试Service");
		try {
			iTestTableService.save(testTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
