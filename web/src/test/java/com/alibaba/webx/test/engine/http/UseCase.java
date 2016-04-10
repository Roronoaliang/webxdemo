package com.alibaba.webx.test.engine.http;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.webx.searchengine.factory.http.HttpClientFactory;
import com.alibaba.webx.searchengine.factory.http.HttpClientUtil;
import com.alibaba.webx.test.engine.base.EngineBaseTest;

/**
 * 【HTTP组件测试用例】
 * 
 * https请求必须加上vm参数：-Djsse.enableSNIExtension=false，否者报错javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
 * 原因：http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase extends EngineBaseTest<UseCase,HttpClientFactory>{
	
	@Before
	public void before(){
		initTarget("httpClientFactory");
	}
	
	@Test
	public void test(){
		System.out.println("```"+log.getName());
	}
	
	// 指定URL，以get的形式发送请求，获取响应————测试通过
	@Test
	public void getWithQueryURL(){
		HttpClientUtil httpClientUtil = null; 
		try {
			byte[] bytes = null;
			httpClientUtil = target.getHttpClientUtil();
			bytes = httpClientUtil.getWithQueryURL("http://1212.ip138.com/ic.asp");
			System.out.println(new String(bytes,"gb2312"));
		} catch (Exception e) {
			log.error("ERROR",e);
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 指定URL，以post的形式发送请求，获取响应————测试通过
	@Test
	public void postWithQueryURL(){
		HttpClientUtil httpClientUtil = null; 
		try {
			httpClientUtil =  target.getHttpClientUtil();
			byte[] bytes = httpClientUtil.postWithQueryURL("http://1212.ip138.com/ic.asp");
			System.out.println(new String(bytes,"gb2312"));
		} catch (Exception e) {
			log.error("ERROR",e);
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	// 指定URL和参数，以post的形式发送请求，获取响应————测试通过
	@Test
	public void postWithParamsMap(){
		HttpClientUtil httpClientUtil = null; 
		
		try {
			httpClientUtil =  target.getHttpClientUtil();
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("userName", "topview");
			paramsMap.put("password", "123456");
			byte[] bytes = httpClientUtil.postWithParamsMap("http://localhost:8080/topview/demo/screenDemo.htm", paramsMap);
			System.out.println(new String(bytes));
		} catch (Exception e) {
			log.error("ERROR",e);
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 指定URL、参数和JessionId，以post的形式发送请求，获取响应————测试通过
	@Test
	public void postWithParamsMapAndJessionId(){
		setHost();
		HttpClientUtil httpClientUtil = null; 
		
		try {
			httpClientUtil =  target.getHttpClientUtil();
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("key", "value");
			byte[] bytes = httpClientUtil.postWithParamsMapAndJessionId("http://localhost.:8080/topview/demo/screen_demo.htm", paramsMap, "testJessionId");
//			byte[] bytes = httpClientUtil.postWithParamsMap("http://1212.ip138.com/ic.asp", paramsMap);
			System.out.println(new String(bytes));
		} catch (Exception e) {
			log.error("ERROR",e);
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	// 指定URL、byte类型的参数，以post的形式发送请求，获取响应————没测试，后台还真不知道怎么接byte[]数组。。
	@Test
	public void postWithBytes() {
		HttpClientUtil httpClientUtil = null; 
		
		try {
			httpClientUtil =  target.getHttpClientUtil();
			byte[] bs = new byte[1024];
			byte[] bytes = httpClientUtil.postWithBytes("http://localhost:8080/topview/demo/screenDemo.htm", bs);
			System.out.println(new String(bytes));
		} catch (Exception e) {
			log.error("ERROR",e);
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 指定URL和文件，以post的形式发送请求，获取响应————测试通过
	@Test
	public void postWithFile() {
		HttpClientUtil httpClientUtil = null; 
		
		try {
			httpClientUtil =  target.getHttpClientUtil();
			byte[] bytes = httpClientUtil.postWithFile("http://localhost:8080/topview/demo/screenDemo.do", new File("D:/火影头像.jpg"));
			System.out.println(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 指定URL、文件和参数，以post的形式发送请求，获取响应————测试通过
	@Test
	public void postWithFileAndParamMap(){
		
		HttpClientUtil httpClientUtil = null; 
		
		try {
			httpClientUtil =  target.getHttpClientUtil();
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("userName", "topview");
			paramsMap.put("password", "123456");
			byte[] bytes = httpClientUtil.postWithFileAndParamMap("http://localhost:8080/topview/demo/screenDemo.do", new File("D:/火影头像.jpg"), paramsMap);
			System.out.println(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 指定URL和多个文件，以post的形式发送请求，获取响应————测试通过
	@Test
	public void postWithFileList(){
		
		HttpClientUtil httpClientUtil = null; 
		
		try {
			httpClientUtil =  target.getHttpClientUtil();
			List<File> list = new ArrayList<File>();
			list.add(new File("D:/火影头像.jpg"));
			list.add(new File("D:/火影头像2.jpg"));
			byte[] bytes = httpClientUtil.postWithFileList("http://localhost:8080/topview/demo/screenDemo.do", list);
			System.out.println(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 指定URL、多个文件和参数，以post的形式发送请求，获取响应————测试通过
	@Test
	public void postWithFileListAndParamMap(){
		
		HttpClientUtil httpClientUtil = null; 
		
		try {
			httpClientUtil =  target.getHttpClientUtil();
			List<File> list = new ArrayList<File>();
			list.add(new File("D:/火影头像.jpg"));
			list.add(new File("D:/火影头像2.jpg"));
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("userName", "topview");
			paramsMap.put("password", "123456");
			byte[] bytes = httpClientUtil.postWithFileListAndParamMap("http://localhost:8080/topview/demo/screenDemo.do", list,paramsMap);
			System.out.println(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClientUtil.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置代理服务器，为了fiddle抓包。
	 */
	public void setHost(){
		System.setProperty("http.proxyHost", "localhost"); 
		System.setProperty("http.proxyPort", "8888"); 
		System.setProperty("https.proxyHost", "localhost");
		System.setProperty("https.proxyPort", "8888");
	}
}
