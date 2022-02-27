package com.beanpostprocessorproxytest;

/**
 * Контроллер для включения/отключения профилирования
 */
public class ProfilingController implements ProfilingControllerMBean {

    /**
     * true - профилирование включено
     */
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
