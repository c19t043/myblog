package com.blog.ssh.dao;

import java.util.List;

import com.blog.ssh.base.dao.CommonDao;
import com.blog.ssh.domain.User;

public interface UserDao extends CommonDao<User>{

	public List<User> getObjectCollectionOfUser()throws Exception;
	
}
