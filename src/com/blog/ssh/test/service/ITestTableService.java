package com.blog.ssh.test.service;

import com.blog.ssh.test.domain.TestTable;

public interface ITestTableService {
	public static final String SERVICE_NAME = "com.blog.ssh.test.service.impl.TestTableServiceImpl";
	
	public void save(TestTable testTable);
}
