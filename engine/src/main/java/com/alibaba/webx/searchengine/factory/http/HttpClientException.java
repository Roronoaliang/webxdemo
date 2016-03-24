package com.alibaba.webx.searchengine.factory.http;

public class HttpClientException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -941511950026608822L;

	public HttpClientException() {
    }

    public HttpClientException(String s) {
        super(s);
    }

    public HttpClientException(Exception e) {
        super(e);
    }

    public HttpClientException(String s, Exception e) {
        super(s, e);
    }
}
