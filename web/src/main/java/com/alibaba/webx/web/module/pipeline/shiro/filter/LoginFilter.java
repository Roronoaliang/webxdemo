package com.alibaba.webx.web.module.pipeline.shiro.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.searchengine.util.switchs.MySwitchUtil;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 登录过滤器
 * 
 * @author xiaoMzjm
 *
 */
public class LoginFilter extends PathMatchingFilter {
	
	private final static String loginUrl = "/login/login/login.do";
	
	private final static int ipLoginErrorNumLimit = 3;	// 记录IP登陆错误次数限制
	private static Map<String, Integer> ipLoginErrorNumMap = new ConcurrentHashMap<String, Integer>();
	
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@Autowired
	private MySwitchUtil mySwitch;
	
	/**
	 * 拦截的方法
	 */
	@Override
	protected boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		// 已登录
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return true;
		}
		
		// 未登录
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		// 是登录请求
		if (isLoginRequest(req)) {
			
			// 登录需要验证码
			if(isNeedCaptcha(req)) {
				
				// 没有传验证码
				if(request.getParameter("captcha") == null) {
					BaseFilter.ajax452json(req , resp , "下次登录需要带上验证码");
					return false;
				}
				
				// 验证验证码
				boolean vriflyCaptchaResult = verifyCaptcha(req);
				
				// 验证码错误
				if(!vriflyCaptchaResult) {
					addIpLoginErrorNum(BaseFilter.getIpAddr(req));
					BaseFilter.ajax451json(req, resp, "验证码错误");
					return false;
				}
			}
			
			// 尝试登录
			boolean loginSuccess = login(req); 
			
			// 登录成功
			if (loginSuccess) {
				clearIpLoginErrorNum(BaseFilter.getIpAddr(req));
				return true;
			}
			
			// 账号密码错误
			else {
				addIpLoginErrorNum(BaseFilter.getIpAddr(req));
				ajaxAccountOrPasswordError(req , resp);
//				BaseFilter.ajax403json(req , resp , "账号或密码错误");
				return false;
			}
		}
		// 未登录且不是登录请求
		else {
			BaseFilter.ajax450json(req , resp , "请先登录账号");
			return false;
		}
	}

	/**
	 * 账号或密码错误时的ajax返回
	 * @param req
	 * @param resp
	 */
	private void ajaxAccountOrPasswordError(HttpServletRequest request,
			HttpServletResponse response) {
		Integer ipLoginErrorNum = ipLoginErrorNumMap.get(BaseFilter.getIpAddr(request));
		if(ipLoginErrorNum != null && ipLoginErrorNum == ipLoginErrorNumLimit) {
			BaseFilter.ajax452json(request , response , "下次登录需要带上验证码");
		}
		else {
			BaseFilter.ajax403json(request , response , "账号或密码错误");
		}
	}

	/**
	 * 登录
	 * @param req
	 * @return
	 */
	private boolean login(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) return false;
		try {
			// 将会使用配置的realm进行验证
			SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证验证码是否正确
	 * 
	 * shiro和webx各自维护自己的session，shiro中的session在webx中获取不到，webx的session在shiro中也获取不到，
	 * 生成验证码所用的sessionId用的是webx的sessionId，如果在这里使用request.getSession().getId()是获取不到的
	 * 好在webx会把sessionId放在cookie里面，我们遍历cookies就能获取到sessionId
	 * 
	 * 在webx.xml中可以配置webx放在cookie里面的sessionId的名称，我配了WEBXJSESSIONID这么名称。
	 * 
	 * @param request
	 * @param captcha
	 * @return
	 */
	private boolean verifyCaptcha(HttpServletRequest request){
		String captcha = request.getParameter("captcha");
		if(StringUtils.isBlank(captcha)) return false;
		boolean result = false;
		try {
			String webxSessionId = "";
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				for(Cookie c : cookies) {
					if(c.getName().equals("WEBXJSESSIONID")) {
						webxSessionId = c.getValue();
					}
				}
			}
			result = imageCaptchaService.validateResponseForID(webxSessionId,captcha);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * 判断是否是登录请求
	 * @param req
	 * @return
	 */
	private boolean isLoginRequest(HttpServletRequest request) {
		return pathsMatch(loginUrl, WebUtils.getPathWithinApplication(request));
	}
	
	/**
	 * 增加某IP登陆错误次数
	 * @param ip
	 */
	private void addIpLoginErrorNum(String ip) {
		Integer oldNum = ipLoginErrorNumMap.get(ip);
		if(oldNum == null) {
			ipLoginErrorNumMap.put(ip, 1);
		}
		else {
			ipLoginErrorNumMap.put(ip, 1 + oldNum);
		}
	}
	
	/**
	 * 清空某IP的登录错误次数
	 * @param ip
	 */
	private void clearIpLoginErrorNum(String ip) {
		ipLoginErrorNumMap.remove(ip);
	}
	
	/**
	 * 是否需要验证码验证
	 * 
	 * 为了提高用户一眼，某个IP登陆次数大于某个值的时候，才需要用验证码登录，而不是每次登录都需要使用验证码
	 * @return
	 */
	private boolean isNeedCaptcha(HttpServletRequest request){
		if(!mySwitch.isLOGIN_CAPTCHA_SWITCH()) {
			return false;
		}
		Integer ipLoginErrorNum = ipLoginErrorNumMap.get(BaseFilter.getIpAddr(request));
		if(ipLoginErrorNum == null) {
			ipLoginErrorNum = 0; 
		}
		if(ipLoginErrorNum >= ipLoginErrorNumLimit) {
			return true;
		}
		return false;
	}
}