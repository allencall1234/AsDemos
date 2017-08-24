package com.example.a524202.asdemos;

import android.app.Application;

/**
 * Created by 524202 on 2017/8/9.
 */

public class MyApplication extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
