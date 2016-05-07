package com.alibaba.webx.test.web.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class UseCase{

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws InterruptedException{
		try {
			// 获取SecurityManager工厂
//			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro.ini");
//			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-realm.ini");
//			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-authenticator-all-success.ini");
//			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-authenticator-at-least-success.ini");
//			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-role.ini");
//			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-permission.ini");
			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-authorizer.ini");
			
			// 获取SecurityManager实例
			SecurityManager securityManager = factory.getInstance();
			SecurityUtils.setSecurityManager(securityManager);
			
			// 得到subject
			Subject subject = SecurityUtils.getSubject();
			
			UsernamePasswordToken token = new UsernamePasswordToken("zhang","123456");
			
			// 登录
			try {
				System.out.println("````将要执行login（）");
				subject.login(token);
				System.out.println("登录成功");
				
				// 用户信息
				PrincipalCollection principalCollection = subject.getPrincipals();
				if(principalCollection != null) {
					List<String> list = principalCollection.asList();
					for(String s : list) {
						System.out.println(s);
					}
				}else {
					System.out.println("no principalCollection");
				}
				
				// 角色
				System.out.println("````将要执行hasRole（）");
				boolean role1 = subject.hasRole("role1");
				boolean role2 = subject.hasRole("role2");
				System.out.println("是否有role1角色："+role1);
				System.out.println("是否有role2角色："+role2);
//				subject.checkRole("role2");// 会抛出异常
				
				// 权限
				System.out.println("````将要执行isPermitted（）");
				boolean permission = subject.isPermitted("user:create");
				System.out.println("是否有user:create权限："+permission);
				boolean permission2 = subject.isPermitted("user1:create");
				System.out.println("是否有user1:create权限："+permission2);
//				subject.checkPermission("user:create");// 会抛出异常
				
				// 权限2
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("验证失败");
			}
			
			// 登出
			subject.logout();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * 测试加密算法
	 */
	@Test
	public void test2(){
		System.out.println(new Md5Hash("123", "333296f683024023a8516abfc29f13e2", 2).toString());
		System.out.println(new SimpleHash("md5","pass","salt",2).toString());
		System.out.println(new Sha256Hash("123","5e4ff9fa84c6428ab55778c371f39f2d",2).toString());
		System.out.println(new SimpleHash("sha-256","pass","salt",2).toString());
		System.out.println(new Sha512Hash("pass","salt",2).toString());
		System.out.println(new SimpleHash("sha-512","pass","salt",2).toString());
	}
	
	/**
	 * 测试HashService和passwordService
	 */
	@Test
	public void test3(){
		// 加密：
		DefaultPasswordService passwordService = new DefaultPasswordService();
		DefaultHashService hashService = new DefaultHashService();
		Shiro1CryptFormat hashFormat = new Shiro1CryptFormat();
		DefaultHashFormatFactory hashFormatFactory = new DefaultHashFormatFactory();
		passwordService.setHashService(hashService);
		passwordService.setHashFormat(hashFormat);
		passwordService.setHashFormatFactory(hashFormatFactory);
		
		PasswordMatcher passwordMatcher = new PasswordMatcher();
		passwordMatcher.setPasswordService(passwordService);
		
		ByteSource password = ByteSource.Util.bytes("topview624");
		
//		SecureRandomNumberGenerator random = new SecureRandomNumberGenerator();
//		ByteSource salt = random.nextBytes();
//		String saltHex = salt.toHex();
//		System.out.println("saltHex = "+saltHex);
//		HashRequest request = new HashRequest
//				.Builder()
//				.setSource(password)
//				.setSalt(salt)
//				.build();
		
//		String newPassword = hashService.computeHash(request).toHex();	
		String newPassword = passwordService.encryptPassword(password);
		System.out.println("newPassword = " + newPassword);
		
	}
}
