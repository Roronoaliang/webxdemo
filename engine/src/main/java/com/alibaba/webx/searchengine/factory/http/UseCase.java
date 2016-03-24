package com.alibaba.webx.searchengine.factory.http;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

/**
 * 【HTTP组件测试用例】
 * 
 * https请求必须加上vm参数：-Djsse.enableSNIExtension=false，否者报错javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
 * 原因：http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {
	
	// 指定URL，以get的形式发送请求，获取响应
	@Test
	public void getWithQueryURL() throws Exception{
		HttpClientWapper HttpClientWapper = null; 
		try {
			byte[] bytes = null;
			HttpClientWapper = HttpClientFactory.getDefaultHttpClientWapper();
			bytes = HttpClientWapper.getWithQueryURL("https://www.heijue.top");
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
	
	
	// 指定URL，以post的形式发送请求，获取响应
	@Test
	public void postWithQueryURL() throws ClientProtocolException, HttpClientException, IOException{
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			byte[] bytes = HttpClientWapper.postWithQueryURL("http://www.gdut.edu.cn/");
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}

	
	// 指定URL和参数，以post的形式发送请求，获取响应
	@Test
	public void postWithParamsMap() throws HttpClientException, IOException {
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("key", "value");
			byte[] bytes = HttpClientWapper.postWithParamsMap("url", paramsMap);
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
	
	
	// 指定URL、参数和JessionId，以post的形式发送请求，获取响应
	@Test
	public void postWithParamsMapAndJessionId() throws HttpClientException, IOException{
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("key", "value");
			byte[] bytes = HttpClientWapper.postWithParamsMapAndJessionId("url", paramsMap, "jessionId");
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
	
	
	// 指定URL、byte类型的参数，以post的形式发送请求，获取响应
	@Test
	public void postWithBytes() throws HttpClientException, IOException{
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			byte[] bs = new byte[1024];
			byte[] bytes = HttpClientWapper.postWithBytes("url", bs);
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
	
	
	// 指定URL和文件，以post的形式发送请求，获取响应
	@Test
	public void postWithFile() throws HttpException, IOException, HttpClientException{
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			byte[] bytes = HttpClientWapper.postWithFile("url", new File("fileUrl"));
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
	
	
	// 指定URL、文件和参数，以post的形式发送请求，获取响应
	@Test
	public void postWithFileAndParamMap() throws HttpException, IOException, HttpClientException{
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("key", "value");
			byte[] bytes = HttpClientWapper.postWithFileAndParamMap("url", new File("fileUrl"), paramsMap);
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
	
	
	// 指定URL和多个文件，以post的形式发送请求，获取响应
	@Test
	public void postWithFileList() throws HttpException, IOException, HttpClientException{
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			List<File> list = new ArrayList<File>();
			list.add(new File("fileUrl"));
			byte[] bytes = HttpClientWapper.postWithFileList("url", list);
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
	
	
	// 指定URL、多个文件和参数，以post的形式发送请求，获取响应
	@Test
	public void postWithFileListAndParamMap() throws HttpException, IOException, HttpClientException{
		HttpClientWapper HttpClientWapper = null; 
		try {
			HttpClientWapper =  HttpClientFactory.getDefaultHttpClientWapper();
			List<File> list = new ArrayList<File>();
			list.add(new File("fileUrl"));
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("key", "value");
			byte[] bytes = HttpClientWapper.postWithFileListAndParamMap("url", list,paramsMap);
			System.out.println(new String(bytes));
		} finally {
			HttpClientWapper.close();
		}
	}
}