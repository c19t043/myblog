package com.blog.ssh.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.blog.ssh.base.dao.impl.CommonDaoImpl;
import com.blog.ssh.test.dao.InnoculationOrderDao;
import com.blog.ssh.test.domain.UserInoculationAppointmentInfo;

@Repository(InnoculationOrderDao.SERVER_NAME)
public class InnoculationOrderDaoImpl extends CommonDaoImpl<UserInoculationAppointmentInfo> implements InnoculationOrderDao{

}
