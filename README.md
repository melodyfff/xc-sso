# xc-sso
SSO Demo
## Rest协议认证 Shiro代理认证
用户：

| 用户名 |密码|是否可登录|备注|验证器|
|:-------|:-------|:-------|:-------|:---|
|admin|123|√|可登录|shiro|
|rest-admin|123|√|可登录|shiro|
|rest-test|123|√|可登录|rest|
|rest-locked|123|×|锁定|rest|
|rest-disable|123|×|不可用|rest|
|rest-expired|123|×|过期，修改密码|rest|

## 模块介绍

|模块名|模块介绍|备注|端口情况|必须https|path|启动循序|
|:-------|:-------|:-------|:----|:-------|:-----|:--|
|sso-server|cas服务|接入鉴权|8443|√|cas|3|
|sso-config|配置中心|管理各个服务配置|8888|×|config|1|
|sso-rest-client|rest验证应用|rest验证应用|8883|×|/|2|