package com.example.a524202.asdemos;


import dagger.Component;

/**
 * Created by 524202 on 2017/8/7.
 */
@ActivityScope
@Component(modules = ActivityModule.class,dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(DaggerActivity activity);
}
