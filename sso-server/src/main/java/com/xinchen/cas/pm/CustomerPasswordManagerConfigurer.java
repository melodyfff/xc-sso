package com.xinchen.cas.pm;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.pm.PasswordChangeBean;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.SubflowState;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.TransitionableState;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

/**
 * 自定义修改密码流程
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/12 22:45
 */
public class CustomerPasswordManagerConfigurer extends AbstractCasWebflowConfigurer {

    private final Action initPasswordChangeAction;

    public CustomerPasswordManagerConfigurer(FlowBuilderServices flowBuilderServices,
                                             FlowDefinitionRegistry loginFlowDefinitionRegistry,
                                             ApplicationContext applicationContext,
                                             CasConfigurationProperties casProperties, Action initPasswordChangeAction) {
        super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
        this.initPasswordChangeAction = initPasswordChangeAction;

    }

    @Override
    protected void doInitialize() {
        Flow flow = this.getLoginFlow();
        if (flow != null) {
            this.createAccountStatusViewStates(flow);
        }
    }

    private void createAccountStatusViewStates(Flow flow) {
        //----------------------------
        // 创建视图
        //----------------------------
        this.createViewState(flow, "casAuthenticationBlockedView", "casAuthenticationBlockedView");
        this.createViewState(flow, "casBadWorkstationView", "casBadWorkstationView");
        this.createViewState(flow, "casBadHoursView", "casBadHoursView");
        this.createViewState(flow, "casAccountLockedView", "casAccountLockedView");
        this.createViewState(flow, "casAccountDisabledView", "casAccountDisabledView");
        this.createViewState(flow, "casPasswordUpdateSuccess", "casPasswordUpdateSuccessView");
        if (this.casProperties.getAuthn().getPm().isEnabled()) {
            // 密码过期必须修改密码
            this.configurePasswordResetFlow(flow, "casExpiredPassView");
            // 必须修改密码
            this.configurePasswordResetFlow(flow, "casMustChangePassView");
            this.configurePasswordMustChangeForAuthnWarnings(flow);

            // 创建修改密码流程
            this.createPasswordResetFlow();
        } else {
            this.createViewState(flow, "casExpiredPassView", "casExpiredPassView");
            this.createViewState(flow, "casMustChangePassView", "casMustChangePassView");
        }
    }

