package com.beanpostprocessortest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring_2022_02_26_0807
 */
public class BeanPostProcessorTest {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.beanpostprocessortest");
        MyBean myBean = context.getBean(MyBean.class);
        System.out.println(myBean);
    }
}
