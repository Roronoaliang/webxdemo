package com.alibaba.webx.searchengine.factory.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.alibaba.webx.common.factory.log.LoggerFactory;

/**
 * 邮件工厂类
 * 
 * 使用例子请见【UseCase.java】
 * 
 * @author xiaoMzjm
 */
public class MailFactory {
	
	private static Logger log = LoggerFactory.getLogger(MailFactory.class);

	// 默认属性
    private static String defaultMailServerHost;    // 默认的发送邮件的服务器的IP
    private static String defaultMailServerPort;   	// 默认的发送邮件的服务器的端口    
    private static String defaultSenderAddress;    	// 默认的邮件发送者的地址    
    private static String defaultPassword;    		// 默认的登陆邮件发送服务器的密码     
    private static Properties defaultProperties; 
    
    // 非默认属性
    private Message mailMessage;		// 消息体
    private String mailServerHost;    	// 发送邮件的服务器的IP
    private String mailServerPort;   	// 发送邮件的服务器的端口    
    private String SenderAddress;    		// 邮件发送者的地址    
    private String password;    		// 登陆邮件发送服务器的密码     
    
    /**
     * 空构造方法，采用默认配置
     */
    public MailFactory() {}
    
    /**
     * 带参构造方法，采用用户自定义配置
     * @throws MyMailException 
     */
    public MailFactory(String mailServerHost,String mailServerPort,String SenderAddress,String password) throws MyMailException{
    	if(StringUtils.isBlank(mailServerHost) || StringUtils.isBlank(mailServerPort) || StringUtils.isBlank(SenderAddress) || StringUtils.isBlank(password)) {
    		throw new MyMailException("参数错误！");
    	}
    	this.mailServerHost = mailServerHost;
    	this.mailServerPort = mailServerPort;
    	this.SenderAddress = SenderAddress;
    	this.password = password;
    	Properties properties = new Properties();   
    	properties.put("mail.smtp.host", mailServerHost);    
    	properties.put("mail.smtp.port", mailServerPort);    
    	properties.put("mail.smtp.auth", "true");  
    	MyAuthenticator authenticator = new MyAuthenticator(this.SenderAddress,this.password);
    	Session sendMailSession = Session.getDefaultInstance(properties, authenticator);
    	mailMessage = new MimeMessage(sendMailSession);
		try {
			Address from = new InternetAddress(this.SenderAddress);
			mailMessage.setFrom(from);
			mailMessage.setSentDate(new Date());
		} catch (Exception e) {
			log.error("ERROR:",e);
		}
    }
    
    // 初始化方法，由spring调用
    public void init() {
    	defaultProperties = new Properties();   
    	defaultProperties.put("mail.smtp.host", defaultMailServerHost);    
    	defaultProperties.put("mail.smtp.port", defaultMailServerPort);    
    	defaultProperties.put("mail.smtp.auth", "true");  
    }
    
    /**
     * 获取默认的邮件发送者
     * @return
     * @throws MyMailException 
     * @throws MessagingException 
     */
    public MailSenderUtil getDefaultMailSender() throws MyMailException, MessagingException {
    	if(StringUtils.isBlank(defaultSenderAddress) || StringUtils.isBlank(defaultPassword) || StringUtils.isBlank(defaultPassword) || StringUtils.isBlank(defaultPassword)) {
    		throw new MyMailException("参数错误！");
    	}
    	MyAuthenticator authenticator = new MyAuthenticator(defaultSenderAddress,defaultPassword);
    	Session defaultsendMailSession = Session.getDefaultInstance(defaultProperties, authenticator);
    	Message defaultMailMessage = new MimeMessage(defaultsendMailSession);
		Address from = new InternetAddress(defaultSenderAddress);
		defaultMailMessage.setFrom(from);
		defaultMailMessage.setSentDate(new Date());
    	return new MailSenderUtil(defaultMailMessage);
    }
    

    
    /**
     * 获取自定义的邮件发送者
     * @return
     * @throws Exception 
     */
    public MailSenderUtil getMailSender() throws Exception {
    	if(mailMessage == null) {
    		throw new MyMailException("请先使用带参构造函数创建MailFactory！");
    	}
    	return new MailSenderUtil(mailMessage);
    }
    

	/**
     * 权限验证内部类
     * @author xiaoMzjm
     */
    private static class MyAuthenticator extends Authenticator{
    	
    	String userName = null;   
        String password = null;   
            
        public MyAuthenticator(String username, String password) {    
            this.userName = username;    
            this.password = password;    
        }    
        protected PasswordAuthentication getPasswordAuthentication(){   
            return new PasswordAuthentication(userName, password);   
        }   
    }
    
    
    // get & set
    public String getMailServerHost() {
		return mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}


	public String getPassword() {
		return password;
	}

	public String getDefaultMailServerHost() {
		return defaultMailServerHost;
	}


	public String getDefaultMailServerPort() {
		return defaultMailServerPort;
	}

	public static void setDefaultMailServerHost(String defaultMailServerHost) {
		MailFactory.defaultMailServerHost = defaultMailServerHost;
	}

	public static void setDefaultMailServerPort(String defaultMailServerPort) {
		MailFactory.defaultMailServerPort = defaultMailServerPort;
	}

	public static String getDefaultSenderAddress() {
		return defaultSenderAddress;
	}

	public static void setDefaultSenderAddress(String defaultSenderAddress) {
		MailFactory.defaultSenderAddress = defaultSenderAddress;
	}

	public static String getDefaultPassword() {
		return defaultPassword;
	}

	public static void setDefaultPassword(String defaultPassword) {
		MailFactory.defaultPassword = defaultPassword;
	}

	public static Properties getDefaultProperties() {
		return defaultProperties;
	}

	public static void setDefaultProperties(Properties defaultProperties) {
		MailFactory.defaultProperties = defaultProperties;
	}
}
