package com.ds.surroundings.place.service;

import android.location.Location;

import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.container.PlaceList;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface PlaceService {

    PlaceList getPlaces();

    PlaceList getPlaces(Location location, double radius, String types) throws IOException, ExecutionException, InterruptedException;

    Place getPlaceDetails(Place place) throws ExecutionException, InterruptedException, IOException;

    void refreshPlacesList(PlaceList places);
}
