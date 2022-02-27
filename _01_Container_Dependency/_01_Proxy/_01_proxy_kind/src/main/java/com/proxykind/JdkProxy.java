package com.proxykind;

import com.proxykind.model.IUser;
import com.proxykind.model.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * JDK proxy
 * #spring_2022_02_11_0715
 */
public class JdkProxy implements IProxy {

    @Override
    public String nameToUpperCase(final User user) {
        InvocationHandler handler = (proxy, method, args) -> {
            if (method.getName().equals("getName")){
                return ((String)method.invoke(user, args)).toUpperCase();
            }
            return method.invoke(user, args);
        };

        IUser userProxy = (IUser) Proxy.newProxyInstance(user.getClass().getClassLoader(), User.class.getInterfaces(), handler);
        return userProxy.getName();
    }
}
