package com.alibaba.webx.common.util.switchs;

/**
 * 功能总开关类
 * 
 * 
 * 【本类用途】：
 * 
 * 用来开关某些功能的，比如新开发了一个邮件发送功能，但这个功能不太稳定，我们可以在该功能代码最前面加上
 * if(MySwitch.email) {...} ，再在if中编写接下来的邮件功能，假如某天邮件功能出现重大异常，导致整个服务器快挂了，
 * 我们又没能立刻找出问题修复之，又不能把代码删掉，因为删除后要重写太麻烦了，这时我们可以把线上的开关设置成false，
 * 这样就暂时不会再执行邮件服务相关的代码了，然后线下我们再慢慢调。
 * 
 * @author xiaoMzjm
 */
public class MySwitch {

	/**
	 * 邮件日志功能开关
	 */
	public static boolean EMAIL_LOG_SWITCH;

	public static boolean isEMAIL_LOG_SWITCH() {
		return EMAIL_LOG_SWITCH;
	}

	public static void setEMAIL_LOG_SWITCH(boolean eMAIL_LOG_SWITCH) {
		EMAIL_LOG_SWITCH = eMAIL_LOG_SWITCH;
	}

}
