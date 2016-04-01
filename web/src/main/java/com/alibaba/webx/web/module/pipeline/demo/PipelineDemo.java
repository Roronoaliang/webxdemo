package com.alibaba.webx.web.module.pipeline.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.webx.common.factory.log.LoggerFactory;

/**
 * 拦截器例子
 * 
 * 需在pipeline.xml中配置该类的地址以及该拦截器在整条链路中的位置
 * 
 * @author xiaoMzjm
 */
public class PipelineDemo extends AbstractValve{

	 	@Autowired
	    private HttpServletRequest request;
	 	
	 	@Autowired
	    private HttpServletResponse response;
	 	
	 	private static Logger log = LoggerFactory.getLogger(PipelineDemo.class);
	 	
	    @Override
	    public void invoke(PipelineContext pipelineContext) throws Exception {
	    	
	    	test(pipelineContext);
	    	
	        pipelineContext.invokeNext();
	    }
	    
	    // 测试
	    public void test(PipelineContext pipelineContext){
	    	System.out.println("进入自定义拦截器，可在这里进行权限管理，数字签名验证等操作。");
	    	
	    	// 假设用户访问地址为：http://localhost:8080/demo/screenDemo.htm ， 则：
	        request.getRequestURL();			// http://localhost:8080/demo/screenDemo.htm
	        request.getRequestURI();			// /demo/screenDemo.htm
	        request.getContextPath();			// 空
	        request.getServletPath();			// /demo/screenDemo.htm
	        
	        pipelineContext.index(); 			// 当前valve在pipeline中的序号
	        pipelineContext.level(); 			// 当前pipeline在所有子pipeline中的级别
	        pipelineContext.isBroken(); 		// 当前pipeline是否已经被中断
	        pipelineContext.isFinished(); 		// 当前pipeline的所有valves是否已经执行完
	        
	        log.info("xxx");
	    }
}