package com.threephaseconstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.threephaseconstructor")
public class Config {

    @Bean
    public QuoterImpl createQuoter() {
        QuoterImpl quoterImpl = new QuoterImpl();
        quoterImpl.setMessage("I'll be back");
        return quoterImpl;
    }

    @Bean
    public PostProxyInvokerContextListener getPostProxyInvokerContextListener(){
        return new PostProxyInvokerContextListener();
    }
}
