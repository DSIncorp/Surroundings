package com.ds.surroundings.place.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.text.Html;

import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;

public class LoadPlaceTask extends AsyncTask<Object, Object, PlaceList> {

    private PlaceService placeService;
    private Context context;
    private ProgressDialog progressDialog;

    public LoadPlaceTask(PlaceService placeService, Context context) {
        this.placeService = placeService;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
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

    @Override
    protected void onPostExecute(PlaceList placeList) {
        super.onPostExecute(placeList);
        progressDialog.dismiss();
    }
}
