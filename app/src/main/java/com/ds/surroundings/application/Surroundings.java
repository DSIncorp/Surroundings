package com.ds.surroundings.application;

import android.app.Application;
import android.location.LocationListener;

import com.ds.surroundings.application.module.AndroidModule;
import com.ds.surroundings.application.module.ApplicationModule;
import com.ds.surroundings.location.listener.LocationListenerImpl;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;

import javax.inject.Inject;

public class Surroundings extends Application {

    private static ApplicationComponent applicationComponent;

    private PlaceList placeList;
    private LocationListener locationListener;
    private PlaceService placeService;

    @Inject
    public void setPlaceList(PlaceList placeList) {
        this.placeList = placeList;
    }

    @Inject
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .applicationModule(new ApplicationModule())
                .build();
        applicationComponent.inject(this);
        init();
    }

    public void init() {
        locationListener = new LocationListenerImpl(getApplicationContext(), placeList, placeService);
    }

    public PlaceList getObserver() {
        return placeList;
    }
}