package com.alibaba.webx.searchengine.factory.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientWapper  extends BaseHttpClientWapper {
	
	private CloseableHttpClient httpclient = null;
	
	public HttpClientWapper(PoolingHttpClientConnectionManager cm){
		
		httpclient = HttpClients.custom().setConnectionManager(cm).build();
	
	}

	/******************************public**********************************/
	
	/**
	 * 关闭httpClient
	 * @throws IOException
	 */
	public void close() throws IOException{
		this.httpclient.close();
	}
	
	/**
     * @description : 通过URL，以get方式请求服务器，返回字节数组
     * 
     * @param queryURL
     * @throws HttpClientException
     * @return byte[]
	 * @throws IOException 
	 * @throws ClientProtocolException 
     */
	public byte[] getWithQueryURL(String queryURL) throws HttpClientException, ClientProtocolException, IOException {
		if(StringUtils.isBlank(queryURL)) {
			throw new HttpClientException("queryURL is null.");
		}
		byte[] newbuf = executeByGet(queryURL);
        if ((newbuf == null) || (newbuf.length == 0)) {
            throw new HttpClientException("Server response is null: " + queryURL);
        }
        return newbuf;
	}

	/**
     * @description : 通过URL，以post方式请求服务器，返回字节数组
     * 
     * @param queryURL
     * @param paramsMap
     * @throws HttpClientException
     * @return byte[]
	 * @throws IOException 
     */
	public byte[] postWithQueryURL(String queryURL) throws HttpClientException, IOException {
		if(StringUtils.isBlank(queryURL)) {
            throw new HttpClientException("queryURL is null.");
        }
        return postWithParamsMap(queryURL,null);
	}

	/**
     * @description : 通过URL和paramsMap参数，以post方式请求服务器，返回字节数组
     * 
     * @param queryURL
     * @param paramsMap
     * @throws HttpClientException
     * @return byte[]
	 * @throws IOException 
     */
	public byte[] postWithParamsMap(String queryURL,
			Map<String, String> paramsMap) throws HttpClientException, IOException {
		if(StringUtils.isBlank(queryURL)) {
    		throw new HttpClientException("queryURL is null.");
    	}
    	byte[] newbuf = executeByPostWithParamsMap(queryURL,paramsMap,null);
        if ((newbuf == null) || (newbuf.length == 0)) {
            throw new HttpClientException("Server response is null: " + queryURL);
        }
        return newbuf;
	}
	
	/**
	 * @description : 通过URL和paramsMap参数和jessionId，以post方式请求服务器，返回字节数组
	 * 
	 * @param queryURL
	 * @param paramsMap
	 * @param jessionId
	 * @return
	 * @throws HttpClientException
	 * @throws IOException
	 */
	public byte[] postWithParamsMapAndJessionId(String queryURL,
			Map<String, String> paramsMap,String jessionId) throws HttpClientException, IOException {
		if(StringUtils.isBlank(queryURL)) {
    		throw new HttpClientException("queryURL is null.");
    	}
    	byte[] newbuf = executeByPostWithParamsMap(queryURL,paramsMap,jessionId);
        if ((newbuf == null) || (newbuf.length == 0)) {
            throw new HttpClientException("Server response is null: " + queryURL);
        }
        return newbuf;
	}

	/**
     * @description : 通过URL和bytes参数，以post方式请求服务器，返回字节数组
     * 
     * @param queryURL
     * @param bytes
     * @throws HttpClientException
     * @return byte[]
	 * @throws IOException 
     */
	public byte[] postWithBytes(String queryURL, byte[] bytes)
			throws HttpClientException, IOException {
		if(StringUtils.isBlank(queryURL)) {
    		throw new HttpClientException("queryURL is null.");
    	}
		if(bytes == null) {
			throw new HttpClientException("bytes is null.");
		}
    	byte[] newbuf = executeByPostWithBytes(queryURL,bytes);
        if ((newbuf == null) || (newbuf.length == 0)) {
            throw new HttpClientException("Server response is null: " + queryURL);
        }
        return newbuf;
	}

	/**
     * @description  : 通过URL和file参数，以post方式请求服务器，返回字节数组
     * @param queryURL
     * @param file
     * @return byte[]
     * @throws IOException 
     * @throws HttpException 
     * @throws HttpClientException 
     */
	public byte[] postWithFile(String queryURL, File file)
			throws HttpException, IOException, HttpClientException {
		return postWithFileAndParamMap(queryURL, file, null);
	}

	/**
     * @description 通过URL、file参数、key-value参数，以post方式请求服务器，返回字节数组
     * @param queryURL
     * @param file
     * @param paramMap
     * @return
     * byte[]
     * @throws IOException 
     * @throws HttpException 
     * @throws HttpClientException 
     */
	public byte[] postWithFileAndParamMap(String queryURL, File file,
			Map<String, String> paramMap) throws HttpException, IOException,
			HttpClientException {
		if(StringUtils.isBlank(queryURL)) {
    		throw new HttpClientException("queryURL is null.");
    	}
    	if(file == null) {
    		throw new HttpClientException("file is null.");
    	}
    	List<File> fileList = new ArrayList<File>();
    	fileList.add(file);
    	return executeByPostWithFileListAndParamMap(queryURL, fileList, paramMap);
	}

	/**
     * @description 通过URL、file参数、key-value参数，以post方式请求服务器，返回字节数组
     * @param queryURL
     * @param file
     * @param paramMap
     * @return
     * byte[]
     * @throws IOException 
     * @throws HttpException 
     * @throws HttpClientException 
     */
	public byte[] postWithFileList(String queryURL, List<File> fileList)
			throws HttpClientException, HttpException, IOException {
		if(StringUtils.isBlank(queryURL)) {
    		throw new HttpClientException("queryURL is null.");
    	}
    	if(fileList == null) {
    		throw new HttpClientException("file is null.");
    	}
    	return executeByPostWithFileListAndParamMap(queryURL, fileList, null);
	}

	/**
     * @description 通过URL、fileList、paramMap参数，以post方式请求服务器，返回字节数组
     * 
     * @param queryURL
     * @param fileList
     * @param paramMap
     * @return
     * @throws HttpClientException
     * @throws HttpException
     * @throws IOException
     * byte[]
     */
	public byte[] postWithFileListAndParamMap(String queryURL,
			List<File> fileList, Map<String, String> paramMap)
			throws HttpClientException, HttpException, IOException {
		if(StringUtils.isBlank(queryURL)) {
    		throw new HttpClientException("queryURL is null.");
    	}
    	if(fileList == null) {
    		throw new HttpClientException("file is null.");
    	}
    	if(paramMap == null){
    		throw new HttpClientException("paramMap is null.");
    	}
		return executeByPostWithFileListAndParamMap(queryURL, fileList, paramMap);
	}
	
	
	/******************************private**********************************/
	
	/**
	 * 通过get提交
	 * 
	 * @param queryURL
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws HttpClientException
	 */
	private byte[] executeByGet(String queryURL) throws ClientProtocolException, IOException, HttpClientException {
		// 创建get方法
        HttpGet httpget = new HttpGet(queryURL);
        
        // 设置header
        httpget.setHeader("Content-type" , "application/x-www-form-urlencoded; charset=UTF-8");
        
        // 提交请求
        CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpget);
        
        // 返回字节流
        byte[] responseBody = null;
        try {
            responseBody = getBytesFromInpuStream(closeableHttpResponse.getEntity().getContent());
        } finally {
        	httpget.releaseConnection();
        	closeableHttpResponse.close();
        }
        return responseBody;
	}
	
	/*
	 * 通过post提交，带字符串参数
	 */
	private byte[] executeByPostWithParamsMap(String queryURL,
		Map<String, String> paramsMap,String jessionId) throws HttpClientException, IOException {
		HttpPost httppost = null;
		// 创建get方法
        httppost = new HttpPost(queryURL);
        
        // 设置header
        httppost.setHeader("Content-type" , "application/x-www-form-urlencoded; charset=UTF-8");
        if(StringUtils.isNotBlank(jessionId)) {
        	httppost.setHeader("Cookie","JSESSIONID="+jessionId);
        }
        
        // 设置参数
        if(paramsMap != null && paramsMap.size() > 0) {
        	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        	Set<Entry<String, String>> entrySet = paramsMap.entrySet();
        	Iterator<Entry<String, String>> iterator = entrySet.iterator();
        	while(iterator.hasNext()) {
        		Entry<String, String> entry = iterator.next();
        		if(entry.getKey() != null) {
        			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        		}
        	}
        	//设置表单提交编码为UTF-8
        	HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
        	httppost.setEntity(httpEntity);
        }
        
        // 提交请求
        CloseableHttpResponse closeableHttpResponse = httpclient.execute(httppost);
        
        // 返回字节流
        byte[] responseBody = null;
        try {
            responseBody = getBytesFromInpuStream(closeableHttpResponse.getEntity().getContent());
        }finally {
        	httppost.releaseConnection();
        	closeableHttpResponse.close();
        }
        return responseBody;
	}
	
	/**
	 * 通过post提交，带byte参数
	 * @param queryURL
	 * @param bytes
	 * @return
	 * @throws HttpClientException
	 * @throws IOException
	 */
	private byte[] executeByPostWithBytes(String queryURL, byte[] bytes) throws HttpClientException, IOException {
		// 创建get方法
        HttpPost httppost = new HttpPost(queryURL);
        
        // 设置header
        httppost.setHeader("Content-type" , "text/plain; charset=UTF-8");
        
        // 设置byte[]参数
        HttpEntity httpEntity = new ByteArrayEntity(bytes);
        httppost.setEntity(httpEntity);
        
        // 提交请求
        CloseableHttpResponse closeableHttpResponse = httpclient.execute(httppost);
        
        // 返回字节流
        byte[] responseBody = null;
        try {
            responseBody = getBytesFromInpuStream(closeableHttpResponse.getEntity().getContent());
        } finally {
        	httppost.releaseConnection();
        	closeableHttpResponse.close();
        }
        return responseBody;
	}

	/**
	 * 通过post提交，带字符串参数 & 文件
	 * @param queryURL
	 * @param fileList
	 * @param paramsMap
	 * @return
	 * @throws HttpClientException
	 * @throws IOException
	 */
	private byte[] executeByPostWithFileListAndParamMap(String queryURL,
		List<File> fileList, Map<String, String> paramsMap) throws HttpClientException, IOException {
		// 创建get方法
        HttpPost httppost = new HttpPost(queryURL);
        
        // 设置header，不用设置，设置了反而服务器会报错，因为Content-type缺少了一个boundary参数
        httppost.addHeader("charset","UTF-8");
        
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Charset.forName(CHARACTER));
        
        // 设置kv参数
        if(paramsMap != null && paramsMap.size() > 0) {
        	Set<Entry<String, String>> entrySet = paramsMap.entrySet();
        	Iterator<Entry<String, String>> iterator = entrySet.iterator();
        	while(iterator.hasNext()) {
        		Entry<String, String> entry = iterator.next();
        		if(entry.getKey() != null) {  
        			builder.addTextBody(entry.getKey(), entry.getValue(),ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Charset.forName(CHARACTER)));
        		}
        	}
        }
        
        // 设置文件参数
        for(int i = 0 ; i < fileList.size() ; i++) {
        	builder.addBinaryBody("upfile"+String.valueOf(i), fileList.get(i), ContentType.DEFAULT_BINARY, fileList.get(i).getName());
        }
        
        // 设置参数
        HttpEntity entity = builder.build();
        httppost.setEntity(entity);
        
        // 提交请求
        CloseableHttpResponse closeableHttpResponse = httpclient.execute(httppost);
        
        // 返回字节流
        byte[] responseBody = null;
        try {
            responseBody = getBytesFromInpuStream(closeableHttpResponse.getEntity().getContent());
        } finally {
        	httppost.releaseConnection();
        	closeableHttpResponse.close();
        }
        return responseBody;
	}
}