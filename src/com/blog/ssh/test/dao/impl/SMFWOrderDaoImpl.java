package com.blog.ssh.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.blog.ssh.base.dao.impl.CommonDaoImpl;
import com.blog.ssh.test.dao.SMFWOrderDao;
import com.blog.ssh.test.domain.OrderInfo;

@Repository
public class SMFWOrderDaoImpl extends CommonDaoImpl<OrderInfo> implements SMFWOrderDao{

}
