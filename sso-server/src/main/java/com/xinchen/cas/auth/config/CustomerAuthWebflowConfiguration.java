package com.xinchen.cas.auth.config;

import com.xinchen.cas.auth.CustomWebflowConfigurer;
import com.xinchen.cas.auth.entity.OK;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.config.FlowDefinitionRegistryBuilder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 *
 */
@Configuration("customerAuthWebflowConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CustomerAuthWebflowConfiguration {
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("logoutFlowRegistry")
    private FlowDefinitionRegistry logoutFlowRegistry;
    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowRegistry;

    @Autowired
    @Qualifier("builder")
    private FlowBuilderServices flowBuilderServices;

//    @Autowired
//    @Qualifier("ticketRegistryCacheManager")
//    private CacheManager cacheManager;

    @Autowired
    private ApplicationContext applicationContext;

//    @Autowired
//    private FlowBuilderServices flowBuilderServices;

    @Bean
    public CasWebflowConfigurer customWebflowConfigurer() {
        final CustomWebflowConfigurer c = new CustomWebflowConfigurer(flowBuilderServices, loginFlowRegistry
                ,applicationContext,casProperties);
        c.setLogoutFlowDefinitionRegistry(logoutFlowRegistry);
        c.initialize();
        return c;
    }
}
