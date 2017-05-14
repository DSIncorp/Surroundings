package com.ds.surroundings.place.service.impl;

import android.location.Location;

import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.PlaceDetails;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.loader.PlaceLoader;
import com.ds.surroundings.place.service.PlaceService;

import java.io.IOException;

public class PlaceServiceImpl implements PlaceService {

    private PlaceList observablePlaceList;
    private PlaceLoader placeLoader;

    public PlaceServiceImpl(PlaceList observablePlaceList) {
        this.observablePlaceList = observablePlaceList;
        this.placeLoader = new PlaceLoader();
    }

    @Override
    public PlaceList getPlaces() {
        return observablePlaceList;
    }

    @Override
    public PlaceList getPlaces(Location location, double radius, String types) throws IOException {
        return placeLoader.getPlaces(location, radius, types);
    }

    @Override
    public Place getPlaceDetails(Place place) throws IOException {
        PlaceDetails details = placeLoader.getPlaceDetails(place.getReference());
        place.setDetails(details);
        return place;
    }

    @Override
    public void refreshPlacesList(PlaceList places) {
        observablePlaceList.setPlaces(places.getPlaces());
    }
}
