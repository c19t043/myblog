package com.blog.ssh.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 在多线程环境中使用同一个HttpClient对象
 */
public class HttpClientTest2 {
	public static void main(String[] args) throws Exception {
		// 创建一个使用ThreadSafeClientConnManager连接管理器的HttpClient实例
		// 当这个HttpClient实例被超过1个线程使用时,必须使用ThreadSafeClientConnManager
		ThreadSafeClientConnManager tscm = new ThreadSafeClientConnManager();
		HttpClient httpclient = new DefaultHttpClient(tscm);
		try {
			// 创建一个需要请求的URL数组
			String[] urisToGet = { "http://www.yshjava.cn",
					"http://hc.apache.org/",
					"http://hc.apache.org/httpcomponents-client-ga/",
					"http://svn.apache.org/viewvc/httpcomponents/" };
			// 为每一个URL创建一个请求线程
			GetThread[] threads = new GetThread[urisToGet.length];
			for (int i = 0; i < threads.length; i++) {
				HttpGet httpget = new HttpGet(urisToGet[i]);
				threads[i] = new GetThread(httpclient, httpget, i + 1);
			}
			// 启动所有请求线程
			for (int j = 0; j < threads.length; j++) {
				threads[j].start();
			}
			// join所有请求线程
			for (int j = 0; j < threads.length; j++) {
				threads[j].join();
			}
		} finally {
			// 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 执行Get请求的线程
	 */
	static class GetThread extends Thread {
		private final HttpClient httpClient;
		private final HttpContext context;
		private final HttpGet httpget;
		private final int id;

		public GetThread(HttpClient httpClient, HttpGet httpget, int id) {
			this.httpClient = httpClient;
			this.context = new BasicHttpContext();
			this.httpget = httpget;
			this.id = id;
		}

		/**
		 * 执行Get请求并打印一些状态信息
		 */
		@Override
		public void run() {
			System.out.println(id + " - about to get something from "
					+ httpget.getURI());
			try {
				// 执行Get请求
				HttpResponse response = httpClient.execute(httpget, context);
				System.out.println(id + " - get executed");
				// 获得以字节数组接收响应实体内容
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					System.out.println(id + " - " + bytes.length
							+ " bytes read");
				}
			} catch (Exception e) {
				httpget.abort();
				System.out.println(id + " - error: " + e);
			}
		}
	}
}
