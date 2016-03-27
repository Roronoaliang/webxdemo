#webxdemo使用指南
---
###一、快速运行项目
1. **一个可行的开发环境**<br>
语言：`jdk1.7`<br>
IDE：`eclipse ee luna 4.4.2`<br>
版本控制：`git preview 1.9.5`<br>
项目管理：`maven 3.3.9`<br>
<br>
2. **克隆项目**<br>
```
git clone https://github.com/xiaoMzjm/webxdemo.git
```
<br>
3. **手动导入maven仓库没有的jar包**<br>
(1) proxool下载地址：[proxool-0.9.1](https://sourceforge.net/projects/proxool/files/proxool/0.9.1/proxool-0.9.1.zip/download?use_mirror=heanet&download=)
```
mvn install:install-file -Dfile=proxool-0.9.1.jar -DgroupId=proxool -DartifactId=proxool -Dversion=0.9.1 -Dpackaging=jar
```
<br>
4. **eclipse导入项目**<br>
按提示解决可能出现的错误<br>
<br>
5. **运行**
```
mvn tomcat:run
```
 <br>
6. **测试**
浏览器输入`http://localhost:8080/topview/captcha/captcha.do`(本框架集成的验证码组件的demo地址)，假如出现一个验证码图形，则代表项目正常运行。

###二、集成组件介绍
1. **Http**<br>
简介：基于HttpClient4.5 的封装，封装了基于get和post的基本的、带参数的、带文件的请求功能。<br>
注意：<br>
(1) 使用前建议根据业务修改web子项目的biz-engine.xml文件，修改连接池，请求超时等相关参数。<br>
(2) 若要使用https请求，必须加上vm参数：-Djsse.enableSNIExtension=false，否者报错javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name，原因请见(这里)[http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0]
