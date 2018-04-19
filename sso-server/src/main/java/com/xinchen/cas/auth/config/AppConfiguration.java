package com.xinchen.cas.auth.config;

import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.ImportResource;

/**
 * 加载环境配置
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/18 22:52
 */
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
@ImportResource(locations={"classpath:deployerConfigContext.xml"})
public class AppConfiguration {
}
