package com.xinchen.cas.captcha.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证码 流程配置
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:50
 */
public class ValidateWebflowConfigurer extends AbstractCasWebflowConfigurer {

    /**
     * 校验码动作
     */
    public static final String VALIDATE_CAPTCHA_ACTION = "validateCaptchaAction";

    public ValidateWebflowConfigurer(FlowBuilderServices flowBuilderServices,
                                     FlowDefinitionRegistry loginFlowRegistry,
                                     ApplicationContext applicationContext,
                                     CasConfigurationProperties casProperties) {
        super(flowBuilderServices, loginFlowRegistry,applicationContext,casProperties);
    }

    @Override
    protected void doInitialize() {
        createLoginValidateValidateFlow();
    }

    /**
     * 登录校验流程
     */
    private void createLoginValidateValidateFlow() {
        final Flow flow = getLoginFlow();
        if (flow != null) {
            final ActionState state = (ActionState) flow.getState(CasWebflowConstants.STATE_ID_REAL_SUBMIT);
            // 用于存储当前的Action列表
            final List<Action> currentActions = new ArrayList<>();

            // 取出state中的Action进行填充填充Action
            state.getActionList().forEach(currentActions::add);

            // state 中先去除所有Action
            currentActions.forEach(a -> state.getActionList().remove(a));

            // 新建验证码 验证Action ，保证执行顺序是第一个
            state.getActionList().add(createEvaluateAction("validateLoginCaptchaAction"));

            // 将原有的Action添加回state
            currentActions.forEach(a -> state.getActionList().add(a));

            // 创建验证码错误 返回的Transition
            state.getTransitionSet().add(createTransition("captchaError", CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM));
        }
    }

}
