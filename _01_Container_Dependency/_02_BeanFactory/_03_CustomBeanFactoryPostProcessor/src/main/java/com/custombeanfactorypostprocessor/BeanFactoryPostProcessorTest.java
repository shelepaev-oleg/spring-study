package com.custombeanfactorypostprocessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * #spring_2022_02_23_1022
 */
public class BeanFactoryPostProcessorTest {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Service service = context.getBean(Service.class);
        System.out.println(service.getStr());
    }
}
