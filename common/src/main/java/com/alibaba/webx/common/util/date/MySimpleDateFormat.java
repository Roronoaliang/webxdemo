package com.alibaba.webx.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期生成类
 * 
 * @author xiaoMzjm
 */
public class MySimpleDateFormat {

	// 日期格式
	public final static String yyyyMMdd = "yyyy-MM-dd";
	public final static String HHmmss = "HH:mm:ss";
	public final static String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 返回距离当时”前后几天“的日期的String格式，通过默认格式
	 * 
	 * @param distanceFromNow				前后N天，正数表示以后，负数表示以前
	 * @return
	 * @throws MySimpleDateFormatException
	 */
	public static String getStringDate(int distanceFromNow)
			throws MySimpleDateFormatException {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, distanceFromNow);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMdd_HHmmss);
		return sdf.format(date);
	}


	/**
	 * 返回距离当时”前后几天“的日期的String格式，通过自定义格式
	 * 
	 * @param distanceFromNow				前后N天，正数表示以后，负数表示以前
	 * @return
	 * @throws MySimpleDateFormatException
	 */
	public static String getStringDate(int distanceFromNow,String format) throws MySimpleDateFormatException {
		// 检查参数
		checkFormatParameter(format);

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, distanceFromNow);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 返回当前日期的String形式，通过默认的格式
	 * 
	 * @return 当前日期的String形式
	 * @throws MySimpleDateFormatException
	 */
	public static String getNowStringDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMdd_HHmmss);
		return sdf.format(new Date());
	}

	/**
	 * 返回当前日期的String形式，通过常用的格式
	 * 
	 * @param format
	 *            常用格式
	 * @return 当前日期的String格式
	 * @throws MySimpleDateFormatException
	 */
	public static String getNowStringDate(String format)
			throws MySimpleDateFormatException {
		// 检查参数
		checkFormatParameter(format);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * 将String字符串转换成 Date，通过常用格式
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param format
	 *            常用格式
	 * @return
	 * @throws MySimpleDateFormatException
	 * @throws ParseException
	 */
	public static Date changeStringToDate(String str, String format) throws MySimpleDateFormatException, ParseException {
		// 检查参数
		checkFormatParameter(format);
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	/**
	 * 将Date数据转化成String类型
	 * @param date
	 * @param format
	 * @return
	 * @throws MySimpleDateFormatException
	 */
	public static String changeDateToString(Date date , String format) throws MySimpleDateFormatException{
		// 检查参数
		checkFormatParameter(format);
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取当前的时间的时间戳
	 * 
	 * @return 当前的时间的时间戳
	 */
	public static long getNowTime() {
		Date d = new Date();
		return d.getTime();
	}

	/**
	 * 把时间戳转化为Date，通过默认格式
	 * 
	 * @param time
	 * @return
	 * @throws MySimpleDateFormatException
	 * @throws ParseException
	 */
	public static Date changeTimeToDate(long time)
			throws MySimpleDateFormatException, ParseException {
		if (time < 0) {
			throw new MySimpleDateFormatException("time 应该大于 0");
		}

		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMdd_HHmmss);
		String d = sdf.format(time);
		return sdf.parse(d);
	}

	/**
	 * 把时间戳转化为Date，通过常用格式
	 * 
	 * @param time
	 *            时间戳
	 * @param format
	 *            常用格式
	 * @return 转化后的Date
	 * @throws MySimpleDateFormatException
	 * @throws ParseException
	 */
	public static Date changeTimeToDate(long time, String format)
			throws MySimpleDateFormatException, ParseException {
		if (time < 0) {
			throw new MySimpleDateFormatException("time 应该大于 0");
		}

		// 检查参数
		checkFormatParameter(format);

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String d = sdf.format(time);
		return sdf.parse(d);
	}
	
	
	/**
	 * 把Date类型的日期转化成时间戳格式
	 * @param date
	 * @return
	 */
	public static long changeDateToTime(Date date){
		return date.getTime();
	}


	/**
	 * 检查日期格式参数异常
	 * 
	 * @param format
	 * @throws MySimpleDateFormatException
	 */
	private static void checkFormatParameter(String format)
			throws MySimpleDateFormatException {
		// 格式为空的话，抛出异常
		if (StringUtils.isBlank(format)) {
			throw new MySimpleDateFormatException("format 不能为  null");
		}
	}

	/**
	 * 异常类
	 * 
	 * @author xiaoMzjm
	 */
	public static class MySimpleDateFormatException extends Exception {
		private static final long serialVersionUID = 1L;

		// 定义有参数的构造方法
		public MySimpleDateFormatException(String msg) {
			super("【日期生成类异常】：" + msg);
		}
	}
}
