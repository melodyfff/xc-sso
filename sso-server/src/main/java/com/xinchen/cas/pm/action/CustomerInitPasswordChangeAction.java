package com.xinchen.cas.pm.action;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/12 22:55
 */
public class CustomerInitPasswordChangeAction extends AbstractAction {
    @Autowired
    private CasConfigurationProperties casProperties;

    public CustomerInitPasswordChangeAction() {
    }

    @Override
    protected Event doExecute(RequestContext requestContext) {
        requestContext.getFlowScope().put("policyPattern", this.casProperties.getAuthn().getPm().getPolicyPattern());
        return null;
    }
}
