package com.alibaba.webx.searchengine.factory.mail;

public class MyMailException extends Exception{

	private static final long serialVersionUID = 1L;

	public MyMailException() {
    }

    public MyMailException(String s) {
        super(s);
    }

    public MyMailException(Exception e) {
        super(e);
    }

    public MyMailException(String s, Exception e) {
        super(s, e);
    }

}
