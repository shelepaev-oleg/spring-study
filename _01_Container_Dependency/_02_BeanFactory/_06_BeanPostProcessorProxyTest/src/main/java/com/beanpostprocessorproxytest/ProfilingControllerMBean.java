package com.beanpostprocessorproxytest;

/**
 * Конвенция, которая позволяет предоставлять методы для JMX Concole
 * требует в имени интерфейса использовать суффикс MBean
 */
public interface ProfilingControllerMBean {
    void setEnabled(boolean enabled);
}
