/**
 * 依赖外面的函数：
 * 		1、jquery
 * 
 * 向外提供的函数：
 * 		1、重定向到https
 * 		2、添加浏览器缓存
 * 		3、获取浏览器缓存
 * 		4、loading样式
 * 	
 * 向外提供的变量：
 * 		1、allDomain：域名
 */
define(['jquery'],function($){
	
	var allDomain = "https://www.heijue.top/heijue/";
	//var allDomain = "http://localhost:8080/";
	//var allDomain = "http://192.168.1.101:8080/";
	

	/**
	 * 重定向到https
	 */
	function changeDomainToHttps() {
		var host = window.location.host;
		if(host.indexOf('heijue') != -1) {
			var targetProtocol = "https:";
		    if (window.location.protocol != targetProtocol)
		    window.location.href = targetProtocol +
		    window.location.href.substring(window.location.protocol.length);
		}
	}

	/**
	 * 添加浏览器缓存
	 */
	function setLocalStorageItem(key,value) {
		if(window.globalStorage) {
			window.globalStorage.namedItem(allDomain).setItem(JSON.stringify(key),JSON.stringify(value));
		}
		else if(window.localStorage){
			window.localStorage.setItem(JSON.stringify(key),JSON.stringify(value));
//			alert('setLocalStorageItem,key='+JSON.stringify(key)+',value='+JSON.stringify(value));
		}
	}

	/**
	 * 获取浏览器缓存
	 */
	function getLocalStorageItem(key) {
		var data ;
		if(window.globalStorage) {
			data = window.globalStorage.namedItem(allDomain).getItem(JSON.stringify(key));
		}
		else if(window.localStorage){
			data = window.localStorage.getItem(JSON.stringify(key));
		}
		return JSON.parse(data);
	}
	

	// window8的loading样式
	function loading(id) {
		// 显示正在加载文案
		$(id).empty();
		var html = 
		"<div class='windows8'>"+
			"<div class='wBall' id='wBall_1'>"+
				"<div class='wInnerBall'></div>"+
			"</div>"+
			"<div class='wBall' id='wBall_2'>"+
				"<div class='wInnerBall'></div>"+
			"</div>"+
			"<div class='wBall' id='wBall_3'>"+
				"<div class='wInnerBall'></div>"+
			"</div>"+
			"<div class='wBall' id='wBall_4'>"+
				"<div class='wInnerBall'></div>"+
			"</div>"+
			"<div class='wBall' id='wBall_5'>"+
				"<div class='wInnerBall'></div>"+
			"</div>"+
		"</div>";
		$(id).html(html);
	}
	
	// 显示正在加载fakeLoader插件
	function openFakeLoader(){
		$('body').append('<div class="fakeloader"></div>');
		$(document).ready(function(){
	        $(".fakeloader").fakeLoader({
	            timeToHide:100000,
	            bgColor:"rgba(0,0,0,0.4)",
	            spinner:"spinner2"
	        });
	    });
	}
	
	// 隐藏正在加载fakeLoader插件
	function closeFakeLoader(){
		$('.fakeloader').remove();
	}
	
	return {
		allDomain:allDomain,
		changeDomainToHttps:changeDomainToHttps,
		setLocalStorageItem:setLocalStorageItem,
		getLocalStorageItem:getLocalStorageItem,
		loading:loading,
		openFakeLoader:openFakeLoader,
		closeFakeLoader:closeFakeLoader,
	};
})