package com.beanpostprocessorproxytest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.beanpostprocessorproxytest")
public class Config {

    @Bean
    public QuoterImpl createTerminatorQuoter() {
        QuoterImpl quoterImpl = new QuoterImpl();
        quoterImpl.setMessage("I'll be back");
        return quoterImpl;
    }

    @Bean
    public ProfilingHandlerBeanPostProcessor createProfilingHandlerBeanPostProcessor() throws Exception {
        return new ProfilingHandlerBeanPostProcessor();
    }
}
