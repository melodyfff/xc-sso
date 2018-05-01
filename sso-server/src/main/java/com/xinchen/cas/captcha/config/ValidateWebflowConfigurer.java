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
import org.springframework.webflow.engine.ViewState;
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
//        createPasswordResetValidateFlow();
        createLoginValidateValidateFlow();
    }

    /**
     * 登录校验流程
     */
    private void createLoginValidateValidateFlow() {
        final Flow flow = getLoginFlow();
        if (flow != null) {
            final ActionState state = (ActionState) flow.getState("checkSubmit");
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

    /**
     * 发送邮箱前输入验证码流程
     */
    private void createPasswordResetValidateFlow() {
        final Flow flow = getLoginFlow();
        if (flow != null) {
            ViewState accountInfo = (ViewState) flow.getState(CasWebflowConstants.VIEW_ID_SEND_RESET_PASSWORD_ACCT_INFO);
            //提交查找用户后，先校验验证码
            createTransitionForState(accountInfo, "findAccount", VALIDATE_CAPTCHA_ACTION, true);
            //校验图片动作
            ActionState actionState = createActionState(flow, VALIDATE_CAPTCHA_ACTION, createEvaluateAction(VALIDATE_CAPTCHA_ACTION));
            //失败重新是发送页
            createTransitionForState(actionState, CasWebflowConstants.TRANSITION_ID_RESET_PASSWORD,
                    CasWebflowConstants.VIEW_ID_SEND_RESET_PASSWORD_ACCT_INFO);
            //发送邮件
            createTransitionForState(actionState, "sendInstructions", "sendInstructions");
        }
    }

}
