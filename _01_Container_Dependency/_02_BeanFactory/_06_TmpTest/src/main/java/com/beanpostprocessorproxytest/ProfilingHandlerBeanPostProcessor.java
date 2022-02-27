package com.beanpostprocessorproxytest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link BeanPostProcessor} для настройки аннотации {@link Profiling}
 */
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    /**
     * Мапа для временного сохранения,
     * чтоб получать данные сохраненные в {@link ProfilingHandlerBeanPostProcessor#postProcessBeforeInitialization(Object, String)}
     * из метода {@link ProfilingHandlerBeanPostProcessor#postProcessAfterInitialization(Object, String)}
     */
    private Map<String, Class> map = new HashMap<>();

    /**
     * {@link ProfilingController} позволяет управлять включением/отключением профилирования
     */
    private ProfilingController controller = new ProfilingController();

    /**
     * Настройка {@link MBeanServer}, не связано со Spring
     */
    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            // Создает объект из нового класса
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("Start profiling");
                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println(after - before);
                        System.out.println("End profiling");
                        return retVal;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
            });
        }
        return bean;
    }
}
