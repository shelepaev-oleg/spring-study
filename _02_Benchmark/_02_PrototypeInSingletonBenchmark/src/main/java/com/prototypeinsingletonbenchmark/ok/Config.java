package com.prototypeinsingletonbenchmark.ok;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {

    @Bean
    public SingletonBean getSingletonBean() {
        return new SingletonBean() {

            @Override
            protected PrototypeBean getPrototypeBean() {
                return prototypeBean();
            }
        };
    }

    @Bean
    @Scope("prototype")
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }
}
