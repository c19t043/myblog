package com.blog.ssh.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.blog.ssh.base.dao.impl.CommonDaoImpl;
import com.blog.ssh.test.dao.ITestTableDao;
import com.blog.ssh.test.domain.TestTable;

@Repository(ITestTableDao.SERVICE_NAME)
public class TestTableDaoImpl extends CommonDaoImpl<TestTable> implements ITestTableDao {

}
