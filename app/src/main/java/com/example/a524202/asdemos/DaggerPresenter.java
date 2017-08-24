package com.example.a524202.asdemos;

/**
 * Created by 524202 on 2017/8/7.
 */

public class DaggerPresenter {

    DaggerActivity activity;
    User user;

    public DaggerPresenter(DaggerActivity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    public void showUserName() {
        activity.showUserName(user.name);
    }

}
