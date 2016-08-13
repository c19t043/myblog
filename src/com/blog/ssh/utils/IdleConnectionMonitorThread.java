package com.blog.ssh.utils;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * httpclient连接回收策略： 经典阻塞I/O模型的一个主要缺陷是网络套接字仅在IO操作过程中被阻塞时能对I/O事件反应。
 * 当一个连接被释放回管理器，它可以保持活动然而不能监听套接字状态并对I/O事件作出反应。
 * 如果服务端的连接被关闭，客户端连接不能侦查到连接状态的改变(且不能在客户端通过关闭套接字作出合理反应)。
 * HttpClient通过测试连接是否'陈腐'来尝试缓和这个问题，连接不再有效是因为在执行一个HTTP请求之前被服务端关闭了。
 * 不为空闲连接的每个套接字模型引入一个线程的唯一可行的解决方案是一个用来回收连接(这些连接被认为长时间没有活动)的专用的监听线程。 监听线程可以调用
 * ClientConnectionManager#closeExpiredConnections()来关闭过期的连接和回收关闭的连接， 也可以调用
 * ClientConnectionManager#closeIdleConnections()来关闭在一段时间内的所有空闲连接。
 * 
 * @author xiawq *
 */
public class IdleConnectionMonitorThread extends Thread {
	private final HttpClientConnectionManager connMgr;
	private volatile boolean shutdown;

	public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
		super();
		this.connMgr = connMgr;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					/*
					 * close expired connections
					 * connMgr.closeExpiredConnections(); optionally, close
					 * connections that have been idle longer than 30 sec.
					 */
					connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();// 唤醒其它线程
		}
	}
}
