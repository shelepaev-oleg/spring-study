package com.prototypeinsingletonwrong;

import java.util.UUID;

public class PrototypeBean {
    private long randomNumber;

    public PrototypeBean() {
        this.randomNumber
                = UUID.randomUUID().getMostSignificantBits();
    }

    public long getRandomNumber() {
        return randomNumber;
    }
}
