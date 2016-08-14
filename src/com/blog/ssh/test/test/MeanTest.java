package com.blog.ssh.test.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.blog.ssh.utils.HttpClientUtils;

public class MeanTest {

	@Test
	public void addUser() {

		String url = "http://localhost:3000/users";
		String result = "";
		Map<String, String> params = new HashMap<String, String>();

		params.put("firstName", "First");
		params.put("lastName", "Last");
		params.put("email", "user@example.com");
		params.put("username", "username");
		params.put("password", "password");

		result = HttpClientUtils.doPost(url, params);

		System.out.println(result);
		/*
		 * {"__v":0,"lastName":"Last","username":"username","email":
		 * "user@example.com","firstName":"First","password":"password","_id":
		 * "57b02fdc6eb8ef183369eee0"}
		 */
	}
	
	@Test
	public void updateUser() {
		/*
		 *	执行过后，数据库中users集合多了条文档 
		 */
		String url = "http://localhost:3000/users/57b02fdc6eb8ef183369eee0";
		String result = "";
		Map<String, String> params = new HashMap<String, String>();

		params.put("firstName", "First");
		params.put("lastName", "Update");

		result = HttpClientUtils.doPut(url, params);
		System.out.println(result);
	}
	
	@Test
	public void deleteUser() {
		String url = "http://localhost:3000/users/57b02fdc6eb8ef183369eee0";

		String result = HttpClientUtils.doDelete(url);
		
		System.out.println(result);
	}
}
