package com.proxykind;

import com.proxykind.model.User;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyTest implements IProxy {

    @Override
    public String nameToUpperCase(final User user) {
        try {
            User userProxy = new ByteBuddy()
                    .subclass(User.class)
                    .method(ElementMatchers.named("getName"))
                    .intercept(MethodDelegation.to(new ByteBuddyInterceptor(user)))
                    .make()
                    .load(User.class.getClassLoader())
                    .getLoaded()
                    .newInstance();
            return userProxy.getName();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
