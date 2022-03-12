package com.prototypeinsingletonbenchmark.wrong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class SingletonBean {
    /**
     * Использовать контекст внутри бизнес-бина - плохая практика
     */
    @Autowired
    ApplicationContext context;

    public long randomNumberFromPrototypeBean() {
        return context.getBean(PrototypeBean.class).getRandomNumber();
    }
}
