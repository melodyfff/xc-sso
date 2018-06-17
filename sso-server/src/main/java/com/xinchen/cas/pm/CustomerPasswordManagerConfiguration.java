package com.xinchen.cas.pm;

import com.xinchen.cas.pm.action.CustomerInitPasswordChangeAction;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.pm.PasswordManagementService;
import org.apereo.cas.pm.PasswordValidationService;
import org.apereo.cas.pm.web.flow.actions.InitPasswordResetAction;
import org.apereo.cas.pm.web.flow.actions.PasswordChangeAction;
import org.apereo.cas.pm.web.flow.actions.SendPasswordResetInstructionsAction;
import org.apereo.cas.pm.web.flow.actions.VerifyPasswordResetRequestAction;
import org.apereo.cas.pm.web.flow.actions.VerifySecurityQuestionsAction;
import org.apereo.cas.util.io.CommunicationsManager;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandler;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;

/**
 * 自定义密码管理
 * 这里自定义之后不会去加载默认的密码管理流程
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/12 23:15
 */
@Configuration("myPasswordManagementWebflowConfiguration")
@EnableConfigurationProperties({CasConfigurationProperties.class})
public class CustomerPasswordManagerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPasswordManagerConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CasConfigurationProperties casProperties;
    @Autowired
    @Qualifier("communicationsManager")
    private CommunicationsManager communicationsManager;
    @Autowired
    private FlowBuilderServices flowBuilderServices;
    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowDefinitionRegistry;
    @Autowired
    @Qualifier("loginFlowExecutor")
    private FlowExecutor loginFlowExecutor;
    @Autowired
    @Qualifier("passwordValidationService")
    private PasswordValidationService passwordValidationService;
    @Autowired
    @Qualifier("passwordChangeService")
    private PasswordManagementService passwordManagementService;

    public CustomerPasswordManagerConfiguration() {
    }


    @RefreshScope
    @Bean
    public HandlerAdapter passwordResetHandlerAdapter() {
        FlowHandlerAdapter handler = new FlowHandlerAdapter() {
            @Override
            public boolean supports(Object handler) {
                return super.supports(handler) && ((FlowHandler)handler).getFlowId().equals("pswdreset");
            }
        };
        handler.setFlowExecutor(this.loginFlowExecutor);
        return handler;
    }

    @ConditionalOnMissingBean(
            name = {"initPasswordChangeAction"}
    )
    @RefreshScope
    @Bean
    public Action initPasswordChangeAction(){
        return new CustomerInitPasswordChangeAction();
    }

    @ConditionalOnMissingBean(
            name = {"initPasswordResetAction"}
    )
    @RefreshScope
    @Bean
    public Action initPasswordResetAction() {
        return new InitPasswordResetAction(this.passwordManagementService);
    }

    @ConditionalOnMissingBean(
            name = {"passwordChangeAction"}
    )
    @RefreshScope
    @Bean
    public Action passwordChangeAction() {
        return new PasswordChangeAction(this.passwordManagementService, this.passwordValidationService);
    }

    @ConditionalOnMissingBean(
            name = {"sendPasswordResetInstructionsAction"}
    )
    @Bean
    @RefreshScope
    public Action sendPasswordResetInstructionsAction() {
        return new SendPasswordResetInstructionsAction(this.communicationsManager, this.passwordManagementService);
    }

    @ConditionalOnMissingBean(
            name = {"verifyPasswordResetRequestAction"}
    )
    @Bean
    @RefreshScope
    public Action verifyPasswordResetRequestAction() {
        return new VerifyPasswordResetRequestAction(this.passwordManagementService);
    }

    @ConditionalOnMissingBean(
            name = {"verifySecurityQuestionsAction"}
    )
    @Bean
    @RefreshScope
    public Action verifySecurityQuestionsAction() {
        return new VerifySecurityQuestionsAction(this.passwordManagementService);
    }

    @ConditionalOnMissingBean(
            name = {"customerPasswordManagerConfigurer"}
    )
    @RefreshScope
    @Bean
    @DependsOn({"defaultWebflowConfigurer"})
    public CasWebflowConfigurer passwordManagementWebflowConfigurer() {
        CasWebflowConfigurer w = new CustomerPasswordManagerConfigurer(this.flowBuilderServices, this.loginFlowDefinitionRegistry, this.applicationContext, this.casProperties, this.initPasswordChangeAction());
        w.initialize();
        return w;
    }
}
