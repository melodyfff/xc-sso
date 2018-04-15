package com.xinchen.cas.sso.client.demo;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean matches(String url) {
        logger.debug("访问路径：" + url);
        return url.contains("zhangsan.jsp");
    }

    @Override
    public void setPattern(String pattern) {

    }
}
