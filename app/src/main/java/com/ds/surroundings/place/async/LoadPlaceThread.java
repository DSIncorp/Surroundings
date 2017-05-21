package com.ds.surroundings.place.async;

import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;

import java.util.concurrent.Callable;

import static com.ds.surroundings.settings.Settings.getCurrentLocation;
import static com.ds.surroundings.settings.Settings.getSearchRadius;
import static com.ds.surroundings.settings.Settings.getTypesToSearch;

public class LoadPlaceThread implements Callable<PlaceList> {

    private PlaceService placeService;

    public LoadPlaceThread(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Override
    public PlaceList call() throws Exception {
        return placeService.getPlaces(getCurrentLocation(), getSearchRadius(), getTypesToSearch());
    }
}
