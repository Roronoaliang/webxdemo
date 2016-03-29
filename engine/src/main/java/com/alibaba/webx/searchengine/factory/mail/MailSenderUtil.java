package com.alibaba.webx.searchengine.factory.mail;

import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

/**
 * 右键发送实现类
 * 
 * @author xiaoMzjm
 *
 */
public class MailSenderUtil {
	
	private Message mailMessage;
	
	public MailSenderUtil(Message mailMessage) {
		this.mailMessage = mailMessage;
	}
	
	
	/**
	 * 群发文本邮件
	 * @param acceptorList
	 * @param title
	 * @param content
	 * @throws MessagingException
	 * @throws MyMailException
	 */
	public void sendText(List<String> acceptorList , String title , String content) throws MessagingException, MyMailException{
		sendFile(acceptorList,title,content,null);
	}
	
	
	/**
	 * 群发文本+文件邮件
	 * @param acceptorList
	 * @param title
	 * @param content
	 * @param fileUrlList
	 * @return
	 * @throws MessagingException
	 * @throws MyMailException
	 */
	public void sendFile(List<String> acceptorList,String title,String content,List<String> filePathList) throws MessagingException, MyMailException {
		if(acceptorList == null || (acceptorList != null && acceptorList.isEmpty()) || StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
			throw new MyMailException("参数错误！");
		}
		// 设置收件人
		int toAddressesSize = acceptorList.size();
		InternetAddress[] address = new InternetAddress[toAddressesSize];
		for (int i = 0; i < toAddressesSize ; i++) {  
            address[i] = new InternetAddress(acceptorList.get(i));  
        }
		mailMessage.setRecipients(Message.RecipientType.TO, address);
		mailMessage.setSubject(title);
		
		// 设置发送的文本
		Multipart multipart = new MimeMultipart();
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(content.toString(), "text/html; charset=utf-8");
		multipart.addBodyPart(bodyPart); 
		
		// 设置发送的文件
		if(filePathList != null && !filePathList.isEmpty()) {
			for(String fileUrl : filePathList) {
				BodyPart bp = new MimeBodyPart();  
	            FileDataSource fds = new FileDataSource(fileUrl); 	// 得到数据源  
	            bp.setDataHandler(new DataHandler(fds)); 			// 得到附件本身并至入BodyPart  
	            bp.setFileName(fds.getName());  					// 得到文件名同样至入BodyPart  
	            multipart.addBodyPart(bp); 
			}
		}
		
		// 发送
		mailMessage.setContent(multipart);
		Transport.send(mailMessage);
	}
}
