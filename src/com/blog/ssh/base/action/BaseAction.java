package com.blog.ssh.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.blog.ssh.utils.TUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("all")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,ServletRequestAware,ServletResponseAware{

	T t;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	
	public BaseAction(){
		try {
			t = (T) TUtil.getActualType(this.getClass()).newInstance();
		} catch (Exception e) {
			System.out.println(this.getClass()+"泛型转换异常");
		}
	}
	
	
	@Override
	public T getModel() {
		return t;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	@Override
	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
	}

}
