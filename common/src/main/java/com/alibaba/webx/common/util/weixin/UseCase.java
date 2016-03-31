package com.alibaba.webx.common.util.weixin;


/**
 * 微信后台请求校验工具类  使用用例
 * 
 * 例子在第29行，因为没有HttpServletRequest环境，所以把代码注释起来。不然报错。
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {
	
	/** 
     * 确认请求来自微信服务器 
     */  
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
//        // 微信加密签名  
//        String signature = request.getParameter("signature");  
//        // 时间戳  
//        String timestamp = request.getParameter("timestamp");  
//        // 随机数  
//        String nonce = request.getParameter("nonce");  
//        // 随机字符串  
//        String echostr = request.getParameter("echostr");  
//  
//        PrintWriter out = response.getWriter();  
//        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
//        
//        if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
//            out.print(echostr);  
//        }  
//        out.close();  
//        out = null;  
//    }  
  
    /** 
     * 处理微信服务器发来的消息 
     */  
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
//        // TODO 消息的接收、处理、响应  
//    }
}
