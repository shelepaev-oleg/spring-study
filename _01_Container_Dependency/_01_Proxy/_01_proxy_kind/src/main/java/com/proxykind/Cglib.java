package com.proxykind;

import com.proxykind.model.User;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class Cglib implements IProxy {

    @Override
    public String nameToUpperCase(final User user) {
        MethodInterceptor handler = (obj, method , args, proxy) -> {
            if (method.getName().equals("getName")){
                return ((String)proxy.invoke(user, args)).toUpperCase() ;
            }
            return proxy.invoke(user, args);
        };

        User userProxy = (User) Enhancer.create(User.class, handler);
        return userProxy.getName();
    }
}