    private void createPasswordResetFlow() {
        Flow flow = this.getLoginFlow();
        if (flow != null) {
            boolean autoLogin = this.casProperties.getAuthn().getPm().isAutoLogin();
            // 获取ViewState
            ViewState state = (ViewState) this.getState(flow, "viewLoginForm", ViewState.class);

            // 创建 transition on=myResetPassword to=casResetPasswordSendInstructionsView
            // 注意这里的 myResetPassword 为前端页面的eventId
            this.createTransitionForState(state, "myResetPassword", "casResetPasswordSendInstructionsView");

            // ---------------------------
            // 创建视图 accountInfo
            // 为accountInfo绑定Action
            // ---------------------------
            ViewState accountInfo = this.createViewState(flow, "casResetPasswordSendInstructionsView", "casResetPasswordSendInstructionsView");
            // 查找用户
            this.createTransitionForState(accountInfo, "findAccount", "sendInstructions");
            ActionState sendInst = this.createActionState(flow, "sendInstructions", this.createEvaluateAction("sendPasswordResetInstructionsAction"));
            this.createTransitionForState(sendInst, "success", "casResetPasswordSentInstructionsView");
            this.createTransitionForState(sendInst, "error", accountInfo.getId());


            this.createViewState(flow, "casResetPasswordSentInstructionsView", "casResetPasswordSentInstructionsView");

            // 加载密码认证流程
            Flow pswdFlow = this.buildFlow("classpath:/webflow/pswdreset/pswdreset-webflow.xml", "pswdreset");
            this.createViewState(pswdFlow, "passwordResetErrorView", "casResetPasswordErrorView");
            this.createViewState(pswdFlow, "casPasswordUpdateSuccess", "casPasswordUpdateSuccessView");

            // 配置修改密码流程
            this.configurePasswordResetFlow(pswdFlow, "casMustChangePassView");
            this.loginFlowDefinitionRegistry.registerFlowDefinition(pswdFlow);
            ActionState initializeLoginFormState = (ActionState) this.getState(flow, "initializeLoginForm", ActionState.class);
            String originalTargetState = initializeLoginFormState.getTransition("success").getTargetStateId();
            SubflowState pswdResetSubFlowState = this.createSubflowState(flow, "pswdResetSubflow", "pswdreset");
            this.getTransitionableState(flow, "realSubmit").getEntryActionList().add(this.createEvaluateAction("flowScope.doChangePassword = requestParameters.doChangePassword != null"));
            this.createDecisionState(flow, "checkForPswdResetToken", "requestParameters.pswdrst != null", "pswdResetSubflow", originalTargetState);
            this.createTransitionForState(initializeLoginFormState, "success", "checkForPswdResetToken", true);
            this.createEndState(pswdFlow, "pswdResetComplete");
            this.createTransitionForState(this.getTransitionableState(pswdFlow, "casPasswordUpdateSuccess"), "proceed", "pswdResetComplete");
            this.createEndState(flow, "redirectToLogin", "'login'", true);
            this.createTransitionForState(pswdResetSubFlowState, "pswdResetComplete", autoLogin ? "realSubmit" : "redirectToLogin");
            this.createDecisionState(flow, "checkDoChangePassword", "flowScope.doChangePassword == true", "casMustChangePassView", this.getTransitionableState(flow, "realSubmit").getTransition("success").getTargetStateId()).getEntryActionList().add(this.createEvaluateAction("flowScope.pswdChangePostLogin=true"));
            this.createTransitionForState(this.getTransitionableState(flow, "realSubmit"), "success", "checkDoChangePassword", true);
            this.createDecisionState(flow, "postLoginPswdChangeCheck", "flowScope.pswdChangePostLogin == true", this.getTransitionableState(flow, "showAuthenticationWarningMessages").getTransition("proceed").getTargetStateId(), autoLogin ? "realSubmit" : "redirectToLogin");
            this.createTransitionForState(this.getTransitionableState(flow, "casPasswordUpdateSuccess"), "proceed", "postLoginPswdChangeCheck");
        }
    }

    private void configurePasswordMustChangeForAuthnWarnings(Flow flow) {
        TransitionableState warningState = this.getTransitionableState(flow, "showAuthenticationWarningMessages");
        warningState.getEntryActionList().add(this.createEvaluateAction("flowScope.pswdChangePostLogin=true"));
        this.createTransitionForState(warningState, "changePassword", "casMustChangePassView");
    }

    /**
     * 配置修改密码校验流程
     * @param flow 流程
     * @param id 流程id
     */
    private void configurePasswordResetFlow(Flow flow, String id) {
        this.createFlowVariable(flow, "password", PasswordChangeBean.class);
        BinderConfiguration binder = this.createStateBinderConfiguration(CollectionUtils.wrapList(new String[]{"password", "confirmedPassword"}));
        ViewState viewState = this.createViewState(flow, id, id, binder);
        this.createStateModelBinding(viewState, "password", PasswordChangeBean.class);
        viewState.getEntryActionList().add(this.initPasswordChangeAction);
        Transition transition = this.createTransitionForState(viewState, "submit", "passwordChangeAction");
        transition.getAttributes().put("bind", Boolean.TRUE);
        transition.getAttributes().put("validate", Boolean.TRUE);
        this.createStateDefaultTransition(viewState, id);
        ActionState pswChangeAction = this.createActionState(flow, "passwordChangeAction", this.createEvaluateAction("passwordChangeAction"));
        pswChangeAction.getTransitionSet().add(this.createTransition("passwordUpdateSuccess", "casPasswordUpdateSuccess"));
        pswChangeAction.getTransitionSet().add(this.createTransition("error", id));
    }

}
