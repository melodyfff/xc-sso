package com.xinchen.cas.auth.action;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.webflow.execution.Action;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/11 19:00
 */
@Configuration("customerActionConfig")
@EnableConfigurationProperties({CasConfigurationProperties.class})
@EnableTransactionManagement(
        proxyTargetClass = true
)
public class ActionConfig {

    @RefreshScope
    @Bean
    @ConditionalOnMissingBean(
            name = {"initializeLoginCheckCodeAction"}
    )
    public Action initializeLoginCheckCodeAction(){
        return new InitializeLoginCheckCodeAction();
    }

    @RefreshScope
    @Bean
    @ConditionalOnMissingBean(
            name = {"checkCodeAction"}
    )
    public Action checkCodeAction(){
        return new CheckCodeAction();
    }

}
