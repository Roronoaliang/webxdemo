#webxdemo使用指南
`webx学习指南`请移步到[这里](https://github.com/xiaoMzjm/webxdemo/blob/master/readme/how-to-learn-webx.md)<br>

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
>`proxool`是一款数据库连接池，可以用来实现多源数据库连接。<br> 
proxool下载地址：[proxool-0.9.1](https://sourceforge.net/projects/proxool/files/proxool/0.9.1/proxool-0.9.1.zip/download?use_mirror=heanet&download=)<br>
解压后进入到proxool.jar所在目录，运行以下命令，把jar包导入到本地仓库中。
```
mvn install:install-file -Dfile=proxool-0.9.1.jar -DgroupId=proxool -DartifactId=proxool -Dversion=0.9.1 -Dpackaging=jar
```

####1.4 导入sigar的dll文件
>`sigar`是一个开源的收集系统信息的工具，本项目用它来监听系统信息(cpu、内存、磁盘、网速)，但它需要把跟系统相关的dll文件放到`${JAVA_HOME}/bin`下。<br>
sigar下载地址：[sigar 1.6.4](http://iweb.dl.sourceforge.net/project/sigar/sigar/1.6/hyperic-sigar-1.6.4.zip)<br>
下载解压后，进入到解压后的文件hyperic-sigar-1.6.4\sigar-bin\lib目录，根据系统对相应的dll文件进行拷贝。<br>
* windows 64位：sigar-amd64-winnt.dll
* windows 32位：sigar-x86-winnt.dll
* linux 64位：libsigar-amd64-linux.so
* linux 32位：libsigar-x86-linux.so

####1.5 eclipse导入项目
>以`管理员身份`运行eclipse-->eclipse-->import-->`Existing Maven Projects`，把整个克隆下来的`webxdemo`导入，打开window-->show view-->`Problems`界面，按提示解决可能出现的错误。<br>
>之所以要以管理员身份运行，是因为webx在加载文件上传模块时，会创建一个/tmp目录（在webx.xml中配置），若不以管理员身份运行eclipse，待会在跑的时候可能因权限不够，无法创建/tmp文件夹而报错。<br>

####1.6 运行
>右键parent父项目-->run as-->Maven build...-->在Gloas后面输入`clean tomcat:run`-->点击Run运行。第一次运行console可能会出现如下信息，请输入yes。<br>

╭───────────────────────┈┈┈┈<br>
│<br>
│ 您的配置文件需要被更新：<br>
│<br>
│ file:/.../antx.properties<br>
│<br>
│ 这个文件包括了您个人的特殊设置，<br>
│ 包括服务器端口、您的邮件地址等内容。<br>
│<br>
└───────┈┈┈┈┈┈┈┈┈┈┈<br>
<br>
 如果不更新此文件，可能会导致配置文件的内容不完整。<br>
 您需要现在更新此文件吗? [Yes][No] yes<br>
 
####1.7 测试
>浏览器输入`http://localhost:8080/topview/captcha/captcha.do`(本项目的验证码工具的demo地址)，假如出现一个验证码图形，则代表项目正常运行。

<br>
###二 集成工具类（engine篇）
engine子项目下的工具，在`web子项目`的`src/test/java`目录下对应的包中都能找到`UseCase类`，里面有对应工具的`使用方法`。为什么engine子项目的测试用例写在web子项目下而不是engine下，是因为spring配置文件放在web子项目中，只有在web子项目才能结合spring+junit跑起来<br>
<br>
####2.1 Http工具
**简介**：<br>
>基于`HttpClient4.5` 的封装，支持基于`get`和`post`的基本的、`带参数`的、带`文件`的`HTTP`、`HTTPS`请求等功能。<br>

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
>使用前必须修改web子项目的biz-engine.xml文件，修改用来发送邮件的`邮箱以及密码`，用来发送的邮箱建议使用`QQ邮箱`，网易邮箱容易被当成`垃圾邮件`发不出去。

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
>基于mybatis-spring、proxool、proxool-cglib的封装，支持`多源`数据库的Session的获取。方便在没使用数据库中间件时，实现`读写分离`。<br>
> 另外，由于spring的`SqlSessionTempalte不支持多源`，所以实现了一个`MySqlSessionTemplate`，里面的代码是复制spring的SqlSessionTempalte的，拥有相同的可靠性与功能，只改了构造方法，支持传入读写库的key，由此来创建不同的数据库源。配置如下，使用时和SqlSessionTempalte一样。`使用例子`请看`engine`项目下com.alibaba.webx.searchengine.dao.impl包`DaoDempImpl`类。

```
 	<bean id="sqlSessionWriteTemplate" class="com.alibaba.webx.searchengine.factory.mybatis.MySqlSessionTemplate">
		<constructor-arg index="0" ref="sqlsessionfactory"></constructor-arg>
		<!-- 与dynamicDataSource这个bean中配置的key对应 -->
		<constructor-arg index="1" > <value>dataSourceKeyRW</value> </constructor-arg>
	</bean>
	<bean id="sqlSessionReadTemplate" class="com.alibaba.webx.searchengine.factory.mybatis.MySqlSessionTemplate">
		<constructor-arg index="0" ref="sqlsessionfactory"></constructor-arg>
		<constructor-arg index="1" > <value>dataSourceKeyR</value> </constructor-arg>
	</bean>
```

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
>使用前必须修改web子项目的biz-engine.xml文件，修改`redis数据库IP、端口、密码，连接池的最大连接数、连接等待时间，连接超时时间`等参数。

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
>在`try-catch`中，使用邮件日志组件把catch到的Exception传到队列中，邮件日志工具会定时（时间间隔可配置）把队列中的错误信息发到指定的邮箱（邮箱可以配置，支持群发）。在没有`日志管理分析`工具的情况下，使用该组件可以及时发现错误。<br>

```
try {
	int num = 5 / 0;
} catch (Exception e) {
	loggerUtils.emailError(e);
}
```

**注意事项1**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，设置接收邮件的`邮箱，邮件标题，发送邮件的时间间隔`等参数。<br>

```
	<bean id="loggerUtils" class="com.alibaba.webx.searchengine.util.log.LoggerUtils"  init-method="init">
		<!-- 要把错误推送给哪些邮箱 -->
		<property name="acceptorList">													
			<list>
				<value>xxx@163.com</value>
			</list>			
		</property>	
		<property name="emailTitle">	<value>XXX项目错误日志推送</value>		</property>	<!-- 邮件标题 -->
		<property name="sendEmailRate">	<value>3000</value>					</property>	<!-- 多少秒发一次邮件 -->
	</bean>
```
**注意事项2**：<br>
本工具使用2.7的开关工具，支持`一键开关`本功能，使用前注意配置文件biz-engine.xml和redis中的配置是否是打开状态：<br>
```
	<bean id="mySwitchUtil" class="com.alibaba.webx.searchengine.util.switchs.MySwitchUtil">
		<property name="EMAIL_LOG_SWITCH">				<value>true</value>		</property>	<!-- 邮件日志功能开关 -->
	</bean>
```
<br>
####2.6 验证码组件
**简介**：<br>
>基于`jcaptcha`的封装，写好了一个获取验证码的接口，客户端可以直接访问该接口获得验证码，后台可以通过一句代码验证验证码正确与否。并且`重写`了验证码的存储逻辑，为验证码分布式存储提供了可能（但没实现`分布式验证码`，因为若使用ip_hash的负载均衡策略，不实现分布式验证码也可以，如果要实现，请自己做二次开发，重新编写web子项目的com.alibaba.webx.web.module.screen.captcha包下的MyCaptchaStore类）。<br>

**注意事项**：<br>
>验证码样式类位置在：web子项目的com.alibaba.webx.web.module.screen.captcha包下的MyCaptchaEngine类，若要重新修改`样式`(比如字体大小，背景，干扰项等等)，可以修改该类的属性值。<br>

<br>
####2.7 降级开关工具
**简介**：<br>
>使用开关工具，在编写一些新功能时，可以在代码的最前方加入开关，以便一键开启/关闭新功能。假如一个功能上线后出了问题，导致其他服务受到影响，如果关闭服务器修复，肯定会影响到用户的正常使用，不修复的话用户又访问不了，有了开关工具后，此时我们可以在`redis数据库`中把开关关闭，即可停掉该功能，这样的话可以保证其他服务正常运行。开关的的获取会从`本地`和`默认redis数据库`中获取，当从redis获取失败时，自动从本地获取，方便没配redis的用户使用。 但没配redis的用户将使用不了从数据库控制开关的功能。<br>

```
if(mySwitchUtil.isEMAIL_LOG_SWITCH()){
    xxxx
}
```

**注意事项**：<br>
>使用前必须修改web子项目的biz-engine.xml文件，配置某个功能`开或关`。<br>

```
	<bean id="mySwitchUtil" class="com.alibaba.webx.searchengine.util.switchs.MySwitchUtil">
		<property name="EMAIL_LOG_SWITCH">				<value>true</value>		</property>	<!-- 邮件日志功能开关 -->
		<property name="DEMO_SWITCH">					<value>true</value>		</property>	<!-- 测试所用 -->
		<property name="EMAIL_SYSTEM_MONITOR_SWITCH">	<value>true</value>		</property>	<!-- 邮件系统级异常开关 -->
	</bean>
```

<br>
####2.8 系统监听器工具
**简介**：<br>
>在没有运维工具和运维人员的情况下，使用系统监听器工具，可以监听系统的`CPU`、`内存`、`磁盘`、`网速`等参数，一旦使用率或网速持续N秒（可配置）超出设定的阀值（可配置），则会使用邮件工具，以默认的发送邮箱发送的指定的接收邮箱。本工具默认在项目运行后自动轮训监听系统状态，无需手动调用。<br>

**注意事项**：<br>
本工具使用2.7的开关工具，支持`一键开关`本功能，使用前注意配置文件biz-engine.xml和redis中的配置是否是打开状态：<br>
```
	<bean id="mySwitchUtil" class="com.alibaba.webx.searchengine.util.switchs.MySwitchUtil">
		<property name="EMAIL_SYSTEM_MONITOR_SWITCH">	<value>true</value>		</property>	<!-- 邮件系统级异常开关 -->
	</bean>
```

<br>
###三 集成工具类（common篇）
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
>基于`poi`的封装，使用word工具，可以方便地进行word的操作。例如写入文字/图片，`替换模板占位符`，word转HTML等操作。<br>

<br>
####3.6 微信验证工具（数字签名工具）
>使用微信工具，可以方便地验证访问者，判断是否是来自微信后台的请求。也可以通过该工具，和APP端约定好使用相同的算法做数字请求的数字签名，验证请求者的身份。<br>

`注意事项`:<br>
>使用前必须修改web子项目的biz-common.xml文件，设置公众号的token（或和客户端约定好的密钥）<br>

```
	<bean id="signUtil" class="com.alibaba.webx.common.util.weixin.WeCharSignUtil" >
		<property name="token">	<value>xxx</value>	</property>	<!-- 公众号token -->
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
>使用`org.apache.commons-commons-lang`3和`commons-collections`<br>
*例如字符串判空：`StringUtils.isBlank(str);`<br>
*例如集合判空：`CollectionUtils.isEmpty(collection);`<br>

<br>
####4.3 开发时尽量要使用内存缓存
>使用`com.google.guava-guava`做缓存，例子位于service子项目的com.alibaba.webx.service.demo.impl包的`ServiceDemoImpl`类。<br>

<br>
####4.4 开发时可适当对象池
>使用commons-pool做对象池。<br>

<br>
####4.5 下传下载写法
>上传下载的写法在web子项目的com.alibaba.webx.web.module.screen.demo包下的`ScreenDemo`类，可以参考其写法。

<br>
####4.6 spring获取对象
>那些在spring配置文件里面配置的bean（例如上面提到的各种组件及工具），请用@Autowired获取，不要用new，避免一些应该被初始化的参数没被初始化。

<br>
####4.7 开发时统一使用的工具
>1、在每个功能前加上`2.7的开关工具`，以便在功能出现异常时一键开关本功能（配上`redis数据库`，否则也一键不了）。<br>
>2、捕获异常时使用`2.5的邮件日志工具`，以便在报错时，作为开发人员，可以第一时间获取错误，而不是等用户投诉。<br>
>3、打开`2.8的系统监听工具`，方便在系统出现超载时及时发现问题。

<br>
####4.8 返回数据格式
>统一json格式

| 参数名     |含义       | 类型      |例子     |
| :-------- | --------:| --------:|:--:    |
| state     | 状态码    |  int     |200     |
| message   | 错误信息  |  string  |权限不足  |
| data      | 数据      |  Object | xxx     |

>状态码约定：
>
| 状态码     |含义             | 
| :-------- | -------------: | 
| 200       | 请求成功        |  
| 250       | 登录成功        |
| 251       | 登出成功        |
| 401       | 权限不足         |  
| 403       | 账号或密码错误    | 
| 500       | 服务器出错       | 




<br>
###五 项目结构
####5.1、工程结构
![工程结构](https://raw.githubusercontent.com/xiaoMzjm/webxdemo/master/readme/%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png) <br>

####5.2、web子项目结构
**5.2.1、web子项目结构1**：<br>
![web子项目结构1](https://raw.githubusercontent.com/xiaoMzjm/webxdemo/master/readme/web%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png) <br>

**5.2.2、web子项目结构2**：<br>
![web子项目结构2](https://github.com/xiaoMzjm/webxdemo/blob/master/readme/web%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%842.png?raw=true)


