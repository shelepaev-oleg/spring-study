package com.proxykind;

import com.proxykind.model.User;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class Javassist implements IProxy {

    @Override
    public String nameToUpperCase(final User user) {
        try {
            MethodHandler handler = (self, overridden, forwarder, args) -> {
                if (overridden.getName().equals("getName")) {
                    return ((String) overridden.invoke(user, args)).toUpperCase();
                }
                return overridden.invoke(user, args);
            };

            ProxyFactory factory = new ProxyFactory();
            factory.setSuperclass(User.class);
            Object instance = factory.createClass().newInstance();
            ((ProxyObject) instance).setHandler(handler);

            User userProxy = (User) instance;
            return userProxy.getName();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
