package com.blog.ssh.test.cxf.client.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.blog.ssh.base.dao.impl.CommonDaoImpl;
import com.blog.ssh.test.cxf.client.dao.SpInterfaceDao;
import com.blog.ssh.test.cxf.client.domain.SpAppointmentSchedule;
import com.blog.ssh.test.cxf.client.domain.SpDoctorInfo;

@Repository
public class SpInterfaceDaoImpl extends CommonDaoImpl<SpAppointmentSchedule> implements SpInterfaceDao{

	@Override
	public void saveAppointmentSchedule(List<SpAppointmentSchedule> list) throws Exception {
		/*Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		SQLQuery createSQLQuery = session.createSQLQuery("delete from sp_appointment_schedule");
		int executeUpdate = createSQLQuery.executeUpdate();*/
		for (SpAppointmentSchedule spAppointmentSchedule : list) {
			this.save(spAppointmentSchedule);
		}
	}
	public List<SpDoctorInfo> getDoctorInfoofSpappointmentschedule()throws Exception{
		return this.getHibernateTemplate().execute(new HibernateCallback<List<SpDoctorInfo>>() {

			@Override
			public List<SpDoctorInfo> doInHibernate(Session session) throws HibernateException,
					SQLException {
				StringBuilder sb  = new StringBuilder();
				sb.append("SELECT DISTINCT a.depID,a.depName,a.doctorID,a.doctor,b.id local_doctorid,c.id local_orgid ");
				//sb.append("SELECT DISTINCT a.depID,a.depName,a.doctorID,a.doctor,b.id ,c.id local_doctorid");
				sb.append("FROM sp_appointment_schedule a ");
				sb.append("LEFT JOIN doctor_info b ON a.doctor = b.doctorName ");
				sb.append("LEFT JOIN hospital_basic_info c ON c.else_orgID = a.orgID ");
				sb.append("WHERE  1=1 ");
				sb.append("AND a.depName IN ('儿科','儿保室') ");
				sb.append("AND c.id = 38");
				SQLQuery createSQLQuery = session.createSQLQuery(sb.toString());
				List<?> list = createSQLQuery.list();
				List<SpDoctorInfo> doctors = new ArrayList<SpDoctorInfo>();
				for (Object object : list) {
					Object[] obj = (Object[]) object;
					doctors.add(new SpDoctorInfo(obj[2].toString(), obj[3].toString(), 
							obj[0].toString(), obj[1].toString(),
							obj[4]==null?null:Long.parseLong(obj[4].toString()), 
							obj[5]==null?null:Long.parseLong(obj[5].toString())));
				}
				return doctors;
			}
		});
	}
	public void saveOrUpdateSpDoctorInfo(SpDoctorInfo spDoctorInfo)throws Exception{
		List<?> retDoctorInfo = this.getHibernateTemplate().
				find("from SpDoctorInfo c where c.doctorName = '" + spDoctorInfo.getDoctorName() +"'");
		if(retDoctorInfo.isEmpty()){
			this.getHibernateTemplate().save(spDoctorInfo);
		}else{
			BeanUtils.copyProperties(spDoctorInfo, retDoctorInfo.get(0), new String[]{"id"});
		}
	}
}
