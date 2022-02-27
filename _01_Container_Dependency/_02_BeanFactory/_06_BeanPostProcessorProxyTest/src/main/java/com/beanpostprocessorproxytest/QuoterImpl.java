package com.beanpostprocessorproxytest;

@Profiling
public class QuoterImpl implements Quoter {
    private String message;
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public void sayQuoter() {
        System.out.println(message);
    }
}
