package com.deprecatedclass;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Пример того как можно сделать неочевидный код
 *
 * Ожидаем:
 * Phase_1
 * Phase_2
 * Новый класс
 *
 * А не:
 * Phase_1
 * Phase_2
 * Phase_3
 */
public class BeanFactoryPostProcessorSubstitutionBeanDefinition {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean(Quoter.class).sayQuoter();
    }
}
