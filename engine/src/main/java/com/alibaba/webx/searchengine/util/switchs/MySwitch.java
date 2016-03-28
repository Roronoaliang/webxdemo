package com.alibaba.webx.searchengine.util.switchs;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.webx.searchengine.factory.redis.DefaultRedisHandler;
import com.alibaba.webx.searchengine.factory.redis.RedisFactory;

/**
 * 【功能总开关类】
 * 
 * 使用开关工具，在编写一些新功能时，可以在代码的最前方加入开关，以便一键开启/关闭新功能。
 * 
 * 假如一个功能上线后出了问题，导致其他服务受到影响，如果关闭服务器修复，肯定会影响到用户的正常使用，
 * 
 * 不修复的话用户又访问不了，有了开关工具后，此时我们可以在`redis数据库`中把开关关闭，
 * 
 * 即可停掉该功能，这样的话可以保证其他服务正常运行。开关的的获取会从`本地`和`默认redis数据库`中获取，
 * 
 * 当从redis获取失败时，自动从本地获取，方便没配redis的用户使用。 
 * 
 * 但没配redis的用户将使用不了从数据库控制开关的功能。
 * 
 * @author xiaoMzjm
 */
public class MySwitch {
	
	// 日志
	private static Logger log = LoggerFactory.getLogger(MySwitch.class);
	
	// redis处理类
	public static DefaultRedisHandler defaultRedisHandler ;
	
	// 邮件日志功能开关
	private static boolean EMAIL_LOG_SWITCH;
	
	public MySwitch(){}

	/**
	 * 获取邮件日志功能开关值
	 * @return
	 */
	public boolean isEMAIL_LOG_SWITCH() {
		EMAIL_LOG_SWITCH = getSwtichByKey("EMAIL_LOG_SWITCH",EMAIL_LOG_SWITCH);
		return EMAIL_LOG_SWITCH;
	}

	public void setEMAIL_LOG_SWITCH(boolean eMAIL_LOG_SWITCH) {
		EMAIL_LOG_SWITCH = eMAIL_LOG_SWITCH;
	}
	
	/**
	 * 获取redis组件
	 */
	public void init(){
		try {
			defaultRedisHandler =  RedisFactory.getDefaultRedisHandler();
		} catch (Exception e) {
			log.warn("初始化redis失败，采用本地配置。");
		}
	}
	
	/**
	 * 根据key获取开关值
	 * 
	 * 如果redis处理对象为空，返回默认值
	 * 
	 * 如果redis处理对象不为空，尝试从redis获取
	 * 
	 * 如果redis返回null，则返回默认值defaultResult，该默认值配置在biz-engine.xml中。
	 * 
	 * @param key
	 * @param defaultResult
	 * @return
	 */
	private Boolean getSwtichByKey(String key , Boolean defaultResult){
		if(defaultRedisHandler == null) {
			return defaultResult;
		}
		else {
			// null | true | false
			Boolean result = getFromRedis("EMAIL_LOG_SWITCH");
			if(result == null){
				return defaultResult;
			}
			else {
				return result;
			}
		}
	}
	
	/**
	 * 根据key，从redis数据库获取开关值
	 * 
	 * 返回的值可能是 null | true | false
	 * 
	 * 若redis返回null，则返回null，并打下一个error日志提醒开发人员处理该错误。
	 * 
	 * @param key
	 * @return
	 */
	private Boolean getFromRedis(String key){
		Boolean result = null;
		try {
			String strReuslt = defaultRedisHandler.get(key);
			if(StringUtils.isBlank(strReuslt)) {
				throw new NullPointerException("ERROR:redis数据库中没有 "+key+" 这个key！");
			}
			else {
				result = Boolean.valueOf(strReuslt);
			}
		} catch (Exception e) {
			log.error("ERROR",e);
		}
		return result;
	}
}