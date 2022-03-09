package com.scopetest;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

/**
 * spring_2022_03_07_2007
 */
@Configuration
@ComponentScan(basePackages = "com.scopetest")
public class Config {

    @Bean
    @Scope("periodical")
    public Color color() {
        return new Color(
                new Random().nextInt(255),
                new Random().nextInt(255),
                new Random().nextInt(255));
    }

    @Bean
    public ColorFrame colorFrame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while(true) {
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(500);
        }
    }
}
