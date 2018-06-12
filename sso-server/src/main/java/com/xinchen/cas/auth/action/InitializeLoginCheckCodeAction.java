package com.xinchen.cas.auth.action;

import org.apereo.cas.web.flow.InitializeLoginAction;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/11 18:58
 */
public class InitializeLoginCheckCodeAction extends AbstractAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeLoginCheckCodeAction.class);
    @Override
    protected Event doExecute(RequestContext requestContext) throws Exception {
        LOGGER.debug("Initialized check code sequence");
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);

        // 针对张三用户进行验证码校验
        if ("zhangsan".equals(request.getParameter("username"))){
            request.getSession().setAttribute("code","01");
            return new Event(this, "needCheck");
        }


        return this.success();
    }
}
