package com.alibaba.webx.common.util.date;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.alibaba.webx.common.util.date.MySimpleDateFormat.MySimpleDateFormatException;

/**
 * 【日期组件 使用用例】
 * 
 * @author xiaoMzjm
 */
public class UseCase {

	/**
	 * 以下用例都是返回默认格式，即【yyyy-MM-dd HH:mm:ss】
	 * 
	 * 每个方法都有提供传入自定义格式的重载版本。
	 * 
	 */
	@Test
	public void test() throws MySimpleDateFormatException, ParseException{
		
		// 获取距离今天“前后N天”的日期
		System.out.println("明天日期：" + MySimpleDateFormat.getStringDate(1));
		System.out.println("昨天日期：" + MySimpleDateFormat.getStringDate(-1));
		
		// 今天日期
		System.out.println("今天日期：" + MySimpleDateFormat.getNowStringDate());
		
		// 把String类型的日期转化成Date类型
		System.out.println("String-->Date：" + MySimpleDateFormat.changeStringToDate("2016-03-18 16:54:42", MySimpleDateFormat.yyyyMMdd_HHmmss));
		System.out.println("String-->Date：" + MySimpleDateFormat.changeStringToDate("2016-03-18 16:54:42", MySimpleDateFormat.yyyyMMdd_HHmmss));
		
		// 把Date类型的日期转化成String类型
		System.out.println("Date-->String：" + MySimpleDateFormat.changeDateToString(new Date(), MySimpleDateFormat.yyyyMMdd_HHmmss));
		
		// 今日日期时间戳
		System.out.println("今天时间戳：" + MySimpleDateFormat.getNowTime());
		
		// 把时间戳类型转成Date类型
		System.out.println("time-->Date："+ MySimpleDateFormat.changeTimeToDate(MySimpleDateFormat.getNowTime()));
		
		// 把Date类型转化成时间戳
		System.out.println("Date-->time：" + MySimpleDateFormat.changeDateToTime(new Date()));
	}
}
