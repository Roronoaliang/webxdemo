package com.alibaba.webx.web.module.screen.demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.common.po.demo.Demo;
import com.alibaba.webx.common.util.uuid.UUIDUtil;
import com.alibaba.webx.searchengine.factory.redis.RedisFactory;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.searchengine.util.switchs.MySwitchUtil;
import com.alibaba.webx.service.demo.DemoService;
import com.alibaba.webx.web.module.screen.base.BaseScreen;

public class ScreenDemo extends BaseScreen {

	private static Logger log = LoggerFactory.getLogger(ScreenDemo.class);
	
	@Autowired
	private LoggerUtils loggerUtils;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;
	
    @Autowired
    ParserRequestContext parser;

	@Autowired
	private DemoService demoServiceImpl;
	
	@Autowired
	private MySwitchUtil mySwitch;
	
	@Autowired
	private RedisFactory redisFactory;
	
	/**
	 * 请求转发例子
	 * 
	 * @param nav
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/forwardTo.do
	 */
	public void doForwardTo(Navigator nav){
		nav.forwardTo("/captcha/captcha.do");
	}
	
	/**
	 * 外部重定向例子
	 * 
	 * @param nav
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/redirectToLocation.do
	 */
	public void doRedirectToLocation(Navigator nav){
		nav.redirectToLocation("http://localhost:8080/topview/demo/screenDemo/ajaxJsonDemo.do");
	}
	
	/**
	 * 调用service层例子
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/serviceDemo.do
	 */
	public void doServiceDemo() {
		try {
			// 假如我要开发A功能，新该功能的所有代码的最前面加个开关，方便上线时一键开关这个功能
			if(mySwitch.isDEMO_SWITCH()) {
				System.out.println();
				System.out.println("service例子:");
				// 增
				Demo demo = new Demo();
				demo.setId(UUIDUtil.getUUID());
				demo.setName("?);drop table test2;");
				int insertResult = demoServiceImpl.insert(demo);
				System.out.println("增结果："+insertResult);
				// 删
				int deleteResult = demoServiceImpl.deleteById("940335b80a9b4e4588c46e7d5fd3a66d");
				System.out.println("删结果："+deleteResult);
				// 查
				List<Demo> demoList = demoServiceImpl.selectAll(0, 3);
				if(demoList != null) {
					System.out.println("selectAll：");
					for(Demo d : demoList) {
						System.out.println(d);
					}
				}
				// 查——根据单属性
				List<Demo> demoList2 = demoServiceImpl.selectByOneParameter("name", "zjm", 0, 3);
				if(demoList2 != null) {
					System.out.println("selectByOneParameter：");
					for(Demo d : demoList2) {
						System.out.println(d);
					}
				}
				// 查——根据多属性
				Map<String,Object> parametersMap = new HashMap<String,Object>();
				parametersMap.put("id", "111");
				parametersMap.put("name", "zjm");
				List<Demo> demoList3 = demoServiceImpl.selectByParameters(parametersMap, 0, 3);
				if(demoList3 != null) {
					System.out.println("selectByParameters：");
					for(Demo d : demoList3) {
						System.out.println(d);
					}
				}
				// 查——根据ID
				Demo demo2 = demoServiceImpl.selectById("111");
				if(demo2 != null) {
					System.out.println("selectById：");
					System.out.println(demo2);
				}
				// 改
				Demo demo3 = new Demo();
				demo3.setId("20160416");
				demo3.setName("zjmzjm");
				int updateResult = demoServiceImpl.updateById(demo3);
				System.out.println("修改结果："+updateResult);
				
				// 事物
//				Demo demo2 = new Demo();
//				demo2.setId("1555");
//				serviceDemo.testTransactional(demo2);
//				serviceDemo.delete("id");
//				serviceDemo.find(demo);
				Map<String,String> map = new HashMap<String,String>();
				map.put("oldId" , "123");
				map.put("newId" , "798");
//							serviceDemo.update(map);
				
			}
		} catch (Exception e) {
			log.error("ERROR:", e);
			loggerUtils.emailError(e);
		}
		return;
	}
	
	/**
	 * 获取参数例子
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/getParameterDemo.do?userName=zjm&password=123
	 */
	public void doGetParameterDemo(TurbineRunData runData){
		System.out.println();
		System.out.println("获取普通参数例子：");
		ParameterParser p = runData.getParameters();
		String userName = p.getString("userName","blank");
		String password = p.getString("password","blank");
		Integer num = p.getInt("num",10);
		System.out.println("getParameterDemo userName="+userName+" , password="+password+" , num="+num);
	}
	
	
	/**
	 * 获取Cookie例子
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/getCookieDemo.do
	 */
	public void doGetCookieDemo(){
		System.out.println();
		System.out.println("获取cookie例子：");
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				System.out.println("key = " + cookie.getName());
				System.out.println("value = " + cookie.getValue());
			}
		}
	}
	
	/**
	 * 获取Header例子
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/getHeaderDemo.do
	 */
	public void doGetHeaderDemo(){
		System.out.println();
		System.out.println("获取header例子：");
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			System.out.println("headerName="+headerName + " , headerValue="+headerValue);
		}
	}
	
	/**
	 * 获取session中的东西例子
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/getSessionDemo.do
	 */
	public void doGetSessionDemo(){
		System.out.println();
		System.out.println("session测试：");
		Demo demo = (Demo) request.getSession().getAttribute("demo");
		if(demo == null) {
			System.out.println("demo is null");
			Demo d = new Demo("666","zjm");
			request.getSession().setAttribute("demo", d);
		}
		else {
			System.out.println(demo);
		}
	}
	
	/**
	 * 获取上传的文件例子
	 * 
	 * 上传文件的配置在webx.xml中
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/uploadFileDemo.do
	 */
	public void doUploadFileDemo(){
		System.out.println();
		System.out.println("文件上传例子：");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			FileItem[] items;
			String password = null;
			try {
				// 获取普通参数
				password = parser.getParameters().getString("password","blank");
				System.out.println("password = " + password);
				// 获取文件
				items = parser.getParameters().getFileItems("file");
				if(items != null) {
					String fileName = null;
					for(FileItem item : items){
						fileName = item.getName();
						if (!item.isFormField() && StringUtils.isNotBlank(fileName)) {
							System.out.println("fileName = " + fileName);
							File uploaderFile = new File("D:/test/"+fileName);
							item.write(uploaderFile);
						}
					}
				}
			} catch (Exception e) {
				log.error("ERROR",e);
			}
		}
	}
	
	/**
	 * 下载文件例子
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/downloadDemo.do
	 */
	public void doDownloadDemo(){
		try {
			File file = new File("D:/test2.docx");
			ajaxdownLoad("test2.docx", new BufferedInputStream(new FileInputStream(file)));
		} catch (Exception e) {
			log.error("ERROR",e);
		}
	}
	
	/**
	 * 返回json数据例子
	 * 
	 * 访问地址：http://localhost:8080/topview/demo/screenDemo/ajaxJsonDemo.do
	 */
	public void doAjaxJsonDemo(){
		Demo demo = new Demo("uuid", "zjm");
		ajax200json(demo);
	}
	
}