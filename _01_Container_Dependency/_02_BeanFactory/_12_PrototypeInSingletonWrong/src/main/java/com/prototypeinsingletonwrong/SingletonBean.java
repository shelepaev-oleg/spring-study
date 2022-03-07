package com.prototypeinsingletonwrong;

import org.springframework.beans.factory.annotation.Autowired;

public class SingletonBean {
    @Autowired
    PrototypeBean prototypeBean;

    public long randomNumberFromPrototypeBean() {
        return prototypeBean.getRandomNumber();
    }
}
