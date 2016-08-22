package com.blog.ssh.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.blog.ssh.test.dao.ITestTableDao;
import com.blog.ssh.test.domain.TestTable;
import com.blog.ssh.test.service.ITestTableService;

@Service(ITestTableService.SERVICE_NAME)
public class TestTableServiceImpl implements ITestTableService {

	@Resource(name=ITestTableDao.SERVICE_NAME)
	private ITestTableDao iTestTableDao;
	
	@Override
	public void save(TestTable testTable) throws Exception {
		iTestTableDao.save(testTable);
	}
	
}
