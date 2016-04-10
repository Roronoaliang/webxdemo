package com.alibaba.webx.test.engine.mail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.webx.searchengine.factory.mail.MailFactory;
import com.alibaba.webx.searchengine.factory.mail.MailSenderUtil;
import com.alibaba.webx.test.engine.base.EngineBaseTest;

/**
 * 【邮件组件使用例子】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase extends EngineBaseTest<UseCase,MailFactory>{
	
	@Before
	public void before(){
		initTarget("mailFactory");
	}
	

	/**
	 * 使用默认配置发送文本————测试通过
	 */
	@Test
	public void sendText(){
		try {
			MailSenderUtil mailSender = target.getDefaultMailSender();
			List<String> acceptorList = new ArrayList<String>();
			acceptorList.add("xxx@163.com");
			mailSender.sendText(acceptorList, "作业", "数学 语文 英文");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用默认配置发送文件————测试通过
	 */
	@Test
	public void sendFile(){
		try {
			MailSenderUtil mailSender = target.getDefaultMailSender();
			List<String> acceptorList = new ArrayList<String>();
			acceptorList.add("xxx@163.com");
			List<String> filePathList = new ArrayList<String>();
			filePathList.add("D:/test2.docx");
			mailSender.sendFile(acceptorList, "标题", "内容",filePathList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用非默认配置发送文本————测试通过
	 */
	@Test
	public void sendText2(){
		try {
			MailFactory mailFactory = new MailFactory("smtp.163.com","25","xxx@163.com","密码");
			MailSenderUtil mailSender = mailFactory.getMailSender();
			List<String> acceptorList = new ArrayList<String>();
			acceptorList.add("xxx@163.com");
			mailSender.sendText(acceptorList, "标题", "内容1<br>内容2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用非默认配置发送文件————测试通过
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
