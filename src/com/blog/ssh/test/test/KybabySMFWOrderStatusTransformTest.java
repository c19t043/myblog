package com.blog.ssh.test.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class KybabySMFWOrderStatusTransformTest {

	private static Log log = LogFactory.getLog(KybabySMFWOrderTest.class);
	
	@Test
	public void testAdd(){
		//String url = "http://dev.qiyico.com/kybabyBG/kyinterface/kyInterfaceAction.action";
		String url = "http://localhost:8082/kybabyBG/kyinterface/kyInterfaceAction.action";
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		
		list.add(new BasicNameValuePair("action", "OrderStatusTransform"));
		list.add(new BasicNameValuePair("kyOrderId", "123123123"));
		list.add(new BasicNameValuePair("orderStatus", "12"));
		
		String mes = doPost(url,list);
		System.out.println(mes);
	}
	
	public String doPost(String Url,List<NameValuePair> list){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		
		String content = "";
		try {
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(Url);
			
			UrlEncodedFormEntity req_Entity = new UrlEncodedFormEntity(list);
			httpPost.setEntity(req_Entity);
			
			response = httpClient.execute(httpPost);
			HttpEntity res_Entity = response.getEntity();
			
			content = EntityUtils.toString(res_Entity);
			EntityUtils.consume(res_Entity);
		} catch (Exception e) {
			log.error(e.toString());
		}finally{
			releaseConnection(httpClient, response);
		}
		return content;
	}
	
	public  void releaseConnection(CloseableHttpClient httpClient,CloseableHttpResponse response){
		try {
			if(httpClient!=null)
				httpClient.close();
		} catch (IOException e) {
			log.error(e);
		}
		try {
			if(response!=null)
				response.close();
		} catch (IOException e) {
			log.error(e);
		}
	}
}
