package com.blog.ssh.test.httpClient_component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
 

import javax.net.ssl.SSLContext;
 

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
 
public class HttpClientTest3 {
 
    /**
     * 管理HTTP连接和连接管理器
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public void m1() throws InterruptedException, ExecutionException, IOException{
        HttpClientContext context=HttpClientContext.create();
         
        //BasicHttpClientConnectionManager是单个线程安全的连接管理器，一次只有一个连接。
        HttpClientConnectionManager connMrg=new BasicHttpClientConnectionManager();
        HttpRoute route=new HttpRoute(new HttpHost("localhost", 80));//请求路由
         
        //请求新连接，这可能是个很长的过程
        ConnectionRequest connRequest=connMrg.requestConnection(route, null);
        //等待连接10秒
        HttpClientConnection conn=connRequest.get(10, TimeUnit.SECONDS);
         
        //必要时可使用 ConnectionRequest#cancel()提前终止连接请求，并会解开 ConnectionRequest#get()的阻塞线程
         
        try {
            //如果连接没有打开
            if(!conn.isOpen()){
                //基于它的路由信息在1000毫秒内创建连接
                connMrg.connect(conn, route, 1000, context);
                //当路由完成即所有中间跳完成，连接完全建立时时标记
                connMrg.routeComplete(conn, route, context);
            }
            //用连接做一些事。。。
        } finally {
            //释放连接到还回到管理器，连接存活1分钟，便于被其它消费者使用
            connMrg.releaseConnection(conn, null, 1, TimeUnit.MINUTES);
        }
    }
     
    /**
     * PoolingHttpClientConnectionManager-管理客户端连接池，可以服务于来自多个执行进程的连接请求。
     * 这些连接是基于每一个路由被池化的。管理器会租借一个持久可用的连接给一个路由的一个请求而不是新建一个连接。
     * 连接管理的实现默认可为每一个路由创建不多于2个且总数不超过20个的并发连接。
     * 然而在真实应用环境中这些限制对性能提高有限，特别是HTTP协议传输服务的时候。
     * 不过下面的例子展示了如何调整连接池的参数：
     */
    public void m2(){
        PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();
         
        //为单路由设置默认最大并发连接数为20
        cm.setDefaultMaxPerRoute(20);
         
        //为地址localhost:80的主机设置最大并发连接数为50
        HttpHost localhost=new HttpHost("localhost", 80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);
         
        CloseableHttpClient client=HttpClients.custom()
                .setConnectionManager(cm)
                .build();
         
        /*连接管理器关闭：保证管理器中的连接得以关闭，且被这些连接分配的系统资源得以释放*/
        //ClosableHttpClient httpClient = <...>
        //httpClient.close();
    }
     
    /**
     * 执行多线程请求---在执行多线程请求时httpClient是线程安全的且可共享。
     * 这里推荐每个线程最好拥有专用的HttpContext实例。
     * @throws InterruptedException
     */
    public void m3() throws InterruptedException{
        PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();
        CloseableHttpClient client=HttpClients.custom().setConnectionManager(cm).build();
         
        //URIs to perform GETs on
        String[] urisToGet={
            "http://www.domain1.com",
            "http://www.domain2.com",
            "http://www.domain3.com",
            "http://www.domain4.com"
        };
         
        //create a thread for each URI
        GetThread[] threads=new GetThread[urisToGet.length];
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpGet=new HttpGet(urisToGet[i]);
            threads[i]=new GetThread(client, httpGet);
        }
         
        //start the threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
         
        //join the threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
     
