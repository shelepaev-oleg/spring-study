package com.threephaseconstructor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ThreePhaseConstructor {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Quoter bean = context.getBean(Quoter.class);
        bean.sayQuoter();
    }
}
