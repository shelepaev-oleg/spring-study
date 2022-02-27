package com.proxykind;

import com.proxykind.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProxyTest {

    @Test
    public void jdkProxy() {
        User user = new User("Вася");
        IProxy proxy = new JdkProxy();
        Assertions.assertEquals("ВАСЯ", proxy.nameToUpperCase(user));
    }

    @Test
    public void cglib() {
        User user = new User("Вася");
        IProxy proxy = new Cglib();
        Assertions.assertEquals("ВАСЯ", proxy.nameToUpperCase(user));
    }

    @Test
    public void javassist() {
        User user = new User("Вася");
        IProxy proxy = new Javassist();
        Assertions.assertEquals("ВАСЯ", proxy.nameToUpperCase(user));
    }

    @Test
    public void byteBuddy() {
        User user = new User("Вася");
        IProxy proxy = new ByteBuddyTest();
        Assertions.assertEquals("ВАСЯ", proxy.nameToUpperCase(user));
    }
}
