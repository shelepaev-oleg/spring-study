package com.beanpostprocessortest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyBean {

    @InjectRandomInt
    private int value1;

    @InjectRandomInt(min = 100, max = 200)
    private int value2;

    private int value3;

    @Override
    public String toString() {
        return "MyBean{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                ", value3=" + value3 +
                '}';
    }

    public MyBean() {
        System.out.println("Phase 1");
    }

    /**
     * https://www.baeldung.com/spring-postconstruct-predestroy
     */
    @PostConstruct
    public void init() {
        System.out.println("Phase 2");
    }
}
