package com.ds.surroundings.place.loader;

import android.location.Location;
import android.util.Log;

import com.ds.surroundings.http.factory.HttpRequestFactoryBuilder;
import com.ds.surroundings.place.PlaceDetails;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.util.Constants;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;

import java.io.IOException;

public class PlaceLoader {

    private HttpRequestFactory httpRequestFactory;

    public PlaceLoader() {
        this.httpRequestFactory = HttpRequestFactoryBuilder.getFactory();
    }

    public PlaceList getPlaces(Location location, double radius, String types) throws IOException {
        HttpRequest request = httpRequestFactory
                .buildGetRequest(new GenericUrl(Constants.PLACES_SEARCH_URL));

        request.getUrl().put("key", Constants.API_KEY);
        request.getUrl().put("location", location.getLatitude() + "," + location.getLongitude());
        request.getUrl().put("radius", radius); // in meters
        request.getUrl().put("sensor", "false");

        //TODO add types to request
//        if (types != null)
//            request.getUrl().put("types", types);
        Log.d("REQUEST", request.getUrl().toString());

        return request.execute().parseAs(PlaceList.class);
    }

    public PlaceDetails getPlaceDetails(String reference) throws IOException {
        HttpRequest request = httpRequestFactory
                .buildGetRequest(new GenericUrl(Constants.PLACES_DETAILS_URL));
        request.getUrl().put("key", Constants.API_KEY);
        request.getUrl().put("reference", reference);
        request.getUrl().put("sensor", "false");
        Log.d("REQUEST", request.getUrl().toString());

        return request.execute().parseAs(PlaceDetails.class);
    }
}
