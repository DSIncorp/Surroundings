package com.ds.surroundings.place.async;

import android.location.Location;
import android.os.AsyncTask;

import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.loader.PlaceLoader;

import java.io.IOException;

public class LoadPlaceTask extends AsyncTask<Object, Object, PlaceList> {

    private PlaceLoader placeLoader;

    public LoadPlaceTask() {
        this.placeLoader = new PlaceLoader();
    }

    @Override
    protected PlaceList doInBackground(Object... params) {
        Location location = (Location) params[0];
        Double radius = (Double) params[1];
        String types = (String) params[2];

        try {
            return placeLoader.getPlaces(location, radius, types);
        } catch (IOException e) {
            //TODO handle
            e.printStackTrace();
        }
        return null;
    }
}
