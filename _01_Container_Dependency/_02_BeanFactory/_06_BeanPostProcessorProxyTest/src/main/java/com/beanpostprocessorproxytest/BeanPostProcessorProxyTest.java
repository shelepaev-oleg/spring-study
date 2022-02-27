package com.beanpostprocessorproxytest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring_2022_02_27_2027
 */
public class BeanPostProcessorProxyTest {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Quoter bean = context.getBean(Quoter.class);
        while (true) {
            Thread.sleep(150);
            bean.sayQuoter();
        }
    }
}
