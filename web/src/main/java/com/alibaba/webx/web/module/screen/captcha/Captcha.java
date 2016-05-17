package com.alibaba.webx.web.module.screen.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.web.module.pipeline.shiro.filter.BaseFilter;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 验证码类
 * 
 * 获取验证码例子：http://localhost:8080/topview/captcha/captcha.do，可令客户端图片的src直接等于该地址
 * 
 * 验证验证码例子：imageCaptchaService.validateResponseForID(request.getSession().getId(), "客户端传来的验证码")，返回 true则代表验证成功
 * 
 * @author xiaoMzjm
 */
public class Captcha {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@Autowired
	private LoggerUtils loggerUtils;

	private static Logger log = LoggerFactory.getLogger(Captcha.class);

	public void execute(TurbineRunData runData, Navigator nav, Context context) throws Exception {
		try {
			
			// 生成验证码
			BufferedImage challenge = imageCaptchaService.getImageChallengeForID(request.getSession().getId(), request.getLocale());
			
			// 格式转换
			ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
			ImageIO.write(challenge, "jpeg", jpegOutputStream);
			byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

			// 输出
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			BaseFilter.crossDomain(request, response);
			response.setDateHeader("Expires", 0L);
			response.setContentType("image/jpeg");
			ServletOutputStream respOs = response.getOutputStream();
			respOs.write(captchaChallengeAsJpeg);
			respOs.flush();
			respOs.close();
		} catch (IOException e) {
			log.error("generate captcha image error: {}", e.getMessage());
			loggerUtils.emailError(e);
		}
	}
	
	/**
	 * 验证验证码例子
	 */
	public boolean checkExample(String captcha){
		return imageCaptchaService.validateResponseForID(request.getSession().getId(), captcha);
	}
}