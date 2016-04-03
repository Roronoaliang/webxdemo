package com.alibaba.webx.searchengine.util.systemmonitor;

import java.net.InetAddress;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.searchengine.factory.mail.MailSenderUtil;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;

/**
 * 系统监控线程
 * 
 * 用来消费要发送的信息队列的
 * 
 * @author xiaoMzjm
 *
 */
public class SystemMonitorThread implements Runnable{
	
	// 邮件日志工具
	@Autowired
	private LoggerUtils loggerUtils;
	
	// 邮件发送对象
	private MailSenderUtil mailSender;
	
	// 收件人
	private List<String> acceptorList;
	
	// 标题
	private String title;
	
	// 日志
	private static Logger log = LoggerFactory.getLogger(SystemMonitorThread.class);

	public SystemMonitorThread(MailSenderUtil mailSender,List<String> acceptorList, String emailTitle) {
		this.mailSender = mailSender;
		this.acceptorList = acceptorList;
		this.title = emailTitle;
	}

	@Override
	public void run() {
		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder htmlContent = new StringBuilder();
			Queue<SystemMonitorBean> queue = new ConcurrentLinkedQueue<SystemMonitorBean>();
			synchronized(SystemMonitor.queue){
				queue.addAll(SystemMonitor.queue);
				SystemMonitor.queue.clear();
			}
			if(queue != null && queue.size() > 0) {
				while(queue.size() > 0){
					SystemMonitorBean systemMonitorBean = queue.poll();
			        sb.append(systemMonitorBean.getDate()).append("<br>").append(systemMonitorBean.getMessage()).append("<br><br><br>");
				}
			}
			if(sb.length() > 0) {
				htmlContent.append("<html><body>");
				htmlContent.append("【域名/IP地址】："+InetAddress.getLocalHost().getCanonicalHostName());
				htmlContent.append("<br><br>");
				htmlContent.append(sb);
				htmlContent.append("</body></html>");
				System.out.println("````````````````发送！title="+title+" , html="+htmlContent.toString());
				mailSender.sendText(acceptorList, title , htmlContent.toString());
			}
		} catch (Exception e) {
			log.error("RROR:",e);
			loggerUtils.emailError(e);
		}
		
	}

}
