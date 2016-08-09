package com.blog.ssh.test.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.blog.ssh.test.domain.OrderInfo;

public class SMFWOrderInterceptor extends HibernateDaoSupport{
	
	private Log log = LogFactory.getLog(SMFWOrderInterceptor.class);
	
	public void setSessionFactoryDI(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	public void afterReturning(JoinPoint joinPoint,Object val) throws Throwable{
		Object[] args = joinPoint.getArgs();
		OrderInfo orderInfo = null;
		if(args[0] instanceof OrderInfo){
			orderInfo = (OrderInfo) args[0];
		}
		
		/*
		 *1.当计免对象的id不为null,订单状态为‘已接单’，医生Id不能为空
		 *2.根据订单id判断该订单是否为快医公司上门服务订单
		 *3.如果是快医公司上门服务订单，修改快医公司上门服务订单医生数据
		 */
		if(orderInfo!=null
				&&orderInfo.getId()!=null
				&&orderInfo.getOrderStatus().equals("已接单")
				&&orderInfo.getDoctorId()!=null){
				
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			String sql = "SELECT kyid FROM kyorder2myorder WHERE id = :id";
			SQLQuery createSQLQuery = session.createSQLQuery(sql);
			createSQLQuery.setLong("id", orderInfo.getId());
			Object uniqueResult = createSQLQuery.uniqueResult();
			
			if(uniqueResult==null) return;
			
			String kyId = uniqueResult.toString();
			String info = "";
			try {
				info = HttpClientUtil.SMFWEdit(kyId, orderInfo.getDoctorId()+"");
			} catch (Exception e) {
				printErrorInfo(orderInfo.getId(),info,e);
			}
			if(!info.equals("true")){
				printErrorInfo(orderInfo.getId(),info,null);
			}else{
				System.out.println(info);
			}
		}
	}
	private void printErrorInfo(Long id,String info,Exception ex){
		StringBuilder sb = new StringBuilder();
		sb.append("修改快医上门服务订单医生数据失败,订单id为"+id);
		if(StringUtils.isNotBlank(info))
			sb.append(System.lineSeparator());
			sb.append("错误信息："+info);
		if(ex!=null)
			sb.append(System.lineSeparator());
			sb.append("异常信息："+ex.toString());
		//System.out.println(sb.toString());
		log.error(sb.toString());
	}
}
