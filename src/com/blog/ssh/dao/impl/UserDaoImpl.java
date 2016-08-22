package com.blog.ssh.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.ssh.base.dao.impl.CommonDaoImpl;
import com.blog.ssh.dao.UserDao;
import com.blog.ssh.domain.User;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User> implements UserDao{

	@Override
	public List<User> getObjectCollectionOfUser()throws Exception {
		String condition = "from User";
		List<User> user_List = 
				this.findObjectCollectionByConditionWithNoPage(condition, null, null);
		return user_List;
	}


}
