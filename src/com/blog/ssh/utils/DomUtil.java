package com.blog.ssh.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class DomUtil {
	
	private final static String ERROR = "0";
	
	@SuppressWarnings("unchecked")
	public static <T>  List<T> xml2Bean(String xmlStr,Class<?> clazz){
		Document doc = DomUtil.parseText(xmlStr);
		
		String retCode = getRetCode(doc);
		if(ERROR.equals(retCode)){
			System.out.println(getErrMsg(doc));
			return null;
		}
		
		List<Object> retListObject = new ArrayList<Object>();
		Object obj = null;
		List<Element> retBeanDatas = getReqBeanDatas(doc);
		//ResultList元素下没有数据
		if(retBeanDatas.isEmpty()) {
			Element retElement = getReqBeanDatas_Res(doc);
			List<Element> elements = retElement.elements();
			obj= convert2JavaBean(elements, clazz);
			retListObject.add(obj);
		}else{
			//ResultList元素下有数据
			for(Element element : retBeanDatas){
				if(element==null) continue;
				List<Element> childEle = element.elements();
				obj = DomUtil.convert2JavaBean(childEle,clazz);
				retListObject.add(obj);
			}
		}
		return (List<T>) retListObject;
	}
	
	public static String getRetCode(Document doc){
		return doc.selectSingleNode("//Response/ReCode").getText();
	}
	
	public static String getErrMsg(Document doc){
		return doc.selectSingleNode("//Response/ErrMsg").getText();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Element> getReqBeanDatas(Document doc){
		return doc.selectNodes("//Response/ResulList");
	}
	public static Element getReqBeanDatas_Res(Document doc){
		return (Element) doc.selectSingleNode("//Response");
	}
	@SuppressWarnings("all")
	public static String createXml(Object obj){
		Method[] methods = obj.getClass().getMethods();
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element rootElement = doc.addElement("Body");
		Element reqElement = rootElement.addElement("Request");
		for(Method method : methods){
			String methodName = method.getName();
			if(methodName.startsWith("getSp_")){
				try {
					String propertyName = methodName.substring("getSp_".length());
					Object retVal = method.invoke(obj, null);
					reqElement.addElement(propertyName).addText(retVal==null?"":retVal.toString()); 
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		String xmlStr = doc.asXML();
		//System.out.println(xmlStr);
		return xmlStr;
	}
	
	public static Document parseText(String xmlStr){
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			System.out.println(e);
		}
		return doc;
	}
	
	public static Object convert2JavaBean(List<Element> elements,Class<?> clazz){
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			System.out.println(clazz.getName()+"实例化失败");
		}
		for (Element element : elements) {
			Element2Bean(element, obj);
		}
		return obj;
	}
	public static Object convert2JavaBean_Res(Element element,Class<?> clazz){
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			System.out.println(clazz.getName()+"实例化失败");
		}
		setPropertyByBeanUtil(element,obj);
		return obj;
	}
	
	private static void Element2Bean(Element element,Object obj){
		setPropertyByBeanUtil(element,obj);
		//setProperty(obj, element.getName(), element.getTextTrim());
	}
	private static void setPropertyByBeanUtil(Element element,Object obj){
		String elementName = element.getName();
		String firstChar = elementName.substring(0, 1);
		elementName=elementName.replaceFirst(firstChar, firstChar.toLowerCase());
		try {
			BeanUtils.setProperty(obj, elementName, element.getTextTrim());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("unused")
	private static void setProperty(Object obj,String propertyName,Object value){
		PropertyDescriptor pd = null;
		try {
			pd = new PropertyDescriptor(propertyName, obj.getClass());
			Method writeMethod = pd.getWriteMethod();
			writeMethod.invoke(obj, value);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
