package com.blog.ssh.test.httpClient_component;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PathVariable;

@SuppressWarnings("all")
public class Test1 {
	private static CloseableHttpClient httpClient;
	private static PoolingHttpClientConnectionManager connManager;
	private static RequestConfig globalConfig;
	private Log log = LogFactory.getLog(Test1.class);
	
	private String url;
	
	// 1.Get
	public static String getResultWithGet(HttpServletRequest request, String url)
			throws Exception {
		String result = null;
		HttpClient client = getClient();
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			result = getResponseBodyAsString(response);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}

	// 2、Post
	public static String getResultWithPost(HttpServletRequest request,
			String url) throws Exception {
		String json = null;
		HttpClient client = getClient();
		try {
			HttpPost post = new HttpPost(url);
			Map<String, String[]> map = request.getParameterMap();
			Set<String> keySet = map.keySet();
			JSONObject jo = new JSONObject();
			for (String s : keySet) {
				if (!"".equals(map.get(s)[0])) {
					jo.element(s, map.get(s)[0]);
				}

			}
			StringEntity reqEntity = new StringEntity(jo.toString(), "UTF-8");
			reqEntity.setContentType("application/json");
			post.setEntity(reqEntity);
			HttpResponse response = client.execute(post);
			json = getResponseBodyAsString(response);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return json;
	}

	// 3、Put
	public static String getResultWithPut(HttpServletRequest request, String url) throws Exception{
		String json = null;
		HttpClient client = getClient(); 
		try{
			HttpPut put = new HttpPut(url);
			@SuppressWarnings("unchecked")
			Map<String, String[]> map = request.getParameterMap();
			Set<String> keySet = map.keySet();
			JSONObject jo = new JSONObject();
			for(String s : keySet){
				if(!"".equals(map.get(s)[0])){
					jo.element(s, map.get(s)[0]);
				}
				
			}
			StringEntity reqEntity = new StringEntity(jo.toString(),"UTF-8");
			reqEntity.setContentType("application/json");
			put.setEntity(reqEntity);
			HttpResponse response = client.execute(put);
			json = getResponseBodyAsString(response);
		}finally{
			client.getConnectionManager().shutdown();  
		}
		return json;
	}

	private static HttpClient getClient() {
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(20);
		
		globalConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(5000) // 设置从connectManager获取Connection,超时时间，单位毫秒
				.setConnectTimeout(5000) // 设置连接超时时间，单位毫秒
				.setSocketTimeout(5000) // 请求获取数据的超时时间，单位毫秒
				.build();
		
		httpClient = HttpClients.custom()
				.setDefaultRequestConfig(globalConfig)//设置全局请求配置
				.setConnectionManager(connManager)//设置连接管理器
				.build();
		return httpClient;
	}

	// 4、Delete
	public static String getResultWithDelete(HttpServletRequest request,
			String url) throws Exception {
		String result = null;
		HttpClient client = getClient();
		try {
			HttpDelete delete = new HttpDelete(url);
			HttpResponse response = client.execute(delete);
			result = getResponseBodyAsString(response);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}

	// 5、getResponseBodyAsString
	public static String getResponseBodyAsString(HttpResponse response)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity != null) {
			httpEntity = new BufferedHttpEntity(httpEntity);
			InputStream is = httpEntity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			is.close();
		}
		return sb.toString();
	}

	// 6、文件上传
	public String uploadAttachment(HttpServletRequest request) {
		String json = null;
		HttpClient client = getClient();
		try {
			
			HttpPost post = new HttpPost(url);

			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			@SuppressWarnings("unchecked")
			List<FileItem> fileList = upload.parseRequest(request);
			Iterator<FileItem> it = fileList.iterator();
			List<File> tempFileList = new ArrayList<File>();
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					if (fileName != null) {
						File file = new File(fileName);
						item.write(file);
						MultipartEntity multipartEntity = new MultipartEntity(
								HttpMultipartMode.BROWSER_COMPATIBLE, null,
								Charset.forName("UTF-8"));
						FileBody fileBody = new FileBody(file);
						multipartEntity.addPart(fileName, fileBody);
						post.setEntity(multipartEntity);
						tempFileList.add(file);
					}
				}
			}
			HttpResponse response = client.execute(post);
			json = getResponseBodyAsString(response);
			// delete temp files
			for (File file : tempFileList) {
				file.delete();
			}
		} catch (Exception e) {
			log.error(e);
			/*json = JSONUtils.getJsonString(Const.ERROR_MESSAGE,
					EM.TICKET_EXCEPTION);*/
		} finally {
			client.getConnectionManager().shutdown();
		}
		return json;
	}

	// 7、文件下载
	public void downloadAttachment(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("fileId") Integer fileId) {
		HttpClient client = getClient();
		try {
			HttpGet get = new HttpGet(url);

			ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>() {
				public byte[] handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						return EntityUtils.toByteArray(entity);
					} else {
						return null;
					}
				}
			};
			byte[] charts = client.execute(get, handler);

			URL url_ = new URL(url);
			HttpURLConnection uc = (HttpURLConnection) url_.openConnection();
			response.reset();
			response.addHeader("Content-disposition",
					uc.getHeaderField("Content-disposition"));
			OutputStream output = new BufferedOutputStream(
					response.getOutputStream());
			output.write(charts);
			output.flush();
			output.close();
			get.releaseConnection();
		} catch (Exception e) {
			log.error(e);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
}
