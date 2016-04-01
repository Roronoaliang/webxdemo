#webxdemo使用指南
###一 快速运行项目
####1.1 一个可行的开发环境
>语言：`jdk1.7`<br>
IDE：`eclipse ee luna 4.4.2`<br>
版本控制：`git preview 1.9.5`<br>
项目管理：`maven 3.3.9`<br>

####1.2 克隆项目
```
git clone https://github.com/xiaoMzjm/webxdemo.git
```

####1.3 手动导入maven仓库没有的jar包
> proxool下载地址：[proxool-0.9.1](https://sourceforge.net/projects/proxool/files/proxool/0.9.1/proxool-0.9.1.zip/download?use_mirror=heanet&download=)<br>

```
mvn install:install-file -Dfile=proxool-0.9.1.jar -DgroupId=proxool -DartifactId=proxool -Dversion=0.9.1 -Dpackaging=jar
```

####1.4 eclipse导入项目
>按提示解决可能出现的错误<br>

####1.5 运行
```
mvn clean tomcat:run
```
 
####1.6 测试
>浏览器输入`http://localhost:8080/topview/captcha/captcha.do`(本框架集成的验证码组件的demo地址)，假如出现一个验证码图形，则代表项目正常运行。

<br>
###二 集成工具类（engine篇）
以下工具，在`web子项目`的`src/test/java`目录下对应的包中都能找到`UseCase类`，里面有对应工具的`使用方法`。为什么engine子项目的测试用例写在web子项目下而不是engine下，是因为spring配置文件放在web子项目中，只有在web子项目才能结合spring+junit跑起来<br>
<br>
####2.1 Http工具
**简介**：<br>
>基于`HttpClient4.5` 的封装，支持基于get和post的基本的、带参数的、带文件的HTTP、HTTPS请求等功能。<br>

**注意事项**：<br>
>使用前建议根据业务修改web子项目的biz-engine.xml文件，修改`连接池`，`请求超时`等相关参数。<br>

```
	<bean id="httpClientFactory" class="com.alibaba.webx.searchengine.factory.http.HttpClientFactory" init-method="init">
		<property name="maxConnectionNum">				<value>10</value> 			</property>	<!-- 最大连接数限制 -->
		<property name="maxGetConnectionTimeOut">		<value>5000</value> 		</property>	<!-- 最大获取连接超时限制 ，单位毫秒-->
		<property name="maxRouteConnectionNum">			<value>10</value> 			</property>	<!-- 每个路由最大的连接数限制 -->
		<property name="maxLastConnectionTimeOut">		<value>10000</value> 		</property>	<!-- 每个请求连接最长时间限制 ，单位毫秒-->
		<property name="maxGetDataTimeOut">				<value>5000</value> 		</property>	<!-- 获取数据最长时间限制，单位毫秒 -->
	</bean>
```
>若要使用`https`请求，必须加上`vm参数`：-Djsse.enableSNIExtension=false，否者报错javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name，原因请见[这里](http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0)。<br>

<br>
####2.2 mail工具
**简介**：<br>
>基于`javax.mail1.4.7 `的封装，支持`群发`带`附件`的`HTML`格式的邮件等基本功能。<br>

**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，修改用来发送邮件的`邮箱以及密码`，用来发送的邮箱建议使用`QQ邮箱`，网易邮箱容易被当成垃圾邮件发不出去。

```
	<bean id="mailFactory" class="com.alibaba.webx.searchengine.factory.mail.MailFactory" init-method="init">
		<property name="defaultMailServerHost">		<value>smtp.qq.com</value> 		</property>	<!-- 邮件服务地址 -->
		<property name="defaultMailServerPort">		<value>25</value> 				</property>	<!-- 端口号 -->
		<property name="defaultSenderAddress">		<value>xxxxxxxxx</value> 		</property> <!-- 用来发送邮件的账号 -->
		<property name="defaultPassword">			<value>xxxxxx</value> 			</property>	<!-- 账号对应的密码 -->
	</bean>
```
<br>
####2.3 mybatis工具
**简介**：<br>
>基于mybatis-spring、proxool、proxool-cglib的封装，支持`多源`数据库的Session的获取。方便在没使用数据库中间件时，实现读写分离。<br>

**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，修改与`数据库连接`相关的参数以及`连接池`相关的参数。

```
	<bean id="dataSourceRW" class="org.logicalcobwebs.proxool.ProxoolDataSource">  
	    <property name="alias" value="ReadWriteDataBase"></property>  
	    <property name="delegateProperties">  
	        <value>user=root,password=root</value>  
	    </property>  
	    <property name="user" value="root" />  
	    <property name="password" value="root" />  
	    <property name="driver" value="com.mysql.jdbc.Driver" />  
	    <property name="driverUrl" value="jdbc:mysql://localhost:3306/test" />
	    <property name="minimumConnectionCount" value="5" />							<!-- 确定池中最小连接数 -->
	    <property name="houseKeepingTestSql" value="select user from mysql.user" />		<!-- 确定保证连接可能的定时查询SQL -->
	    <property name="testBeforeUse" value="true" />									<!-- 检测连接是否可以用，没用的话就换条连接 -->
	    <property name="maximumActiveTime" value="5" />									<!-- 确定连接的最大持续使用时间，超时断开，单位为分钟 -->
	    <property name="prototypeCount" value="2" />									<!-- 确定该连接被使用后，池中还有多少空闲连接，是否得重新创建新的备用，需要的话要创建多少个 -->
	    <property name="maximumConnectionLifetime" value="4" />							<!-- 确定在连接数大于最小连接数时，多出的空闲连接多久被杀死，单位为小时 -->
	    <property name="maximumConnectionCount" value="15" />							<!-- 确定一个阀门，规定池中最大连接数 -->
	    <prope
```
<br>
####2.4 redis工具
**简介**：<br>
>基于jedis的封装，从`连接池`从获取jedis对象。<br>

**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，修改`数据库IP、端口、密码，连接池的最大连接数、连接等待时间，连接超时时间`等参数。

```
	<bean id="redisFactory" class="com.alibaba.webx.searchengine.factory.redis.RedisFactory" init-method="init">
		<property name="poolMaxIdel">			<value>8</value> 				</property>	<!-- 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例 -->
		<property name="poolMaxWaitMillis">		<value>5000</value> 			</property>	<!-- 获取连接时的最大等待时间，单位毫秒 ,默认值为-1，表示永不超时 -->
		<property name="poolTestOnBorrow">		<value>true</value> 			</property>	<!-- 是否在获取连接的时候检查有效性, 默认false -->
		<property name="poolIp">				<value>xxx</value> 				</property>	<!-- 数据库的地址 -->
		<property name="poolPort">				<value>6379</value> 			</property>	<!-- 数据库的端口 -->
		<property name="poolConnectTimeOut">	<value>5000</value> 			</property>	<!-- 连接持续连接的超时时间，单位毫秒-->
		<property name="poolPassword">			<value>xxxxxxxx</value> 		</property>	<!-- 数据库密码 -->
	</bean>
```
<br>
####2.5 邮件日志组件
**简介**：<br>
>在`try-catch`中，使用邮件日志组件把catch到的Exception传到邮件队列中，邮件日志组件会定时把队列中的错误信息发到指定的邮箱。在没有`日志管理分析`工具的情况下，使用该组件可以及时发现错误。<br>

```
try {
	int num = 5 / 0;
} catch (Exception e) {
	loggerUtils.emailError(e);
}
```

**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，设置接收邮件的`邮箱，邮件标题，用来发送邮件的线程池的大小，发送邮件的时间间隔`等参数。<br>

```
	<bean id="loggerUtils" class="com.alibaba.webx.searchengine.util.log.LoggerUtils"  init-method="init">
		<property name="acceptorList">	<value>xxx@163.com</value>			</property>	<!-- 要把错误推送给哪些邮箱 -->
		<property name="emailTitle">	<value>XXX项目错误日志推送</value>		</property>	<!-- 邮件标题 -->
		<property name="threadNum">		<value>10</value>					</property>	<!-- 用来发邮件的线程数 -->
		<property name="sendEmailRate">	<value>20</value>					</property>	<!-- 多少秒发一次邮件 -->
	</bean>
```
<br>
####2.6 验证码组件
**简介**：<br>
>基于`jcaptcha`的封装，写好了一个获取验证码的接口，客户端可以直接访问该接口获得验证码，后台可以通过一句代码验证验证码正确与否。并且`重写`了验证码的存储逻辑，为验证码分布式存储提供了可能（但没实现`分布式验证码`，因为若使用ip_hash的负载均衡策略，不实现分布式验证码也可以，如果要实现，请自己做二次开发）。<br>

**注意事项**：<br>
>验证码样式类位置在：web子项目的com.alibaba.webx.web.module.screen.captcha包下的MyCaptchaEngine类，若要重新修改`样式`(比如字体大小，背景，干扰项等等)，可以修改该类的属性值。<br>

<br>
###三 集成工具类（coomon篇）
`以下工具，在各自的包的下面都有一个UseCase类，里面有对应组件的使用方法。`<br>
<br>
####3.1 日期工具
>使用日期工具，可以方便地获取当天日期和`前后N天`的日期（Date、String、Long格式），也可以方便地对`Date、String、Long`三种格式的日期进行互相转化。<br>

<br>
####3.2 excel工具
>基于`jxl`的封装，使用excel工具，可以方便地对excel进行读写操作。内定了比较好看的基本样式，包括颜色，边框等等。当然也可以自己修改样式。<br>

<br>
####3.3 image工具
>使用image工具，可以方便地对图片进行操作。例如`裁剪、缩小放大、图片拼接、图像合并、去色、水印、画图`等等。常用的做法是用来对用户上传的图片进行`压缩`。<br>

<br>
####3.4 MD5工具
>使用MD5工具，可以方便地进行MD5加密。<br>

<br>
####3.5、 word工具
>基于`poi`的封装，使用word工具，可以方便地进行word的操作。例如写入文字/图片，替换模板占位符，word转HTML等操作。<br>

<br>
####3.6 微信验证工具
>使用微信工具，可以方便地验证访问者，判断是否是来自微信后台的请求。也可以通过该工具，和APP端约定好使用相同的算法做数字请求的数字签名，验证请求者的身份。<br>

`注意事项`:<br>
>使用前必须修改web子项目的biz-common.xml文件，设置公众号的token（密钥）<br>

```
	<bean id="signUtil" class="com.alibaba.webx.common.util.weixin.WeCharSignUtil" >
		<property name="token">	<value>xxx</value>	</property>	<!-- 公众号token -->
	</bean>
```

<br>
####3.7 开关工具
>使用开关工具，在编写一些新功能时，可以在代码的最前方加入开关，以便一键开启/关闭新功能。假如一个功能上线后出了问题，导致其他服务受到影响，如果关闭服务器修复，肯定会影响到用户的正常使用，不修复的话用户又访问不了，有了开关工具后，此时我们可以在`redis数据库`中把开关关闭，即可停掉该功能，这样的话可以保证其他服务正常运行。开关的的获取会从`本地`和`默认redis数据库`中获取，当从redis获取失败时，自动从本地获取，方便没配redis的用户使用。 但没配redis的用户将使用不了从数据库控制开关的功能。<br>

**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，配置某个功能`开或关`。<br>

```
	<bean id="mySwitchUtil" class="com.alibaba.webx.searchengine.util.switchs.MySwitchUtil" init-method="init">
		<property name="EMAIL_LOG_SWITCH">	<value>true</value>		</property>	<!-- 邮件日志功能开关 -->
		<property name="DEMO_SWITCH">		<value>true</value>		</property>
	</bean>
```

<br>
###四 开发规范与约定
####4.1  json
>使用`fastjson`进行json序列化，例如：<br>

```java
String jsonStr = JSON.toJSONString(object);
```

<br>
####4.2 字符串、集合
使用`org.apache.commons-commons-lang`3和`commons-collections`<br>
例如字符串判空：`StringUtils.isBlank(str);`<br>
例如集合判空：`CollectionUtils.isEmpty(collection);`<br>

<br>
####4.3 内存缓存
>使用`com.google.guava-guava`做缓存，例子位于service子项目的com.alibaba.webx.service.demo.impl包的`ServiceDemoImpl`类。<br>

<br>
####4.4 对象池
>使用commons-pool做对象池。<br>

<br>
####4.5 下传下载写法
>上传下载的写法在web子项目的com.alibaba.webx.web.module.screen.demo包下的`ScreenDemo`类，可以参考其写法。

<br>
####4.6 spring获取对象
>那些在spring配置文件里面配置的bean（例如上面提到的各种组件及工具），请用@Autowired获取，不要用new，避免一些应该被初始化的参数没被初始化。
