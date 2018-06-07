package com.xinchen.cas.pm;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.pm.PasswordManagementService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/3 21:46
 */
@Configuration("MyPasswordConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class MyPasswordConfiguration {

    @Bean
    public PasswordManagementService passwordChangeService() {
        return null;
    }

}
