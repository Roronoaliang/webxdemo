package com.alibaba.webx.searchengine.util.log;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.common.util.date.MyDateUtil;
import com.alibaba.webx.searchengine.factory.mail.MailFactory;
import com.alibaba.webx.searchengine.factory.mail.MailSenderUtil;
import com.alibaba.webx.searchengine.util.switchs.MySwitchUtil;

/**
 * 日志工具类
 * 
 * 【设计该类的目的】：
 * 
 * 有一些日志比较重要，比如error日志，调用error函数的话，一个数报错等重大错误。
 * 这些错误可以通过其他工具去分析赋值文件得出，但麻烦，如果能在error的同时，发送报错邮件到作者，那么将省去这个麻烦。
 * 所以该日志工具类封装了这一类的功能。
 * @author xiaoMzjm
 *
 */
public class LoggerUtils {
	
	@Autowired
	private MailFactory mailFactory;
	
	@Autowired
	private MySwitchUtil mySwitchUtil;
	
	// 日志
	private static Logger log = LoggerFactory.getLogger(LoggerUtils.class);
	
	// 邮件发送对象
	private static MailSenderUtil mailSender;
	
	// 接收者
	private List<String> acceptorList;
	
	// 标题
	private String emailTitle;
	
	// 线程池最大线程数量，用来控制并发数
	private int threadNum;
	
	// 发送错误邮件的频率，单位秒
	private int sendEmailRate;
	
	// 线程池
	private static ScheduledThreadPoolExecutor stpe;
	
	// 错误队列
	public static Queue<MyThrowable> queue = new ConcurrentLinkedQueue<MyThrowable>();
	
	// 构造函数
	public LoggerUtils(){}
	
	// 初始化函数
	public void init(){
		try {
			stpe = new ScheduledThreadPoolExecutor(threadNum);
			mailSender = mailFactory.getDefaultMailSender();
			EmailErrorThread emailErrorThread = new EmailErrorThread(mailSender,acceptorList,emailTitle);
			stpe.scheduleAtFixedRate(emailErrorThread , 10, sendEmailRate , TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("ERROR:",e);
		}
	}
	
	/**
	 * 打error日志并发送邮件。
	 * @throws Exception 
	 */
	public void emailError(Throwable e){
		if(mySwitchUtil.isEMAIL_LOG_SWITCH()){
			MyThrowable mt = new MyThrowable(MyDateUtil.getNowStringDate(),e);
			queue.add(mt);
		}
	}

	// get & set
	public List<String> getAcceptorList() {
		return acceptorList;
	}

	public void setAcceptorList(List<String> acceptorList) {
		this.acceptorList = acceptorList;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public int getSendEmailRate() {
		return sendEmailRate;
	}

	public void setSendEmailRate(int sendEmailRate) {
		this.sendEmailRate = sendEmailRate;
	}
}