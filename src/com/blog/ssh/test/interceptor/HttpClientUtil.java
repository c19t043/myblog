package com.blog.ssh.test.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil {

	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	
	private final static String url = "http://develop.jkscw.com.cn/bsky/service";
	
	private final static String authentication = "kybb"+"kybb123456"+"kybbSMFWRegisterInfo";
	
	public static void main(String[] args) throws Exception {
		//System.out.println(inocalutionOrderEdit("1634e0631c7248538bb9a93dc00680b4", "3"));
		System.out.println(SMFWEdit("6d99f986c284402eb738757dfc36d77e","167"));
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
		CloseableHttpClient httpclient=  HttpClients.createDefault();
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(postJson.getBytes("UTF-8")));
		requestBody.setContentLength(postJson.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		CloseableHttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String str = EntityUtils.toString(entity);
		
		releaseConnection(httpclient,response);
		httppost.releaseConnection();
		EntityUtils.consume(entity);
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
		CloseableHttpClient  httpclient=  HttpClients.createDefault();
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(postJson.getBytes("UTF-8")));
		requestBody.setContentLength(postJson.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		CloseableHttpResponse response = httpclient.execute(httppost);
		
		
		HttpEntity entity = response.getEntity();
		String reponseStr = EntityUtils.toString(entity);

		releaseConnection(httpclient,response);
		httppost.releaseConnection();
		EntityUtils.consume(entity);
		return reponseStr;
	}
	
	public static void releaseConnection(CloseableHttpClient  httpclient,CloseableHttpResponse response){
		try {
			if(httpclient!=null)
				httpclient.close();
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
	
	/*public static String doPost(String url,String postJson) throws Exception{
		long time = System.currentTimeMillis();
		String sig =  EncryptUtil.getMD5Str(authentication+time);
		
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
		
		postjon = postjon2;
		
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
	}*/
}
