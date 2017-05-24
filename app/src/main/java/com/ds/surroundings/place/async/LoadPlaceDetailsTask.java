package com.ds.surroundings.place.async;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.service.PlaceService;

public class LoadPlaceDetailsTask extends AsyncTask<Object, Object, Place> {

    private PlaceService placeService;
    private ProgressBar progressBar;

    public LoadPlaceDetailsTask(PlaceService placeService) {
        this.placeService = placeService;
    }

    public LoadPlaceDetailsTask(PlaceService placeService, ProgressBar progressBar) {
        this(placeService);
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
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

    @Override
    protected void onPostExecute(Place place) {
        super.onPostExecute(place);
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
