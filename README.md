# xc-sso
SSO Demo


 ssl开关
 
 `sso-config resources/config/sso-dev.properties`
 
```bsah
 server.ssl.enabled=true
 server.ssl.key-store=classpath:tomcat.keystore
 server.ssl.key-store-password=123456
 server.ssl.keyAlias=xinchen.sso.com
```

## 测试信息
用户：

| 用户名 |密码|是否可登录|备注|
|:-------|:-------|:-------|:-------|
|admin|123|√||
|zhangsan|12345678|√||
|zhaosi|12345|×|禁用|
|wangwu|1234|√|需修改密码|

## 模块介绍

| 模块名 |模块介绍|备注|端口情况|必须https|path|启动循序
|:-------|:-------|:-------|:----|:-------|:-----|:--|
|sso-server|cas服务|接入鉴权|8443|√|cas|2|
|sso-config|配置中心|管理各个服务配置|8888|×|config|1|
|sso-management|service管理|接入管理|8081|×|cas-management|3|