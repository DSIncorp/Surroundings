package com.ds.surroundings.place.async;

import android.os.AsyncTask;

import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.service.PlaceService;

public class LoadPlaceDetailsTask extends AsyncTask<Object, Object, Place> {

    private PlaceService placeService;

    public LoadPlaceDetailsTask(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Override
    protected Place doInBackground(Object... params) {
        try {
            return placeService.getPlaceDetails((Place) params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
