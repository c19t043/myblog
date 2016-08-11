package com.blog.ssh.test.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.blog.ssh.test.domain.UserInoculationAppointmentInfo;

public class InnoculationOrderInterceptor extends HibernateDaoSupport{
	
	private Log log = LogFactory.getLog(InnoculationOrderInterceptor.class);
	
	public void setSessionFactoryDI(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	public void afterReturning(JoinPoint joinPoint,Object val) throws Throwable{
		Object[] args = joinPoint.getArgs();
		
		UserInoculationAppointmentInfo userInoculationAppointmentInfo = null;
		if(args[0] instanceof UserInoculationAppointmentInfo)
			userInoculationAppointmentInfo = (UserInoculationAppointmentInfo) args[0];
		
		/*
		 *1.当计免对象的id不为null是为修改订单状态 
		 *2.根据订单id判断该订单是否为快医公司计免订单
		 *3.如果是快医公司订单，获取计免对象的订单状态，和快医计免的订单id，
		 *4.修改快医计免订单状态	
		 */
		if(userInoculationAppointmentInfo!=null&&userInoculationAppointmentInfo.getId()!=null){
			
			if(!userInoculationAppointmentInfo.getStatus().equals("已登记")) return ;
				
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			String sql = "SELECT kyid FROM kyinoculationorder2myorder WHERE id = :id";
			SQLQuery createSQLQuery = session.createSQLQuery(sql);
			createSQLQuery.setLong("id", userInoculationAppointmentInfo.getId());
			Object uniqueResult = createSQLQuery.uniqueResult();
			
			if(uniqueResult==null) return;
			
			String kyId = uniqueResult.toString();
			String info = "";
			try {
				info = HttpClientUtil.inocalutionOrderEdit(kyId, 3+"");
			} catch (Exception e) {
				printErrorInfo(userInoculationAppointmentInfo.getId(),info,e);
			}
			if(!info.equals("true")){
				printErrorInfo(userInoculationAppointmentInfo.getId(),info,null);
			}else{
				System.out.println("修改计免状态-操作："+info);
			}
		}
	}
	private void printErrorInfo(Long id,String info,Exception ex){
		StringBuilder sb = new StringBuilder();
		sb.append("修改快医计免订单状态失败,订单id为"+id);
		if(StringUtils.isNotBlank(info))
			sb.append(System.lineSeparator());
			sb.append("错误信息："+info);
		if(ex!=null)
			sb.append(System.lineSeparator());
			sb.append("异常信息："+ex.toString());
		System.out.println(sb.toString());
		log.error(sb.toString());
	}
}
