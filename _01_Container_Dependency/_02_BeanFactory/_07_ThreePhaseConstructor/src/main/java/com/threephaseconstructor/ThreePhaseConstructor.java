package com.threephaseconstructor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring_2022_03_01_0749
 */
public class ThreePhaseConstructor {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}
