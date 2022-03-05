package com.propertyapplicationcontext;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QuoterImpl implements Quoter {

    private String message;

    public void setMessage(final String message) {
        this.message = message;
    }

    public QuoterImpl() {
        System.out.println("Phase_1");
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase_2");
    }

    @PostProxy
    @Override
    public void sayQuoter() {
        System.out.println(message);
        System.out.println("Phase_3");
    }
}
