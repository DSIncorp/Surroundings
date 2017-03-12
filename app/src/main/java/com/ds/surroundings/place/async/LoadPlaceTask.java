package com.ds.surroundings.place.async;

import android.location.Location;
import android.os.AsyncTask;

import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;

public class LoadPlaceTask extends AsyncTask<Object, Object, PlaceList> {

    private PlaceService placeService;

    public LoadPlaceTask(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Override
    protected PlaceList doInBackground(Object... params) {
        Location location = (Location) params[0];
        Double radius = (Double) params[1];
        String types = (String) params[2];

        PlaceList places;
        try {
            places = placeService.getPlaces(location, radius, types);
            placeService.refreshPlacesList(places);
            return places;
        } catch (Exception e) {
            //TODO handle
            e.printStackTrace();
        }
        return null;
    }
}
