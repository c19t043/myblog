package com.blog.ssh.test.cxf.client.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.blog.ssh.test.cxf.client.dao.SpInterfaceDao;
import com.blog.ssh.test.cxf.client.domain.SpAppointmentSchedule;
import com.blog.ssh.test.cxf.client.domain.SpDoctorInfo;
import com.blog.ssh.test.cxf.client.domain.SpVisitRecord;
import com.blog.ssh.test.cxf.client.generateFile.ToolInterface;
import com.blog.ssh.test.cxf.client.generateFile.ToolInterfaceSoap;
import com.blog.ssh.test.cxf.client.service.SPInterfaceService;
import com.blog.ssh.utils.DomUtil;

@Service("spInterfaceService")
public class SPInterfaceServiceImpl implements SPInterfaceService{

	@Autowired
	private SpInterfaceDao spInterfaceDao;
	
	private final String ERROR = "0";
	
	public static void main(String[] args) {
		SPInterfaceServiceImpl p = new SPInterfaceServiceImpl();
		try {
			p.getSPAppointmentSchedule();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		String request = DomUtil.createXml(visitRecord);
		String res = invokeInterfaceMethod(request);
		List<SpVisitRecord> list = xml2Bean(res,SpVisitRecord.class);
		return list;
	}
	/**  获取挂号安排  
	 * @throws Exception */
	@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public List<SpAppointmentSchedule> getSPAppointmentSchedule() throws Exception {
		SpAppointmentSchedule appointment = new SpAppointmentSchedule();
		appointment.setSp_OperType("101");
		appointment.setSp_OrgCode("3d715fb3-5fd4-4f36-9be9-7cca29de01ca");
		String request = DomUtil.createXml(appointment);
		String res = invokeInterfaceMethod(request);
		List<SpAppointmentSchedule> list = xml2Bean(res,SpAppointmentSchedule.class);
		spInterfaceDao.saveAppointmentSchedule(list);
		return list;
	}
	
	/**  获取医生列表  */
	public List<SpDoctorInfo> getSPDoctorInfos(){
		String request = null;//createDocumentAsXML("004", "3d715fb3-5fd4-4f36-9be9-7cca29de01ca", null,"2");
		String res = invokeInterfaceMethod(request);
		return xml2Bean(res,SpDoctorInfo.class);
	}
	
	/*private String createDocumentAsXML(String OperType,String OrgCode,String QueryString,String type){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element rootElement = doc.addElement("Body");
		Element reqElement = rootElement.addElement("Request");
		reqElement.addElement("OperType").addText(StringUtils.isNotBlank(OperType)?OperType:""); 
		reqElement.addElement("OrgCode").addText(StringUtils.isNotBlank(OrgCode)?OrgCode:"");
		reqElement.addElement("QueryString").addText(StringUtils.isNotBlank(QueryString)?QueryString:"");
		reqElement.addElement("Type").addText(StringUtils.isNotBlank(type)?type:"");
		return doc.asXML();
	
	}*/
	
	private String invokeInterfaceMethod(String request){
		ToolInterface tf = new ToolInterface();
		ToolInterfaceSoap toolInterfaceSoap = tf.getToolInterfaceSoap();
		return toolInterfaceSoap.interactionOperating(request);
	}
	
	@SuppressWarnings("unchecked")
	private <T>  List<T> xml2Bean(String xmlStr,Class<?> clazz){
		Document doc = DomUtil.parseText(xmlStr);
		
		String retCode = getRetCode(doc);
		if(ERROR.equals(retCode)){
			System.out.println(getErrMsg(doc));
			return null;
		}
		
		List<Element> retBeanDatas = getReqBeanDatas(doc);
		if(retBeanDatas==null) return null;
		
		List<Object> retListObject = new ArrayList<Object>();
		for(Element element : retBeanDatas){
			if(element==null) continue;
			List<Element> childEle = element.elements();
			Object obj = DomUtil.convert2JavaBean(childEle,clazz);
			retListObject.add(obj);
		}
		return (List<T>) retListObject;
	}
	
	private String getRetCode(Document doc){
		return doc.selectSingleNode("//Response/ReCode").getText();
	}
	
	private String getErrMsg(Document doc){
		return doc.selectSingleNode("//Response/ErrMsg").getText();
	}
	
	@SuppressWarnings("unchecked")
	private List<Element> getReqBeanDatas(Document doc){
		return doc.selectNodes("//Response/ResulList");
	}
}
