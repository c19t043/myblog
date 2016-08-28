package com.blog.ssh.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class DomUtil {
	
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
	
	private static void Element2Bean(Element element,Object obj){
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
