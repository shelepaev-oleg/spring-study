package com.prototypeinsingletonbenchmark.ok;

public abstract class SingletonBean {
    public long randomNumberFromPrototypeBean() {
        return getPrototypeBean().getRandomNumber();
    }

    protected abstract PrototypeBean getPrototypeBean();
}
