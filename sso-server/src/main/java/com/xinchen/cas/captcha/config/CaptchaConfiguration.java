package com.xinchen.cas.captcha.config;

import com.xinchen.cas.captcha.SessionCaptchaResultProvider;
import com.xinchen.cas.captcha.imp.cage.CageCaptchaController;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 17:32
 */
@Configuration("captchaConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CaptchaConfiguration {
    //注册bean到spring容器
    @Bean
    @ConditionalOnMissingBean(name = "captchaController")
    public CageCaptchaController captchaController() {
        return new CageCaptchaController(captchaResultProvider());
    }

    @Bean
    public SessionCaptchaResultProvider captchaResultProvider() {
        return new SessionCaptchaResultProvider();
    }
}
