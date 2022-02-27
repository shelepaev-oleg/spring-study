package com.proxykind.model;

public class User implements IUser {
    private String name;
    public User() {
    }
    public User(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
