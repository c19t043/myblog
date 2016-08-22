package com.blog.ssh.test.service;

import com.blog.ssh.test.domain.OrderInfo;

public interface SMFWOrderService {
	
	public static final String SERVER_NAME = "com.blog.ssh.test.service.impl.SMFWOrderServiceImpl";
	
	public void updateOrderInfo(OrderInfo orderInfo)throws Exception;
	public OrderInfo getOrderInfo(Long id)throws Exception;
}
