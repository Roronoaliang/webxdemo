#webxdemo使用指南
###一、快速运行项目
1、 **一个可行的开发环境**<br>
>语言：`jdk1.7`<br>
IDE：`eclipse ee luna 4.4.2`<br>
版本控制：`git preview 1.9.5`<br>
项目管理：`maven 3.3.9`<br>

<br>
2、**克隆项目**<br>
```
git clone https://github.com/xiaoMzjm/webxdemo.git
```

<br>
3、 **手动导入maven仓库没有的jar包**<br>
> proxool下载地址：[proxool-0.9.1](https://sourceforge.net/projects/proxool/files/proxool/0.9.1/proxool-0.9.1.zip/download?use_mirror=heanet&download=)
```
mvn install:install-file -Dfile=proxool-0.9.1.jar -DgroupId=proxool -DartifactId=proxool -Dversion=0.9.1 -Dpackaging=jar
```

<br>
4、 **eclipse导入项目**<br>
>按提示解决可能出现的错误<br>

<br>
5、 **运行**
```
mvn tomcat:run
```
 
 <br>
6、 **测试**
>浏览器输入`http://localhost:8080/topview/captcha/captcha.do`(本框架集成的验证码组件的demo地址)，假如出现一个验证码图形，则代表项目正常运行。

<br>
###二、集成组件介绍
`以下组件，在各自的包的下面都有一个UseCase类，里面有对应组件的使用方法。`<br>
<br>
1、 **Http组件**<br>
<br>
**简介**：<br>
>基于`HttpClient4.5` 的封装，支持基于get和post的基本的请求，带参数的、带文件的请求等功能。<br>

<br>
**注意事项**：<br>
>使用前建议根据业务修改web子项目的biz-engine.xml文件，修改`连接池`，`请求超时`等相关参数。<br>
```
	<bean id="httpClientFactory" class="com.alibaba.webx.searchengine.factory.http.HttpClientFactory" init-method="init">
		<property name="maxConnectionNum">				<value>10</value> 			</property>
		<property name="maxGetConnectionTimeOut">		<value>5000</value> 		</property>
		<property name="maxRouteConnectionNum">			<value>10</value> 			</property>
		<property name="maxLastConnectionTimeOut">		<value>10000</value> 		</property>
		<property name="maxGetDataTimeOut">				<value>5000</value> 		</property>
	</bean>
```
>若要使用`https`请求，必须加上vm参数：-Djsse.enableSNIExtension=false，否者报错javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name，原因请见[这里](http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0)。<br>

<br>
2、 **mail组件**<br>
<br>
**简介**：<br>
>基于`javax.mail1.4.7 `的封装，支持群发带附件的HTML格式的邮件等基本功能。<br>

<br>
**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，修改用来发送邮件的`邮箱以及密码`。
```
	<bean id="mailFactory" class="com.alibaba.webx.searchengine.factory.mail.MailFactory" init-method="init">
		<property name="defaultMailServerHost">		<value>smtp.163.com</value> 			</property>
		<property name="defaultMailServerPort">		<value>25</value> 						</property>
		<property name="defaultSenderAddress">		<value>xxx@163.com</value> 	</property>
		<property name="defaultPassword">			<value>xxx</value> 				</property>
	</bean>
```
<br>
3、 **mybatis组件**<br>
 <br>
**简介**：<br>
>基于mybatis-spring、proxool、proxool-cglib的封装，支持多源数据库的Session的获取。方便在没使用数据库中间件时，实现读写分离。<br>

<br>
**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，修改与`数据库连接`相关的参数以及`连接池`相关的参数。
```
	<bean id="myBatisFactory" class="com.alibaba.webx.searchengine.factory.mybatis.MyBatisFactory" init-method="init"></bean>
	<!-- 写库 -->
	<bean id="dataSourceRW" class="org.logicalcobwebs.proxool.ProxoolDataSource">  
	    <property name="alias" value="ReadWriteDataBase"></property>  
	    <property name="delegateProperties">  
	        <value>user=test,password=123456</value>  
	    </property>  
	    <property name="user" value="test" />  
	    <property name="password" value="123456" />  
	    <property name="driver" value="com.mysql.jdbc.Driver" />  
	    <property name="driverUrl" value="jdbc:mysql://192.168.236.128:32768/test" />
	    <property name="minimumConnectionCount" value="5" />							<!-- 确定池中最小连接数 -->
	    <property name="houseKeepingTestSql" value="select user from mysql.user" />		<!-- 确定保证连接可能的定时查询SQL -->
	    <property name="testBeforeUse" value="true" />									<!-- 检测连接是否可以用，没用的话就换条连接 -->
	    <property name="maximumActiveTime" value="5" />									<!-- 确定连接的最大持续使用时间，超时断开，单位为分钟 -->
	    <property name="prototypeCount" value="2" />									<!-- 确定该连接被使用后，池中还有多少空闲连接，是否得重新创建新的备用，需要的话要创建多少个 -->
	    <property name="maximumConnectionLifetime" value="4" />							<!-- 确定在连接数大于最小连接数时，多出的空闲连接多久被杀死，单位为小时 -->
	    <property name="maximumConnectionCount" value="15" />							<!-- 确定一个阀门，规定池中最大连接数 -->
	    <property name="houseKeepingSleepTime" value="30" />							<!-- 确定多久检查连接的可用性，扫除无用的连接，创建新的可用连接，单位为秒 -->
	</bean> 
```
<br>
4、 **redis组件**<br>
<br>
**简介**：<br>
>基于jedis的封装，提供了对redis数据库基本操作的功能。<br>

<br>
**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，修改`数据库IP、端口、密码，连接池的最大连接数、连接等待时间，连接超时时间`等参数。
```
	<bean id="redisFactory" class="com.alibaba.webx.searchengine.factory.redis.RedisFactory" init-method="init">
		<property name="poolMaxIdel">			<value>8</value> 				</property>
		<property name="poolMaxWaitMillis">		<value>5000</value> 			</property>
		<property name="poolTestOnBorrow">		<value>true</value> 			</property>
		<property name="poolIp">				<value>ip</value> 	</property>
		<property name="poolPort">				<value>6379</value> 			</property>
		<property name="poolConnectTimeOut">	<value>5000</value> 			</property>
		<property name="poolPassword">			<value>xxxx</value> 		</property>
	</bean>
```
<br>
5、 **邮件日志组件**<br>
<br>
**简介**：<br>
>在try-catch中，使用邮件日志组件把catch到的Exception传到邮件队列中，邮件日志组件会定时把队列中的错误信息发到指定的邮箱。在没有`日志管理分析`工具的情况下，使用该组件可以及时发现错误。<br>

<br>
**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，设置接收邮件的`邮箱，邮件标题，用来发送邮件的线程池的大小，发送邮件的时间间隔`等参数。
```
	<bean id="loggerUtils" class="com.alibaba.webx.searchengine.util.log.LoggerUtils"  init-method="init">
		<property name="acceptorList">	<value>topviewacceptor@163.com</value>	</property>
		<property name="emailTitle">	<value>XXX项目错误日志推送</value>			</property>
		<property name="threadNum">		<value>10</value>						</property>
		<property name="sendEmailRate">	<value>3600</value>						</property>
	</bean>
```
<br>
6、 **验证码组件**<br>
<br>
**简介**：<br>
>基于jcaptcha的封装，写好了一个获取验证码的接口，客户端可以直接访问该接口获得验证码，后台可以通过一句代码验证验证码正确与否。并且`重写`了验证码的存储逻辑，为验证码分布式存储提供了可能（但没实现`分布式验证码`，因为若使用ip_hash的负载均衡策略，不实现分布式验证码也可以，如果要实现，请自己做二次开发）。<br>

<br>
**注意事项**：<br>
>验证码样式类位置在：web子项目的com.alibaba.webx.web.module.screen.captcha包下的MyCaptchaEngine类，若要重新修改`样式`(比如字体大小，背景，干扰项等等)，可以修改该类的属性值。<br>

<br>
###三、集成的工具介绍
`以下工具，在各自的包的下面都有一个UseCase类，里面有对应组件的使用方法。`<br>
<br>
1、**日期工具**<br>
>使用日期工具，可以方便地获取当天日期和前后N天的日期（Date、String、Long格式），也可以方便地对Date、String、Long三种格式的日期进行互相转化。<br>

<br>
2、 **excel工具**<br>
>基于`jxl`的封装，使用excel工具，可以方便地对excel进行读写操作。内定了比较好看的基本样式，包括颜色，边框等等。当然也可以自己修改样式。<br>

<br>
3、 **image工具**<br>
>使用image工具，可以方便地对图片进行操作。例如裁剪、缩小放大、图片拼接、图像合并、去色、水印、画图等等。常用的做法是用来对用户上传的图片进行`压缩`。<br>

<br>
4、 **MD5工具**<br>
>使用MD5工具，可以方便地进行MD5加密。<br>

<br>
5、 **word工具**<br>
>基于`poi`的封装，使用word工具，可以方便地进行word的操作。例如写入文字/图片，替换模板占位符，word转HTML等操作。<br>

<br>
6、**微信工具**<br>
>使用微信工具，可以方便地验证访问者，判断是否是来自微信后台的请求。<br>
`注意事项`:<br>
使用前必须修改web子项目的biz-common.xml文件，设置公众号的token<br>
```
	<bean id="signUtil" class="com.alibaba.webx.common.util.weixin.SignUtil" >
		<property name="token"><value>xxx</value></property>
	</bean>
```

<br>
7、 **开关工具**<br>
>使用开关工具，在编写一些新功能时，可以在代码的最前方加入开关，以便一键开启/关闭新功能。假如一个功能上线后出了问题，导致其他服务受到影响，此时我们把开关关闭，即可停掉该功能，保证其他服务正常运行。<br>
`注意事项`：<br>
开关功能与redis结合起来，需对redis进行配置。<br>
```
TODO
```

<br>
###四、开发规范与约定
1、 **json**<br>
>使用`fastjson`进行json序列化，例如：<br>
```java
String jsonStr = JSON.toJSONString(object);
```

<br>
2、**字符串、集合**<br>
使用`org.apache.commons-commons-lang`3和`commons-collections`<br>
例如字符串判空：<br>
```java
StringUtils.isBlank(str);
```
例如集合判空：<br>
```java
CollectionUtils.isEmpty(collection);
```

<br>
3、**缓存**<br>
>使用`com.google.guava-guava`做缓存，例子位于service子项目的com.alibaba.webx.service.demo.impl包的`ServiceDemoImpl`类。<br>

<br>
4、**对象池**<br>
>使用commons-pool做对象池。

