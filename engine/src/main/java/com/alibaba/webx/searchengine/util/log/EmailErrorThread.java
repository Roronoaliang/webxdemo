package com.alibaba.webx.searchengine.util.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.searchengine.factory.mail.MailSender;

/**
 * 以邮件的形式发送错误报告线程类
 * 
 * 【设计该类的目的】：
 * 
 * 发送邮件耗时比较长，不能由于发送邮件而影响正常功能，所以要开一条线程出来执行发送邮件，所以诞生了该类
 * 
 * @author xiaoMzjm
 *
 */
public class EmailErrorThread implements Runnable{
	
	// 邮件发送对象
	private MailSender mailSender;
	
	// 收件人
	private List<String> acceptorList;
	
	// 标题
	private String title;
	
	// 日志
	private static Logger log = LoggerFactory.getLogger(EmailErrorThread.class);
	
	
	// 构造函数
	public EmailErrorThread(MailSender mailSender,List<String> acceptorList,String title) {
		this.mailSender = mailSender;
		this.acceptorList = acceptorList;
		this.title = title;
	}

	@Override
	public void run() {
		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder htmlContent = new StringBuilder();
			Queue<MyThrowable> queue = new ConcurrentLinkedQueue<MyThrowable>();
			synchronized(LoggerUtils.queue){
				queue.addAll(LoggerUtils.queue);
				LoggerUtils.queue.clear();
			}
			if(queue != null && queue.size() > 0) {
				while(queue.size() > 0){
					MyThrowable mt = queue.poll();
					Writer result = new StringWriter();
			        PrintWriter printWriter = new PrintWriter(result);
			        mt.getThrowable().printStackTrace(printWriter);
			        sb.append(mt.getData()).append("<br>").append(result.toString()).append("<br><br><br>");
				}
			}
			if(sb.length() > 0) {
				htmlContent.append("<html><body>");
				htmlContent.append(sb);
				htmlContent.append("</body></html>");
				mailSender.sendText(acceptorList, title , htmlContent.toString());
			}
		} catch (Exception e) {
			log.error("RROR:",e);
		}
	}
}