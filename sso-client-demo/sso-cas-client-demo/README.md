# 配置


需要启动配置中心、cas服务、客户端demo

启动demo:
```cmd
mvn jetty:run
mvn org.mortbay.jetty:maven-jetty-plugin:run   
```

访问：

[http://localhost:8080/sso-cas-client-demo](http://localhost:8080/sample)  

不需登录访问:

[http://localhost:8080/sso-cas-client-demo/zhangsan.jsp](http://localhost:8080/sample/zhangsan.jsp)