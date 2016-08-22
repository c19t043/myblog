package com.blog.ssh.service;

import java.util.List;

import com.blog.ssh.domain.User;

public interface UserService {
	public List<User> registUser(User user)throws Exception;
}
