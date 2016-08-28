package com.blog.ssh.test.cxf.client.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.blog.ssh.test.cxf.client.domain.SPAppointmentSchedule;
import com.blog.ssh.test.cxf.client.generateFile.ToolInterface;
import com.blog.ssh.test.cxf.client.generateFile.ToolInterfaceSoap;
import com.blog.ssh.test.cxf.client.service.SPInterfaceService;
import com.blog.ssh.utils.DomUtil;

public class SPInterfaceServiceImpl implements SPInterfaceService{

	private final String ERROR = "0";
	
	@Override
	public List<SPAppointmentSchedule> getSPAppointmentSchedule() {
		String request = createDocumentAsXML("101", "3d715fb3-5fd4-4f36-9be9-7cca29de01ca", "");
		String res = invokeInterfaceMethod(request);
		return xml2Bean(res,SPAppointmentSchedule.class);
	}
	
	private String createDocumentAsXML(String OperType,String OrgCode,String QueryString){
		Document doc = DocumentHelper.createDocument();
		Element rootElement = doc.addElement("Body");
		Element reqElement = rootElement.addElement("Request");
		reqElement.addElement("OperType").addText(StringUtils.isNotBlank(OperType)?OperType:""); 
		reqElement.addElement("OrgCode").addText(StringUtils.isNotBlank(OrgCode)?OrgCode:"");
		reqElement.addElement("QueryString").addText(StringUtils.isNotBlank(QueryString)?QueryString:"");
		return doc.asXML();
	
	}
	
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
	
	public static void main(String[] args) {
		SPInterfaceServiceImpl p = new SPInterfaceServiceImpl();
		p.getSPAppointmentSchedule();
	}
}
