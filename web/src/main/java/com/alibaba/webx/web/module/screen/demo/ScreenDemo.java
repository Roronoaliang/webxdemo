package com.alibaba.webx.web.module.screen.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.common.po.demo.Demo;
import com.alibaba.webx.searchengine.factory.redis.RedisFactory;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.searchengine.util.switchs.MySwitchUtil;
import com.alibaba.webx.service.demo.ServiceDemo;
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
	private ServiceDemo serviceDemo;
	
	@Autowired
	private MySwitchUtil mySwitch;
	
	@Autowired
	private RedisFactory redisFactory;
	
	public void execute(TurbineRunData runData, Navigator nav, Context context) throws Exception {
		try {
			test();
			
			// 假如我要开发A功能，新该功能的所有代码的最前面加个开关，方便上线时一键开关这个功能
			if(mySwitch.isDEMO_SWITCH()) {
				Demo demo = new Demo();
				serviceDemo.add(demo);
				serviceDemo.delete(demo);
				serviceDemo.find(demo);
				serviceDemo.update(demo);
			}
			
		} catch (Exception e) {
			log.error("ERROR:", e);
			loggerUtils.emailError(e);
		}
		return;
	}
	
	/**
	 * 获取参数例子
	 */
	public void getParameterDemo(TurbineRunData runData){
		ParameterParser p = runData.getParameters();
		String userName = p.getString("userName");
		String password = p.getString("password");
		// 假如没有num参数，则赋予默认值10
		Integer num = p.getInt("num",10);
		System.out.println("userName="+userName+" , password="+password+" , num="+num);
	}
	
	/**
	 * 获取上传的文件例子
	 * 
	 * 上传文件的配置在webx.xml中
	 */
	public void uploadFileDemo(){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			FileItem[] items;
			String password = null;
			try {
				// 获取普通参数
				password = parser.getParameters().getString("password");
				System.out.println("password="+password);
				// 获取文件
				items = parser.getParameters().getFileItems("file");
				String fileName = null;
				for(FileItem item : items){
					fileName = item.getName();
					if (!item.isFormField() && fileName != null && !"".equals(fileName)) {
						File uploaderFile = new File("D:/"+fileName);
						item.write(uploaderFile);
					}
				}
			} catch (Exception e) {
				log.error("ERROR",e);
			}
		}
	}
	
	/**
	 * 下载文件例子，访问的时候，URL后缀得为.do
	 * 例如：http://localhost:8080/demo/screenDemo.do
	 */
	public void downloadDemo(){
		try {
			File file = new File("D:/test2.docx");
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        fis.close();
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("utf-8"),"utf-8"));
	        response.addHeader("Content-Length", "" + file.length());
	        response.setContentType("application/octet-stream");
	        OutputStream out = new BufferedOutputStream(response.getOutputStream());
	        out.write(buffer);
	        out.flush();
	        out.close();
		} catch (Exception e) {
			log.error("ERROR",e);
		}
	}
	
	/**
	 * 测试方法，随便写
	 */
	public void test (){
	}
}