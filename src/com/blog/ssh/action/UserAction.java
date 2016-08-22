package com.blog.ssh.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.blog.ssh.base.action.BaseAction;
import com.blog.ssh.domain.User;
import com.blog.ssh.service.UserService;
import com.blog.ssh.utils.ResponseUtils;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	private static final long serialVersionUID = -3681278803493798332L;

	private User user = this.getModel();
	
	@Autowired
	private UserService userService;
	
	public String userRegist(){
		try {
			List<User> registUser = userService.registUser(user);
			//JSONArray ja = JSONArray.fromObject(registUser);
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			for(User user : registUser){
				jo = JSONObject.fromObject(user);
				ja.add(jo);
			}
			ResponseUtils.writeJson(response, ja.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
		return NONE;
	}
}
