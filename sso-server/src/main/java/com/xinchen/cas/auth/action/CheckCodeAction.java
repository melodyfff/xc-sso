package com.xinchen.cas.auth.action;

import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/11 20:00
 */
public class CheckCodeAction extends AbstractAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCodeAction.class);

    private final static String ERROR_CODE = "checkCodeError";
    @Override
    protected Event doExecute(RequestContext requestContext) throws Exception {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
        String code = request.getParameter("code");
        LOGGER.info("服务器端-code:{}",request.getSession().getAttribute("code"));
        LOGGER.info("用户输入-code:{}",code);
        if (code.equals(request.getSession().getAttribute("code"))){
            return success();
        }else {
            return getError(requestContext);
        }
    }

    /**
     * 跳转到错误页
     * @param requestContext
     * @return
     */
    private Event getError(final RequestContext requestContext) {
        final MessageContext messageContext = requestContext.getMessageContext();
        messageContext.addMessage(new MessageBuilder().error().code(ERROR_CODE).build());
        return getEventFactorySupport().event(this, "error");
    }
}
