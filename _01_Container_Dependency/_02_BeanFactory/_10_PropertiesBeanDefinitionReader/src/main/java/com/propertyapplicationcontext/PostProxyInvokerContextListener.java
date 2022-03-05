package com.propertyapplicationcontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            // Получаем BeanDefinition
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                // Получаем класс Bean-а
                Class<?> originalClass = Class.forName(originalClassName);
                // Получаем все методы класса Bean-а
                Method[] methods = originalClass.getDeclaredMethods();
                for (Method method : methods) {
                    // Находим в классе Bean, помеченный аннотацией PostProxy
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        // Получаем Bean
                        Object bean = context.getBean(name);
                        // Получаем метод
                        Method currMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        // Вызываем метод на 3-ьей фазе
                        currMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
