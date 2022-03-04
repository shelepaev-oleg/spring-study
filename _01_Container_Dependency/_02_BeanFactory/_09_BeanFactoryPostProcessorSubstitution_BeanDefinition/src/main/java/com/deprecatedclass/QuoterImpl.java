package com.deprecatedclass;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@DeprecatedClass(newClass = QuoterImplNew.class)
public class QuoterImpl implements Quoter {

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
        System.out.println("Phase_3");
    }
}
