package com.ds.surroundings.application;

import com.ds.surroundings.activity.MainActivity;
import com.ds.surroundings.application.module.AndroidModule;
import com.ds.surroundings.application.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AndroidModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(Surroundings surroundings);


}
