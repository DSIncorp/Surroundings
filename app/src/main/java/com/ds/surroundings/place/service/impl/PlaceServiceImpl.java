package com.ds.surroundings.place.service.impl;

import android.location.Location;

import com.ds.surroundings.application.Surroundings;
import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.async.LoadPlaceDetailsTask;
import com.ds.surroundings.place.async.LoadPlaceTask;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PlaceServiceImpl implements PlaceService {

    private Surroundings application;

    public PlaceServiceImpl(Surroundings application) {
        this.application = application;
    }

    @Override
    public PlaceList searchPlaces(Location location, double radius, String types) throws IOException, ExecutionException, InterruptedException {
        LoadPlaceTask loadPlaceTask = new LoadPlaceTask();
        return loadPlaceTask.execute(location, radius, types).get();

    }

    @Override
    public Place getPlaceDetails(Place place) throws ExecutionException, InterruptedException {
        LoadPlaceDetailsTask loadPlaceDetailsTask = new LoadPlaceDetailsTask();
        return loadPlaceDetailsTask.execute(place).get();
    }

    @Override
    public void refreshPlacesList(PlaceList places) {
        application.getObserver().setPlaces(places.getPlaces());
    }
}
