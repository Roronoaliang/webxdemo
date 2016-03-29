package com.alibaba.webx.searchengine.factory.mail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 邮件组件使用例子
 * 
 * @author xiaoMzjm
 */
public class UseCase {
	
	/**
	 * 使用默认配置发送文本
	 * 默认配置的参数写在xml中，得由spring加载，所以这个例子其实跑不起来
	 */
	@Test
	public void sendText(){
		try {
			MailFactory mailFactory = new MailFactory();
			MailSenderUtil mailSender = mailFactory.getDefaultMailSender();
			List<String> acceptorList = new ArrayList<String>();
			acceptorList.add("topviewacceptor@163.com");
			mailSender.sendText(acceptorList, "标题", "内容");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用默认配置发送文件
	 * 默认配置的参数写在xml中，得由spring加载，所以这个例子其实跑不起来
	 */
	@Test
	public void sendFile(){
		try {
			MailFactory mailFactory = new MailFactory();
			MailSenderUtil mailSender = mailFactory.getDefaultMailSender();
			List<String> acceptorList = new ArrayList<String>();
			acceptorList.add("topviewacceptor@163.com");
			List<String> filePathList = new ArrayList<String>();
			filePathList.add("D:/test2.docx");
			mailSender.sendFile(acceptorList, "标题", "内容",filePathList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用非默认配置发送文本
	 */
	@Test
	public void sendText2(){
		try {
			MailFactory mailFactory = new MailFactory("smtp.163.com","25","topviewsender@163.com","topview624");
			MailSenderUtil mailSender = mailFactory.getMailSender();
			List<String> acceptorList = new ArrayList<String>();
			acceptorList.add("topviewacceptor@163.com");
			mailSender.sendText(acceptorList, "标题", "内容1<br>内容2</br>内容3\n内容4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用非默认配置发送文件
	 */
	@Test
	public void sendFile2(){
		try {
			MailFactory mailFactory = new MailFactory("smtp.163.com","25","topviewsender@163.com","topview624");
			MailSenderUtil mailSender = mailFactory.getMailSender();
			List<String> acceptorList = new ArrayList<String>();
			acceptorList.add("heijueerror@163.com");
			List<String> filePathList = new ArrayList<String>();
			filePathList.add("D:/test2.docx");
			mailSender.sendFile(acceptorList, "标题", "内容",filePathList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
