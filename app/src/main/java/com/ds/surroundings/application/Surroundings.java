package com.ds.surroundings.application;

import android.app.Application;

import com.ds.surroundings.location.service.LocationService;
import com.ds.surroundings.location.service.impl.LocationServiceImpl;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;
import com.ds.surroundings.place.service.impl.PlaceServiceImpl;

public class Surroundings extends Application {

    private PlaceList placeList;
    private LocationService locationService;
    private PlaceService placeService;

    @Override
    public void onCreate() {
        super.onCreate();

        placeList = new PlaceList();
        placeService = new PlaceServiceImpl(this);
        locationService = new LocationServiceImpl(getApplicationContext(), placeService);
    }

    public PlaceList getObserver() {
        return placeList;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public PlaceService getPlaceService() {
        return placeService;
    }
}
