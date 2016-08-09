package com.blog.ssh.test.dao;

import com.blog.ssh.base.dao.CommonDao;
import com.blog.ssh.test.domain.UserInoculationAppointmentInfo;

public interface InnoculationOrderDao extends CommonDao<UserInoculationAppointmentInfo>{
	public static final String SERVER_NAME ="com.blog.ssh.test.dao.impl.InnoculationOrderDaoImpl";
}
