package com.blog.ssh.test.cxf.client.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.blog.ssh.test.cxf.client.dao.SpInterfaceDao;
import com.blog.ssh.test.cxf.client.domain.SpAppointmentSchedule;
import com.blog.ssh.test.cxf.client.domain.SpDoctorInfo;
import com.blog.ssh.test.cxf.client.domain.SpUserInfo;
import com.blog.ssh.test.cxf.client.domain.SpVisitRecord;
import com.blog.ssh.test.cxf.client.generateFile.ToolInterface;
import com.blog.ssh.test.cxf.client.generateFile.ToolInterfaceSoap;
import com.blog.ssh.test.cxf.client.service.SPInterfaceService;
import com.blog.ssh.utils.DomUtil;

@Service("spInterfaceService")
public class SPInterfaceServiceImpl implements SPInterfaceService{

	@Autowired
	private SpInterfaceDao spInterfaceDao;
	
	public static void main(String[] args) {
		SPInterfaceServiceImpl p = new SPInterfaceServiceImpl();
		try {
			p.getSPAppointmentSchedule();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**  会员信息同步  */
	public void saveUserInfo()throws Exception{
		SpUserInfo spUserInfo = new SpUserInfo();
		spUserInfo.setSp_OperType("003");
		spUserInfo.setSp_OrgCode("3d715fb3-5fd4-4f36-9be9-7cca29de01ca");
		
		List<Object> reqData = getReqData(spUserInfo, SpUserInfo.class);
		System.out.println(reqData.size());
	}
	private <T> List<T> getReqData(Object obj,Class<?> clazz){
		String request = DomUtil.createXml(obj);
		String res = invokeInterfaceMethod(request);
		List<T> list = DomUtil.xml2Bean(res,clazz);
		return list;
	}
	/**  获取就诊记录  */
	public List<SpVisitRecord> getSpVisitRecord(){
		SpVisitRecord visitRecord = new SpVisitRecord();
		visitRecord.setSp_OperType("103");
		visitRecord.setSp_OrgCode("3d715fb3-5fd4-4f36-9be9-7cca29de01ca");
		//visitRecord.setSp_ResidentID(sp_ResidentID);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		visitRecord.setSp_BeginDT("2016-08-29");
		visitRecord.setSp_EndDT("2016-08-29");
		List<SpVisitRecord> reqData = getReqData(visitRecord,SpVisitRecord.class);
		return reqData;
	}
	/**  获取挂号安排  */
	@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public List<SpAppointmentSchedule> getSPAppointmentSchedule() throws Exception {
		SpAppointmentSchedule appointment = new SpAppointmentSchedule();
		appointment.setSp_OperType("101");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String save_Regist_time = sdf.format(new Date());
		//苏坡社区
		List<SpAppointmentSchedule> list = new ArrayList<SpAppointmentSchedule>();
		appointment.setSp_OrgCode("3d715fb3-5fd4-4f36-9be9-7cca29de01ca");
		List<SpAppointmentSchedule> reqData = getReqData(appointment,SpAppointmentSchedule.class);
		for (SpAppointmentSchedule spAppointmentSchedule : reqData) {
			spAppointmentSchedule.setOrgId(appointment.getSp_OrgCode());
			spAppointmentSchedule.setOptime(save_Regist_time);
		}
		list.addAll(reqData);
		
		//第九人民医院
		/*appointment.setSp_OrgCode("1cc69208-1d1d-43f8-925b-39fc437e320d");
		reqData = getReqData(appointment,SpAppointmentSchedule.class);
		for (SpAppointmentSchedule spAppointmentSchedule : reqData) {
			spAppointmentSchedule.setOrgId(appointment.getSp_OrgCode());
			spAppointmentSchedule.setOptime(save_Regist_time);
		}
		list.addAll(reqData);*/
		spInterfaceDao.saveAppointmentSchedule(list);
		return list;
	}
	
	/**  获取医生列表  
	 * @throws Exception */
	@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public List<SpDoctorInfo> getSPDoctorInfos() throws Exception{
		/*
		 * 获取挂号排版医生数据
		 */
		List<SpDoctorInfo> doctorInfos = spInterfaceDao.getDoctorInfoofSpappointmentschedule();
		for (SpDoctorInfo spDoctorInfo : doctorInfos) {
			spInterfaceDao.saveOrUpdateSpDoctorInfo(spDoctorInfo);
		}
		return doctorInfos;
	}
	
	private String invokeInterfaceMethod(String request){
		ToolInterface tf = new ToolInterface();
		ToolInterfaceSoap toolInterfaceSoap = tf.getToolInterfaceSoap();
		return toolInterfaceSoap.interactionOperating(request);
	}
}
