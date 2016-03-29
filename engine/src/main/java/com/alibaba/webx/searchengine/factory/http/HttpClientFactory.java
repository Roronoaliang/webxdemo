package com.alibaba.webx.searchengine.factory.http;

import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

/**
 * http工厂
 * 
 * https请求必须加上vm参数：-Djsse.enableSNIExtension=false，否者报错javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
 * 原因：http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
 * 
 * @author xiaoMzjm
 *
 */
public class HttpClientFactory {

	private static PoolingHttpClientConnectionManager cm;

	public int maxConnectionNum = 1000; // 最大连接数 ：1000个连接
	public int maxGetConnectionTimeOut = 5000; // 获取连接的最大等待时间 ：5s超时
	public int maxRouteConnectionNum = 1000; // 每个路由最大连接数：1000个
	public int maxLastConnectionTimeOut = 60000; // 连接超时时间 ：60s超时
	public int maxGetDataTimeOut = 60000; // 读取超时时间：60s超时

	public void init() {
		System.setProperty("jsse.enableSNIExtension", "false");
		// https支持,但运行时要加上vm参数：-Djsse.enableSNIExtension=false，否者报错javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
		// 原因：http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
		SSLContext sslcontext = null;
		SSLConnectionSocketFactory sockerFactory = null;
		try {
			sslcontext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustSelfSignedStrategy())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sockerFactory = new SSLConnectionSocketFactory(sslcontext,NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http",
						PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sockerFactory).build();
		
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setMaxTotal(maxConnectionNum);
		cm.setDefaultMaxPerRoute(maxRouteConnectionNum);
	}

	public static HttpClientUtil getDefaultHttpClientWapper() {
		return new HttpClientUtil(cm);
	}

	public int getMaxConnectionNum() {
		return maxConnectionNum;
	}

	public void setMaxConnectionNum(int maxConnectionNum) {
		this.maxConnectionNum = maxConnectionNum;
	}

	public int getMaxGetConnectionTimeOut() {
		return maxGetConnectionTimeOut;
	}

	public void setMaxGetConnectionTimeOut(int maxGetConnectionTimeOut) {
		this.maxGetConnectionTimeOut = maxGetConnectionTimeOut;
	}

	public int getMaxRouteConnectionNum() {
		return maxRouteConnectionNum;
	}

	public void setMaxRouteConnectionNum(int maxRouteConnectionNum) {
		this.maxRouteConnectionNum = maxRouteConnectionNum;
	}

	public int getMaxLastConnectionTimeOut() {
		return maxLastConnectionTimeOut;
	}

	public void setMaxLastConnectionTimeOut(int maxLastConnectionTimeOut) {
		this.maxLastConnectionTimeOut = maxLastConnectionTimeOut;
	}

	public int getMaxGetDataTimeOut() {
		return maxGetDataTimeOut;
	}

	public void setMaxGetDataTimeOut(int maxGetDataTimeOut) {
		this.maxGetDataTimeOut = maxGetDataTimeOut;
	}
}
