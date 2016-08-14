package com.blog.ssh.test.httpClient_component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;


public class HttpClientTest1 {
	private static Logger logger = LoggerFactory.getLogger(HttpClientTest1.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static CloseableHttpClient httpClient;
    private static final int MAX_TIMEOUT = 60*1000;
    public static final String DEFAULT_ENCODING="utf-8";
    static {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        requestConfig = configBuilder.build();
    }

    
    /**
     * 发送 SSL POST 请求（HTTPS）
     * 适用于普通参数交互的接口
     * @param apiUrl     API接口URL
     * @param params    参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, List<NameValuePair> pairList,String keyStorePath,String keyStorePassword) {
        createHttpClient(keyStorePath,keyStorePassword);
        System.out.println(httpClient.toString());
        String root=null;
        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(DEFAULT_ENCODING)));
            System.out.println("***********start***********");
            CloseableHttpResponse response = httpClient.execute(httpPost);  //发送请求
            System.out.println("**********end********");
            root = getResult(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
    
    /**
     * 发送 SSL POST 请求（HTTPS）
     * 适用于普通参数交互的接口
     * @param apiUrl     API接口URL
     * @param params    参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params,String keyStorePath,String keyStorePassword) {
        createHttpClient(keyStorePath,keyStorePassword);
        System.out.println(httpClient.toString());
        String root=null;
        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = getHttpClientPair(params);
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(DEFAULT_ENCODING)));
            System.out.println("***********start***********");
            CloseableHttpResponse response = httpClient.execute(httpPost);  //发送请求
            System.out.println("**********end********");
            root = getResult(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
  
    private static List<NameValuePair> getHttpClientPair(Map<String,Object> params){
        List<NameValuePair> pairList = new ArrayList<NameValuePair>(
                params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(),
                    entry.getValue().toString());
            pairList.add(pair);
        }
        return pairList;
    }

    /**
     * 设置HttpClient连接池
     * @param keyStorePath 证书路径  default null
     * @param keyStorePassword 证书密码 default null
     */
    private static void createPoolingHttpClientConnectionManager(String keyStorePath,String keyStorePassword){
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", createSSLConnSocketFactory(keyStorePath,keyStorePassword))
                .build();
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        //设置每个池中每个主机最大链接数
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
    }
    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory(String keyStorePath,String keyStorePassword) {
        SSLConnectionSocketFactory sslsf = null;
        try {
            KeyStore keyStore = getKeyStore(keyStorePath,keyStorePassword);
            if(keyStore==null){
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustStrategyImpl()).build();
                sslsf = new SSLConnectionSocketFactory(sslContext);
            }else {
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
                sslsf = new SSLConnectionSocketFactory(sslContext);
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    /**
     * 获取安全证书
     * @param path  证书路径
     * @param password  证书密码
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    private static KeyStore getKeyStore(String path,String password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore trustStore=null;
        if(StringUtils.isEmpty(path)){
            return trustStore;
        }
        trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(new File(path));
        trustStore.load(instream,password.toCharArray());
        return trustStore;
    }

    /**
     * 获取连接结果
     * @param response
     * @return
     */
    private static String getResult(HttpResponse response){
        String resultStr=null;
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                resultStr = EntityUtils.toString(entity, DEFAULT_ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        logger.info("HttpClient返回状态:"+statusCode);
        logger.info("HttpClient返回结果:"+resultStr);
       // Root root = new Root(statusCode,resultStr);
        return resultStr;
    }

    /**
     * 创建httpClient连接
     * @param keyStorePath
     * @param keyStrorePassword
     */
    private static void createHttpClient(String keyStorePath,String keyStrorePassword){
        createPoolingHttpClientConnectionManager(keyStorePath,keyStrorePassword);
        httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
    }
    /**
     * TrustStrategy实现类
     * @author Admin
     *
     */
    private static class TrustStrategyImpl implements TrustStrategy{

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            return true;
        }
    }
}
