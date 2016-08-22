package com.blog.ssh.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.blog.ssh.test.dao.SMFWOrderDao;
import com.blog.ssh.test.domain.OrderInfo;
import com.blog.ssh.test.service.SMFWOrderService;

@Service(SMFWOrderService.SERVER_NAME)
public class SMFWOrderServiceImpl implements SMFWOrderService{

	@Resource
	private SMFWOrderDao sMFWOrderDao;
	
	@Override
	public void updateOrderInfo(OrderInfo orderInfo) throws Exception {
		sMFWOrderDao.update(orderInfo);
	}

	@Override
	public OrderInfo getOrderInfo(Long id) throws Exception {
		return sMFWOrderDao.findObjectById(id);
	}
}
