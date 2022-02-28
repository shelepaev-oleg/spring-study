package com.threephaseconstructor;

import javax.annotation.PostConstruct;

public class QuoterImpl implements Quoter {
    private String message;

    public QuoterImpl() {
        System.out.println("Phase_1");
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase_2");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    @PostProxy
    public void sayQuoter() {
        System.out.println("Phase_3");
        System.out.println(message);
    }
}
