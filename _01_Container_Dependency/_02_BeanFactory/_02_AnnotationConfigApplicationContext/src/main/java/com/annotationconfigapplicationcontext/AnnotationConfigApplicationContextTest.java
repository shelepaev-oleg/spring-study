package com.annotationconfigapplicationcontext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * #spring_2022_02_18_0809
 */
public class AnnotationConfigApplicationContextTest {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(LessonsConfiguration.class);
        GreetingService greetingService = context.getBean(GreetingService.class);
        System.out.println(greetingService.sayGreeting());
    }
}
