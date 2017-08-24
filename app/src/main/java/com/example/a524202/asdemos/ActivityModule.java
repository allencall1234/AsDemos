package com.example.a524202.asdemos;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 524202 on 2017/8/7.
 */

@Module
public class ActivityModule {

    private DaggerActivity activity;

    public ActivityModule(DaggerActivity activity) {
        this.activity = activity;
    }

    @Provides
    public DaggerActivity provideActivity() {
        return activity;
    }

//    @Provides
//    public User provideUser() {
//        return new User("User from ActivityModule");
//    }

    @Provides
    public DaggerPresenter provideDaggerPresenter(DaggerActivity activity, User user) {
        return new DaggerPresenter(activity, user);
    }
}
