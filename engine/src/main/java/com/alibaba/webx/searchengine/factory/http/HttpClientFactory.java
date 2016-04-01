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

	public static int maxConnectionNum; 			// 最大连接数 ：1000个连接
	public static int maxGetConnectionTimeOut; 		// 获取连接的最大等待时间 ：5s超时
	public static int maxRouteConnectionNum; 		// 每个路由最大连接数：1000个
	public static int maxLastConnectionTimeOut; 	// 连接超时时间 ：60s超时
	public static int maxGetDataTimeOut;			// 读取超时时间：60s超时

	/**
	 * 初始化连接池
	 */
	public void init() {
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

	/**
	 * 获取HttpClientUtil工具
	 * 
	 * @return
	 */
	public HttpClientUtil getHttpClientUtil() {
		return new HttpClientUtil(cm);
	}

	
	// get & set
	public static int getMaxConnectionNum() {
		return maxConnectionNum;
	}

	public static int getMaxGetConnectionTimeOut() {
		return maxGetConnectionTimeOut;
	}

	public static void setMaxGetConnectionTimeOut(int maxGetConnectionTimeOut) {
		HttpClientFactory.maxGetConnectionTimeOut = maxGetConnectionTimeOut;
	}

	public static int getMaxRouteConnectionNum() {
		return maxRouteConnectionNum;
	}

	public static void setMaxRouteConnectionNum(int maxRouteConnectionNum) {
		HttpClientFactory.maxRouteConnectionNum = maxRouteConnectionNum;
	}

	public static int getMaxLastConnectionTimeOut() {
		return maxLastConnectionTimeOut;
	}

	public static void setMaxLastConnectionTimeOut(int maxLastConnectionTimeOut) {
		HttpClientFactory.maxLastConnectionTimeOut = maxLastConnectionTimeOut;
	}

	public static int getMaxGetDataTimeOut() {
		return maxGetDataTimeOut;
	}

	public static void setMaxGetDataTimeOut(int maxGetDataTimeOut) {
		HttpClientFactory.maxGetDataTimeOut = maxGetDataTimeOut;
	}

	public static void setMaxConnectionNum(int maxConnectionNum) {
		HttpClientFactory.maxConnectionNum = maxConnectionNum;
	}
}