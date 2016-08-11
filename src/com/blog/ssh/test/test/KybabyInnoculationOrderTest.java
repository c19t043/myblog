package com.blog.ssh.test.test;

import java.io.IOException;
import java.util.ArrayList;
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

public class KybabyInnoculationOrderTest {
	
	private static Log log = LogFactory.getLog(KybabyInnoculationOrderTest.class);
	
	@Test
	public void testAdd(){
		String url = "http://dev.qiyico.com/kybabyBG/kyinterface/kyInterfaceAction.action";
		//String url = "http://localhost:8082/kybabyBG/kyinterface/kyInterfaceAction.action";
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		list.add(new BasicNameValuePair("action", "inoculationOrder"));
		list.add(new BasicNameValuePair("kyOrderId", "kyorder1234"));
		list.add(new BasicNameValuePair("kyUserId", "kyUserId123"));
		list.add(new BasicNameValuePair("status", "1"));
		list.add(new BasicNameValuePair("opt_time", "2016-08-09 16:00:00"));
		list.add(new BasicNameValuePair("appointment_code", "ky0001"));
		list.add(new BasicNameValuePair("ascription_organ", "7"));
		list.add(new BasicNameValuePair("open_resources_detail_id", "197"));
		list.add(new BasicNameValuePair("open_resources_id", "7"));
		list.add(new BasicNameValuePair("userName", "test001"));
		list.add(new BasicNameValuePair("userPhone", "12345678901235"));
		list.add(new BasicNameValuePair("userAddress", "110"));
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
