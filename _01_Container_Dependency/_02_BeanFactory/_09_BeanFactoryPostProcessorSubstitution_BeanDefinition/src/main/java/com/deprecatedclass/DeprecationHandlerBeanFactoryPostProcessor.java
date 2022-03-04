package com.deprecatedclass;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Подменяет {@link BeanDefinition} на основе значения аннотации {@link DeprecatedClass}
 */
public class DeprecationHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> beanClass = Class.forName(beanClassName);
                DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
                if (annotation != null) {
                    beanDefinition.setBeanClassName(annotation.newClass().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
