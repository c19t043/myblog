package com.blog.ssh.test.domain;

import java.io.Serializable;

public class TestTable implements Serializable{
	private static final long serialVersionUID = -4206930014237206124L;

	private Long id;//主键
	private String name;//测试名称
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
