package com.ds.surroundings.application.module;

import com.ds.surroundings.camera.service.CameraService;
import com.ds.surroundings.camera.service.impl.CameraServiceImpl;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;
import com.ds.surroundings.place.service.impl.PlaceServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public PlaceList getPlaceList() {
        return new PlaceList();
    }

    @Provides
    @Singleton
    public PlaceService getPlaceService(PlaceList placeList) {
        return new PlaceServiceImpl(placeList);
    }

    @Provides
    @Singleton
    public CameraService getCameraService() {
        return new CameraServiceImpl();
    }

}
