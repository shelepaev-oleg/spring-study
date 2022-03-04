package com.deprecatedclass;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.deprecatedclass")
public class Config {

    @Bean
    static DeprecationHandlerBeanFactoryPostProcessor deprecationHandlerBeanFactoryPostProcessor() {
        return new DeprecationHandlerBeanFactoryPostProcessor();
    }
}
