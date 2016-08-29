package com.blog.ssh.test.cxf.client.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.ssh.test.domain.TestTable;
import com.blog.ssh.test.service.ITestTableService;


public class ClientTest {
	ApplicationContext context;
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testAdd(){
		ITestTableService iTestTableService = (ITestTableService) context.getBean("");
		
		TestTable testTable = new TestTable();
		testTable.setName("测试Service");
		try {
			iTestTableService.save(testTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
