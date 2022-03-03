package com.custombeanfactorypostprocessor;

public class ServiceImpl implements Service {
    private String str;
    @Override
    public String getStr() {
        return str;
    }
    @Override
    public void setStr(final String str) {
        this.str = str;
    }
}
