package com.alibaba.webx.common.util.excel;

public class ExcelException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcelException() {
		super();
	}

	public ExcelException(String msg) {
		super("【Excel-Exception】" + msg);
	}

}
