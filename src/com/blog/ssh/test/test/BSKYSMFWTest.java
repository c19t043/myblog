package com.blog.ssh.test.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.ssh.test.domain.OrderInfo;
import com.blog.ssh.test.service.SMFWOrderService;

public class BSKYSMFWTest {
	
	@Test
	public void testUpdateOrderStatus(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SMFWOrderService SMFWOrderServiceImpl = 
				(SMFWOrderService) context.getBean(SMFWOrderService.SERVER_NAME);
		
//		int ids [] = new int[]{};
//		for(int i=0,len=ids.length;i<len;i++){
			OrderInfo orderInfo = SMFWOrderServiceImpl.getOrderInfo(332L);
			orderInfo.setOrderStatus("已接单");
			orderInfo.setDoctorId(167L);//3151647,,167
			SMFWOrderServiceImpl.updateOrderInfo(orderInfo);
		/*}*/
		System.out.println("操作完成");
	}
}
