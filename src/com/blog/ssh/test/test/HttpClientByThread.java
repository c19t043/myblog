package com.blog.ssh.test.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 实际运用，开启线程处理请求，得不到线程返回的结果
 * 
 * <P>分析：新开启的线程，和调用方法所在的线程不一样，在调用方法的线</P>
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class HttpClientByThread {
	private static CloseableHttpClient httpClient;
	private static PoolingHttpClientConnectionManager connManager;
	private static RequestConfig globalConfig;
	private final String url = "";

	private static HttpClientByThread instance;

	static {
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(20);

		// --==========================自定义Cookie策略
		CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
			@Override
			public CookieSpec create(HttpContext context) {
				return new BrowserCompatSpec() {
					@Override
					public void validate(Cookie cookie, CookieOrigin origin)
							throws MalformedCookieException {
						super.validate(cookie, origin);
					}
				};
			}
		};

		Registry<CookieSpecProvider> r = RegistryBuilder
				.<CookieSpecProvider> create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY,
						new BrowserCompatSpecFactory())
				.register("easy", easySpecProvider).build();
		// --==========================自定义Cookie策略

		// =============================================Cookie持久化
		// 创建CookieStore实例
		CookieStore cookieStore = new BasicCookieStore();
		// 新建一个Cookie
		BasicClientCookie cookie = new BasicClientCookie("name", "value");
		cookie.setVersion(0);
		cookie.setDomain(".mycompany.com");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		// =============================================Cookie持久化

		globalConfig = RequestConfig.custom().setConnectionRequestTimeout(5000) // 设置从connectManager获取Connection,超时时间，单位毫秒
				.setConnectTimeout(5000) // 设置连接超时时间，单位毫秒
				.setSocketTimeout(5000) // 请求获取数据的超时时间，单位毫秒
				// .setCookieSpec(CookieSpecs.BEST_MATCH)//基本上将上面的几种规范积聚到一个类中。
				.setCookieSpec("easy").build();

		httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)// 设置全局请求配置
				.setConnectionManager(connManager)// 设置连接管理器
				.setDefaultCookieStore(cookieStore)// 设置cooke存储
				.build();
	}

	public static HttpClientByThread getInstance() {
		if (instance == null) {
			instance = new HttpClientByThread();
		}
		return instance;
	}

	public String doPost() {

		HttpPost httpPost = new HttpPost(url);
		PostThread postThread = new PostThread(httpClient, httpPost);

		postThread.start();
		String result = "";

		try {
			result = postThread.call();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	private String getResponseContent(CloseableHttpResponse response) {
		StringBuilder sb = new StringBuilder();
		InputStream in = null;
		String str = "";
		try {
			HttpEntity res_Entity = response.getEntity();
			in = res_Entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			if ((str = br.readLine()) != null) {
				sb.append(str);
			}
			//EntityUtils.consume(res_Entity);
			
			//result = IOUtils.toString(in, "UTF-8");
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			if (in != null) {
				try {
					in.close();
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

	private class PostThread extends Thread implements Callable<String> {

		private RequestConfig config;

		private CloseableHttpClient httpClient;

		private HttpContext context;

		private HttpPost httpPost;

		private String result;

		public PostThread(CloseableHttpClient httpClient, HttpPost httpPost) {
			this.httpClient = httpClient;
			this.httpPost = httpPost;

			config = RequestConfig.custom().setConnectionRequestTimeout(5000) // 设置从connectManager获取Connection,超时时间，单位毫秒
					.setConnectTimeout(5000) // 设置连接超时时间，单位毫秒
					.setSocketTimeout(5000) // 请求获取数据的超时时间，单位毫秒
					.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)// 根据运行时环境自己选择合适的规范。
					.build();
			this.httpPost.setConfig(config);

			context = new BasicHttpContext();
		}

		@Override
		public void run() {
			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(httpPost, context);
				
				result = getResponseContent(response);

			} catch (IOException e) {
				httpPost.abort();
				System.out.println(e);
			} finally {
				/*
				 * if(httpClient!=null){ try { httpClient.close(); } catch
				 * (IOException e) { e.printStackTrace(); } }
				 */
				if (httpPost != null) {
					httpPost.releaseConnection();
				}
			}
		}

		@Override
		public String call() throws Exception {
			return result;
		}
	}
}
