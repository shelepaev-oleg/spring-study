package com.threephaseconstructor;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QuoterImpl implements Quoter {

    public QuoterImpl() {
        System.out.println("Phase_1");
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase_2");
    }

    @PostProxy
    public void sayQuoter() {
        System.out.println("Phase_3");
    }
}
