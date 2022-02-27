package com.factorybeantest;

import org.springframework.beans.factory.FactoryBean;

import java.util.Random;

public class ColorFactory implements FactoryBean<Color> {

    @Override
    public Color getObject() throws Exception {
        Random random = new Random();
        Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        return color;
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
