package com.xinchen.cas.auth.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xinchen.cas.auth.entity.OK;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/18 22:52
 */
@Configuration("dataSourceConfiguration")
//@EnableAutoConfiguration
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class DataSourceConfiguration {
    @Bean("myok")
    public OK ok(){
        return new OK();
    }
}
