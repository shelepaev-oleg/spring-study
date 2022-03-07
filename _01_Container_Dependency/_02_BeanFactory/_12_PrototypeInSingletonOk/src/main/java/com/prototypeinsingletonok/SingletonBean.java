package com.prototypeinsingletonok;

public abstract class SingletonBean {
    public long randomNumberFromPrototypeBean() {
        return getPrototypeBean().getRandomNumber();
    }

    protected abstract PrototypeBean getPrototypeBean();
}
