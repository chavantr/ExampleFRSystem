package com.mywings.foodrecommended.process;

import com.mywings.foodrecommended.models.User;

public class UserInfoHolder {

    private User user;

    public static UserInfoHolder getInstance() {
        return UserInfoHolderHelper.INSTANCE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static class UserInfoHolderHelper {

        static UserInfoHolder INSTANCE = new UserInfoHolder();

    }

}
