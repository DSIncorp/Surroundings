package com.ds.surroundings.place.async;

import android.os.AsyncTask;

import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.PlaceDetails;
import com.ds.surroundings.place.loader.PlaceLoader;

import java.io.IOException;

public class LoadPlaceDetailsTask extends AsyncTask<Object, Object, Place> {

    private PlaceLoader placeLoader;

    public LoadPlaceDetailsTask() {
        this.placeLoader = new PlaceLoader();
    }

    @Override
    protected Place doInBackground(Object... params) {
        Place currentPlace = (Place) params[0];
        PlaceDetails details = null;
        try {
            details = placeLoader.getPlaceDetails(currentPlace.getReference());
        } catch (IOException e) {
            //TODO handle
            e.printStackTrace();
        }
        currentPlace.setDetails(details);
        return currentPlace;
    }
}
