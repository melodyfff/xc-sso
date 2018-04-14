package com.xinchen.cas.sso.rest.client.config;

import com.xinchen.cas.sso.rest.client.bean.SysUser;
import com.xinchen.cas.sso.rest.client.service.UserRepertory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/14 23:37
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public UserRepertory userRepertory() {
        // 密码：123  MD5加密32位 小写
        SysUser admin = new SysUser().setUsername("rest-admin").setPassword("202cb962ac59075b964b07152d234b70").addAttribute("key", "keyVal");
        SysUser test = new SysUser().setUsername("rest-test").setPassword("202cb962ac59075b964b07152d234b70").addAttribute("test", "testVal");
        SysUser locked = new SysUser().setUsername("rest-locked").setPassword("202cb962ac59075b964b07152d234b70").setLocked(true);
        SysUser disable = new SysUser().setUsername("rest-disable").setPassword("202cb962ac59075b964b07152d234b70").setDisable(true);
        SysUser expired = new SysUser().setUsername("rest-expired").setPassword("202cb962ac59075b964b07152d234b70").setExpired(true);
        return new UserRepertory(admin, test, locked, disable, expired);
    }
}
