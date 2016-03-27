package com.alibaba.webx.web.module.screen.captcha;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaAndLocale;
import com.octo.captcha.service.captchastore.CaptchaStore;

/**
 * 验证码【存储 & 获取】相关类
 * 
 * 
 * 由于原生的jcaptcha只是在单机用类似于map<sessionId,captche>的结构来存储验证码，不支持分布式，所以本类重写其存储相关的类。
 * 本类为分布式存储提供了可能，但没有实现分布式存储。
 * 如果要实现分布式存储，可以使用类似redis等技术，把id和对应的序列化后的CaptchaAndLocale存储到redis，读取的时候根据id读取。
 * 假如负载均衡采用的是ip_hash算法的话，也可不必实现验证码的分布式存储，因为每个用户都会访问到同一台服务器。
 * 
 * 
 * 下面简单分析下这个类被JCaptcha的调用过程：
 * 
 * 【存储过程】：
 * 每次我们通过imageCaptchaService.getImageChallengeForID来获取验证码时，JCaptcha都会先调用本类的hasCaptcha方法查看该session
 * 有没有对应的验证码未被remove还可以用，如果没有，则内部生成一个验证码，然后调用本类的storeCaptcha存储验证码；
 * 如果已经存在验证码，则直接调用本类的getCaptcha方法获取已存在的验证码去用，避免浪费，不过最后还是会调用一次storeCaptcha存储验证码，
 * 但是因为session_id一样，所以map的长度不会变化，跟没存一样。
 * 
 * 【验证过程】：
 * 先调用hasCaptcha看看该session_id有没有对应的验证码，有的话调用getCaptcha获取验证码去验证，最后调用removeCaptcha
 * 删除已验证的验证码。
 * 
 * @author xiaoMzjm
 */
public class MyCaptchaStore implements CaptchaStore{
	
	private Map<String,CaptchaAndLocale> map = new ConcurrentHashMap<String,CaptchaAndLocale>();
  
    @Override
    public boolean hasCaptcha(String id) {
        CaptchaAndLocale captcha = map.get(id);
        return captcha == null ? false : true;
    }

    @Override
    public void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException {
        try {
        	map.put(id,new CaptchaAndLocale(captcha));
        } catch (Exception e) {
            throw new CaptchaServiceException(e);
        }
    }

    @Override
    public void storeCaptcha(String id, Captcha captcha, Locale locale) throws CaptchaServiceException {
        try {
        	map.put(id,new CaptchaAndLocale(captcha,locale));
        } catch (Exception e) {
            throw new CaptchaServiceException(e);
        }
    }

    @Override
    public boolean removeCaptcha(String id) {
    	return map.remove(id) != null;
    }

    @Override
    public Captcha getCaptcha(String id) throws CaptchaServiceException {
        CaptchaAndLocale captchaAndLocale = map.get(id);
        return captchaAndLocale != null ? (captchaAndLocale.getCaptcha()) : null;
    }

    @Override
    public Locale getLocale(String id) throws CaptchaServiceException {
        CaptchaAndLocale captchaAndLocale = map.get(id);
        return captchaAndLocale != null ? (captchaAndLocale.getLocale()) : null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Collection getKeys() {
        return null;
    }

    @Override
    public void empty() {
    }
}