package com.proxykind;

import com.proxykind.model.User;

public class ByteBuddyInterceptor {
    private User user;

    public ByteBuddyInterceptor(User user) {
        this.user = user;
    }

    public String getName() {
        return user.getName().toUpperCase();
    }
}
