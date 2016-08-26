package com.blog.ssh.test.cxf.client.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import com.blog.ssh.test.cxf.client.domain.org.tempuri.ToolInterface;
import com.blog.ssh.test.cxf.client.domain.org.tempuri.ToolInterfaceSoap;
import com.opensymphony.xwork2.interceptor.annotations.Before;


public class ClientTest {
	
	public void test1(){
		ToolInterface tf = new ToolInterface();
		ToolInterfaceSoap toolInterfaceSoap = tf.getToolInterfaceSoap();
		String request = "";
		String login = toolInterfaceSoap.login(request);
		String interactionOperating = toolInterfaceSoap.interactionOperating(request);
	}
	
	public void test2() throws MalformedURLException {
			//创建服务视图
		//URL wsdlDocumentLocation = new URL("file:///d:/WeatherWebService.wsdl");
		//也可以指定公网地址
		String request = "";
		port.login(request);
		port.interactionOperating(request);
	}
	
	
    private ToolInterfaceSoap port;
	
	@Before
	public void init() throws MalformedURLException{
		URL wsdlDocumentLocation = new URL("http://171.221.218.21:5418/ToolInterface.asmx?wsdl");

		QName serviceName = new QName("http://tempuri.org/", "ToolInterface");
		
		Service service = Service.create(wsdlDocumentLocation, serviceName);
		//得到portType
		ToolInterfaceSoap port = service.getPort(ToolInterfaceSoap.class);
	}
	
	@Test
	public void getOrg_guahao_scheduling() throws Exception{
		ToolInterface tf = new ToolInterface();
		ToolInterfaceSoap toolInterfaceSoap = tf.getToolInterfaceSoap();
		String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
						+"<Body>"
						  +"<Request>"
						   +"<OperType>101</OperType>"
						   +"<OrgCode>3d715fb3-5fd4-4f36-9be9-7cca29de01ca</OrgCode>"
						   +"<QueryString></QueryString>"
						  +"</Request>"
						+"</Body>";
		String res = toolInterfaceSoap.interactionOperating(request);
		System.out.println(res);
       /* Document doc = DocumentHelper.parseText(res);  
        //获取根节点  
        Element root = doc.getRootElement();  
        List<Element> elements = root.elements();
        for (Element element : elements) {
        	System.out.println(element.getText());
		}*/
	}
}
