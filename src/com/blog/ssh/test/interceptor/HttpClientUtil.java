package com.blog.ssh.test.interceptor;

import java.io.ByteArrayInputStream;

import org.springframework.http.HttpEntity;

public class HttpClientUtil {

	private final static String url = "http://192.168.0.224:8080/jqyy-mainweb/bsky/service";
	
	private final static String authentication = "kybb"+"kybb123456"+"kybbSMFWRegisterInfo";
	
	public static void main(String[] args) throws Exception {
		System.out.println(inocalutionOrderEdit("1", "2"));
		//System.out.println(SMFWEdit("1","38"));
	}
	/**
	 * 修改计免订单状态
	 * @param orderId 巴蜀快医订单id
	 * @param status 订单状态
	 * @return
	 * @throws Exception
	 */
	public static String inocalutionOrderEdit(String orderId,String status)throws Exception{
		long time = System.currentTimeMillis();
		String sig =  EncryptUtil.getMD5Str(authentication+time);
		//修改计免订单状态
		String postJson ="{\"head\":"
				+ "{\"accountid\":\"kybb\","
				+ "\"code\":\"kybbJMEnd\","//方法名
				+ "\"timestamp\":\""+time+"\","
				+ "\"sig\":\""+sig+"\"},"
				+ "\"content\":"
				+ "{\"JM_REGISTER_ID\":\""+orderId+"\","//订单id
				+ "\"STATE\":\""+status+"\"}"//状态
				+ "}";
		/*{head:{accountid:kybb,code:kybbJMEnd},content:{JM_REGISTER_ID:123asdf,STATE:2}}*/
		HttpPost httppost = new HttpPost(url);
		HttpClient httpclient=  new DefaultHttpClient();
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(postJson.getBytes("UTF-8")));
		requestBody.setContentLength(postJson.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String str = EntityUtils.toString(entity);
		httppost.releaseConnection();
		EntityUtils.consume(entity);
		/*entity.consumeContent();*/
		return str;
	}
	
	/**
	 * 上门服务修改订单数据u
	 * @param orderId 巴蜀快医订单id
	 * @param doctorId 医生id
	 * @return
	 * @throws Exception
	 */
	public static String SMFWEdit(String orderId,String doctorId) throws Exception{
		long time = System.currentTimeMillis();
		String sig =  EncryptUtil.getMD5Str(authentication+time);
		//上门服务修改订单数据u
		String postJson ="{\"head\":"
				+ "{\"accountid\":\"kybb\","
				+ "\"code\":\"kybbSMFWRegisterInfo\","//方法名
				+ "\"timestamp\":\""+time+"\","
				+ "\"sig\":\""+sig+"\"},"
				+ "\"content\":"
				+ "{\"JM_REGISTER_ID\":\""+orderId+"\","//订单id
				+ "\"BSKY_DOCTOR_ID\":\""+doctorId+"\"}"//医生id
				+ "}";
		
		HttpPost httppost = new HttpPost(url);
		HttpClient httpclient=  new DefaultHttpClient();
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(postJson.getBytes("UTF-8")));
		requestBody.setContentLength(postJson.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		
		
		HttpEntity entity = response.getEntity();
		String reponseStr = EntityUtils.toString(entity);
		entity.consumeContent();
		httppost.releaseConnection();
		EntityUtils.consume(entity);
		return reponseStr;
	}
	

	public static String doPost(String url,String postJson) throws Exception{
/*		long time = System.currentTimeMillis();
		String sig =  EncryptUtil.getMD5Str(authentication+time);*/
		/*
	    String postjon = "";
		//上门服务修改订单数据u
		String postjon1 ="{\"head\":"
				+ "{\"accountid\":\"kybb\","
				+ "\"code\":\"kybbSMFWRegisterInfo\","//方法名
				+ "\"timestamp\":\""+time+"\","
				+ "\"sig\":\""+sig+"\"},"
				+ "\"content\":"
				+ "{\"JM_REGISTER_ID\":\"123asdf\","//订单id
				+ "\"BSKY_DOCTOR_ID\":\"doctorid\"}"//医生id
				+ "}";
		//修改计免订单状态
		String postjon2 ="{\"head\":"
				+ "{\"accountid\":\"kybb\","
				+ "\"code\":\"kybbJMEnd\","//方法名
				+ "\"timestamp\":\""+time+"\","
				+ "\"sig\":\""+sig+"\"},"
				+ "\"content\":"
				+ "{\"JM_REGISTER_ID\":\"123asdf\","//订单id
				+ "\"STATE\":\"2\"}"//状态
				+ "}";
		
		postjon = postjon2;*/
		
		HttpPost httppost = new HttpPost(url);
		HttpClient httpclient=  new DefaultHttpClient();
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(postJson.getBytes("UTF-8")));
		requestBody.setContentLength(postJson.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		
		String reponseStr = EntityUtils.toString(entity);
		httppost.releaseConnection();
		return reponseStr;
	}
}
