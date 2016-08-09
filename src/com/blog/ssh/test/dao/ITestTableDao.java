package com.blog.ssh.test.dao;

import com.blog.ssh.base.dao.CommonDao;
import com.blog.ssh.test.domain.TestTable;

public interface ITestTableDao extends CommonDao<TestTable>{
	public static final String SERVICE_NAME = "com.blog.ssh.test.dao.impl.TestTableDaoImpl";  
}