    /**
     * 连接保持存活策略与keep-alive头
     */
    public void m4(){
        ConnectionKeepAliveStrategy myStrategy=new ConnectionKeepAliveStrategy() {
             
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                //Honor 'keep-alive' header
                HeaderElementIterator iterator=new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (iterator.hasNext()) {
                    HeaderElement he = (HeaderElement) iterator.next();
                    String param=he.getName();
                    String value=he.getValue();
                    if(value != null && param.equalsIgnoreCase("timeout")){
                        try {
                            return Long.parseLong(value)*1000;
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
                if("www.naughty-server.com".equalsIgnoreCase(target.getHostName())){
                    //keep alive for 5 seconds only
                    return 5*1000;
                }else{
                    //otherwise keep alive for 30 seconds
                    return 30*1000;
                }
            }
        };
        CloseableHttpClient client=HttpClients.custom()
                .setKeepAliveStrategy(myStrategy)
                .build();
    }
     
    /**
     * 连接套接字工厂-依靠 ConnectionSocketFactory接口去创建、初始化、连接套接字。
     * PlainConnectionSocketFactory是创建和初始化原生(非加密的)套接字的默认工厂类。
     * 
     * 安全套接字层连接工厂类LayeredConnectionSocketFactory是ConnectionSocketFactory接口的扩展，
     * 可以再纯socket上创建套接字层。
     * 
     * HttpClient也可使用实现了SSL/TLS层的SSLSocketFactory。注意HttpClient不适用任何自定义的加密功能。
     * 它完全依赖于标准的Java Cryptography(JCE) 和 安全套接字扩展(JSEE)。
     * @throws IOException 
     */
    public void m5() throws IOException{
        HttpClientContext clientContext=HttpClientContext.create();
        PlainConnectionSocketFactory sf=PlainConnectionSocketFactory.getSocketFactory();
        //创建并初始化套接字
        Socket socket=sf.createSocket(clientContext);
         
        int timeout=1000;//ms
        HttpHost target=new HttpHost("localhost");
        InetSocketAddress remoteAddress=new InetSocketAddress(
                InetAddress.getByAddress(new byte[]{127,0,0,1}), 80);
        //连接套接字
        sf.connectSocket(timeout, socket, target, remoteAddress, null, clientContext);
    }
     
    /**
     * 连接套接字工厂整合连接管理器：
     * 自定义连接套接字工厂可以和一个特殊的协议如HTTP或HTTPS相关联，然后用来创建一个自定义的连接管理器
     */
    public void m6(){
        ConnectionSocketFactory plainsf=PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf=SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
         
        HttpClientConnectionManager cm=new PoolingHttpClientConnectionManager(r);
        CloseableHttpClient client=HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }
     
    /**
     * SSL/TLS定制化：
     * HttpClient使用SSLConnectionSocketFactory去创建SSL连接。SSLConnectionSocketFactory允许高度定制化。
     * 它利用javax.net.ssl.SSLContext的一个实例作为参数，并用它创建定制配置的SSL连接。
     * 该部分需要对SSL/TLS协议有一定程度的了解。
     */
    public void m7(){
//      KeyStore myTrustStore = <...>
//      SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(myTrustStore).build();
//      SSLConnectionSocketFactory sslsf=new SSLConnectionSocketFactory(sslContext);
    }
     
    /**
     * 主机名验证：除了在SSL/TSL协议层扮演信任验证和客户端认证角色之外，HttpClient能选择性的去验证
     * 目标主机名称是否和储存在服务器中X.509证书上的名称一致。一旦连接已经建立，验证过程能提供服务器
     * 信任材料的额外可靠性保证。
     * javax.net.ssl.HostnameVerifier接口体现了一种主机名验证策略。
     * javax.net.ssl.HostnameVerifier有两种实现HttpClient可以用来工作。
     * 注意：主机名验证和SSL信任验证不可混淆。
     * 
     * (1)DefaultHostnameVerifier:默认实现，遵从RFC2818。主机名必须符合指定证书上任意备选名称。
     * (2)NoopHostnameVerifier:该主机名验证器本质上会关闭主机名验证。它接受任何有效的和符合目标主机的SSL会话。
     */
    public void m8(){
        /*每一个HttpClient会使用默认的DefaultHostnameVerifier实现，如果你想也可以指定实现*/
        SSLContext sslContext=SSLContexts.createSystemDefault();
//      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.Instance);
         
//      HttpClient 4.4版本使用公共后缀列表来保证SSL证书中的通配符不会被误用，当应用在有一个共同顶级域名下的子域名的时候。
//      【参考：https://publicsuffix.org/list/effective_tld_names.dat】。强烈建议制作一个列表的本地副本，从源位置
//      location即https://publicsuffix.org/list/effective_tld_names.dat下载这个列表的次数每天不超过一次。
//      
//      PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(
//              PublicSuffixMatcher.class.getResource("my-copy-effective_tld_names.dat"));
//      DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
         
//      禁用公共后缀验证
//      DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(null);
    }
     
    /**
     * HttpClient代理配置：通过代理连接目标主机。
     * 即使HttpClient意识到复杂的路由方案和代理链，它只支持简单直接的或者说箱外一跳代理连接。
     * 例子中有三种配置方式
     */
    public void m9(){
//      1.默认的代理参数
//      HttpHost proxy = new HttpHost("someproxy", 8080);
//      DefaultProxyRoutePlanner routePlanner=new DefaultProxyRoutePlanner(proxy);
         
//      或 2.用标准的JRE代理选择器获取代理信息
//      SystemDefaultRoutePlanner routePlanner=new SystemDefaultRoutePlanner(
//              ProxySelector.getDefault());
         
//      或 3.定制实现RoutePlanner，以完全控制HTTP路由计算的过程
        HttpRoutePlanner routePlanner = new HttpRoutePlanner() {
             
            public HttpRoute determineRoute(
                    HttpHost target, 
                    HttpRequest request, 
                    HttpContext context) throws HttpException {
                 
                return new HttpRoute(
                        target,//目标主机
                        null,//本地地址
                        new HttpHost("someproxy", 8080),//代理主机
                        "https".equalsIgnoreCase(target.getSchemeName()));//是否安全
            }
        };
         
        CloseableHttpClient client=HttpClients.custom()
                .setRoutePlanner(routePlanner)
                .build();
    }
    private class GetThread extends Thread { 
    	private final CloseableHttpClient httpClient; 
    	private final HttpContext context; 
    	private final HttpGet httpGet; 
    	public GetThread(CloseableHttpClient httpClient, HttpGet httpGet) 
    	{ 
    		this.httpClient = httpClient; 
    		this.context = HttpClientContext.create(); 
    		this.httpGet = httpGet; 
    	} 
    	@Override 
    	public void run() 
    	{ 
    		try { 
    			CloseableHttpResponse response= httpClient.execute(httpGet, context); 
    			try { 
    				HttpEntity entity = response.getEntity();
    				entity.writeTo(new FileOutputStream(new File("D:\\"+new Date().getTime()+".txt"))); 
    			} finally { 
    				response.close(); 
    			} 
    		} catch (Exception e) { 
    			//handle protocal errors } catch (IOException e) { //handle I/O errors } } }
    		}
    	}
    }
}
