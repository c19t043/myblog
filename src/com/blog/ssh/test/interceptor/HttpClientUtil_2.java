package com.blog.ssh.test.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;


public class HttpClientUtil_2 {

	//private static Log log = LogFactory.getLog(HttpClientUtil.class);
	
	private final static String url = "http://develop.jkscw.com.cn/bsky/service";
	
	private final static String authentication = "kybb"+"kybb123456"+"kybbSMFWRegisterInfo";
	
	private static CloseableHttpClient httpClient;
	private static PoolingHttpClientConnectionManager connManager;
	private static RequestConfig globalConfig;
	
	private static HttpClientUtil_2 instance;
	public static HttpClientUtil_2 getInstance(){
		if(instance==null)
			instance = new HttpClientUtil_2();
		return instance;
	}
	
	static{
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(20);

		globalConfig = RequestConfig.custom().setConnectionRequestTimeout(5000) // 设置从connectManager获取Connection,超时时间，单位毫秒
				.setConnectTimeout(5000) // 设置连接超时时间，单位毫秒
				.setSocketTimeout(5000) // 请求获取数据的超时时间，单位毫秒
				.build();

		httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)// 设置全局请求配置
				.setConnectionManager(connManager)// 设置连接管理器
				.build();
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(inocalutionOrderEdit("1634e0631c7248538bb9a93dc00680b4", "3"));
		System.out.println(SMFWEdit("840385f92a8047258011fbe8c9a0dca9","167"));
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
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(globalConfig);
		/*BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(postJson.getBytes("UTF-8")));
		requestBody.setContentLength(postJson.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);*/
		
		//StringEntity req_Entity = new StringEntity(postJson,Charsets.UTF_8);
		StringEntity req_Entity = new StringEntity(postJson,"UTF-8");
		httppost.setEntity(req_Entity);
		// 执行客户端请求
		CloseableHttpResponse response = httpClient.execute(httppost);
		
		String reponseStr = getResponseContent(response);
		
		return reponseStr;
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
		httppost.setConfig(globalConfig);
		/*		
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(postJson.getBytes("UTF-8")));
		requestBody.setContentLength(postJson.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		*/
		
		//StringEntity req_Entity = new StringEntity(postJson,Charsets.UTF_8);
		StringEntity req_Entity = new StringEntity(postJson,"UTF-8");
		httppost.setEntity(req_Entity);
		// 执行客户端请求
		CloseableHttpResponse response = httpClient.execute(httppost);
		String reponseStr = getResponseContent(response);
		
		return reponseStr;
	}
	
	private static String getResponseContent(CloseableHttpResponse response) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String str = "";
		try {
			HttpEntity res_Entity = response.getEntity();
			res_Entity = new BufferedHttpEntity(res_Entity);
			InputStream in = res_Entity.getContent();
			br = new BufferedReader(new InputStreamReader(in));

			if ((str = br.readLine()) != null) {
				sb.append(str);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
		return sb.toString();
	}
}
