package com.ds.surroundings.util;

import android.location.LocationManager;

public class Constants {

//    public static final int MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 2; // 2 minutes
    public static final int MIN_TIME_BETWEEN_UPDATES = 0; //as much as possible

    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

    public static final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    //    public static final String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

    // Google API Key
    public static final String API_KEY = "AIzaSyCyOGoP_BzPkjrqCtoVzLRuszEtIEsjHJ8";

    // Google Places search url's
    public static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    public static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";


    public static double DEFAULT_SEARCH_RADIUS = 1000d;

    public static String DEFAULT_TYPES_TO_SEARCH = "bank";

}
