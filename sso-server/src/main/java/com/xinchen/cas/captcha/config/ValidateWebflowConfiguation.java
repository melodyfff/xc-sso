package com.xinchen.cas.captcha.config;

import com.xinchen.cas.captcha.SessionCaptchaResultProvider;
import com.xinchen.cas.captcha.action.ValidateLoginCaptchaAction;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:46
 */
@Configuration("validateWebflowConfiguation")
@EnableConfigurationProperties(CasConfigurationProperties.class)
//@AutoConfigureAfter(PasswordManagementConfiguration.class)
public class ValidateWebflowConfiguation {
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowRegistry;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @Autowired
    private SessionCaptchaResultProvider captchaResultProvider;

    @ConditionalOnMissingBean(name = "validateWebflowConfigurer")
    @RefreshScope
    @Bean
    public CasWebflowConfigurer validateWebflowConfigurer() {
        ValidateWebflowConfigurer validateWebflowConfigurer = new ValidateWebflowConfigurer(flowBuilderServices,
                loginFlowRegistry,
                applicationContext, casProperties);
        return validateWebflowConfigurer;
    }

    @ConditionalOnMissingBean(name = "validateLoginCaptchaAction")
    @Bean
    @RefreshScope
    public Action validateLoginCaptchaAction() {
        ValidateLoginCaptchaAction validateCaptchaAction = new ValidateLoginCaptchaAction(captchaResultProvider);
        return validateCaptchaAction;
    }
}
