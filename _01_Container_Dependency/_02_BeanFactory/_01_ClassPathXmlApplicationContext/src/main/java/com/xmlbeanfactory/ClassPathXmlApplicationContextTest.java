package com.xmlbeanfactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * #spring_2022_02_17_0809
 */
public class ClassPathXmlApplicationContextTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        Country countryBean = (Country) ac.getBean("country");
        String name = countryBean.getName();
        System.out.println("Country name: "+name);
        long population = countryBean.getPopulation();
        System.out.println("Country population: "+population);
        ac.close();
    }
}
