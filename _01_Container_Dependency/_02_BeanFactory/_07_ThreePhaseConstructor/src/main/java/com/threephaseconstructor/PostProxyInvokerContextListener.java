package com.threephaseconstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> originalClass = Class.forName(originalClassName);
                Method[] methods = originalClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        // Вытащим бин, на основе которого построен Proxy
                        Object bean = context.getBean(name);
                        Method currMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
