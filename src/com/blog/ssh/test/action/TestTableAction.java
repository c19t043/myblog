package com.blog.ssh.test.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.blog.ssh.base.action.BaseAction;
import com.blog.ssh.test.domain.TestTable;
import com.blog.ssh.test.service.ITestTableService;

@Controller("testTableAction")
@Scope("prototype")
public class TestTableAction extends BaseAction<TestTable>{

	private static final long serialVersionUID = 116488015489842552L;

	private TestTable testTable = this.getModel();
	
	@Resource(name=ITestTableService.SERVICE_NAME)
	private ITestTableService iTestTableService;
	
	public String add(){
		iTestTableService.save(testTable);
		return SUCCESS;
	}

}
