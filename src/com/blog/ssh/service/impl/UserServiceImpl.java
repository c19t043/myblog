package com.blog.ssh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.blog.ssh.dao.UserDao;
import com.blog.ssh.domain.User;
import com.blog.ssh.service.UserService;

@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public List<User> registUser(User user) throws Exception{
		userDao.save(user);
		List<User> user_list = userDao.getObjectCollectionOfUser();
		return user_list;
	}
}
