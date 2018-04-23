package com.xinchen.cas.auth;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

public class CustomWebflowConfigurer extends AbstractCasWebflowConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebflowConfigurer.class);

    public CustomWebflowConfigurer(FlowBuilderServices flowBuilderServices,
                                      FlowDefinitionRegistry flowDefinitionRegistry,
                                      ApplicationContext applicationContext,
                                      CasConfigurationProperties casProperties) {
        super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
    }

    @Override
    protected void doInitialize(){
        LOGGER.info("初始化自定义认证流程");
        final Flow flow = super.getLoginFlow();
        bindCredential(flow);
    }


    /**
     * 绑定输入信息
     *
     * @param flow
     */
    protected void bindCredential(Flow flow) {
        //重写绑定自定义credential
        createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, UsernamePasswordSysCredential.class);
        //登录页绑定新参数
        final ViewState state = (ViewState) flow.getState(CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM);
        final BinderConfiguration cfg = getViewStateBinderConfiguration(state);
        //由于用户名以及密码已经绑定，所以只需对新加系统参数绑定即可
        cfg.addBinding(new BinderConfiguration.Binding("system", null, false));
    }
}
