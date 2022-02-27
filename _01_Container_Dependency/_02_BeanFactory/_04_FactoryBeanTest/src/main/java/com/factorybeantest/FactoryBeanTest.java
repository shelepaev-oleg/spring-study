package com.factorybeantest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * #spring_2022_02_24_0744
 */
public class FactoryBeanTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        Color colorBean = (Color) ac.getBean("colorFactory");
        System.out.println(colorBean);
        ac.close();
    }
}
