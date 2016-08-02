package com.blog.ssh.test.dao;

import com.blog.ssh.base.dao.ICommonDao;
import com.blog.ssh.test.domain.TestTable;

public interface ITestTableDao extends ICommonDao<TestTable>{
	public static final String SERVICE_NAME = "com.blog.ssh.test.dao.impl.TestTableDaoImpl";  
}
