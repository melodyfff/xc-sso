package com.xinchen.cas.auth.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xinchen.cas.auth.entity.OK;
import com.xinchen.cas.auth.handler.UsernamePasswordSystemAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 寄存器
 */
@Configuration("customAuthenticationEventExecutionPlanConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomAuthenticationEventExecutionPlanConfiguration implements AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    @Qualifier("myok")
    private OK ok;

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

//    @Autowired
//    @Qualifier("jdbcPrincipalFactory")
//    public PrincipalFactory jdbcPrincipalFactory;

    /**
     * 注册验证器
     *
     * @return
     */
    @Bean
    public AuthenticationHandler customAuthenticationHandler() {
        //优先验证
        return new UsernamePasswordSystemAuthenticationHandler(UsernamePasswordSystemAuthenticationHandler.class.getSimpleName(),
                servicesManager, new DefaultPrincipalFactory(), 1);
    }

    //注册自定义认证器
    @Override
    public void configureAuthenticationExecutionPlan(final AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(customAuthenticationHandler());
    }
}
