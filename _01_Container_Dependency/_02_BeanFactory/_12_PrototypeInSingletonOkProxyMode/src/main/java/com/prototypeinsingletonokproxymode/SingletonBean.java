package com.prototypeinsingletonokproxymode;

import org.springframework.beans.factory.annotation.Autowired;

public class SingletonBean {
    @Autowired
    private PrototypeBean prototypeBean;

    public long randomNumberFromPrototypeBean() {
        return getPrototypeBean().getRandomNumber();
    }

    PrototypeBean getPrototypeBean() {
        return new PrototypeBean();
    }
}
