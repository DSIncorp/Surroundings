package com.ds.surroundings.settings;

import android.location.Location;

import static com.ds.surroundings.util.Constants.DEFAULT_SEARCH_RADIUS;
import static com.ds.surroundings.util.Constants.DEFAULT_TYPES_TO_SEARCH;

public final class Settings {

    private static double searchRadius;

    private static String typesToSearch;

    private static Location currentLocation;

    private Settings() {
        throw new IllegalStateException("calss is not designed for instantiation");
    }

    public static double getSearchRadius() {
        return searchRadius != 0d ? searchRadius : DEFAULT_SEARCH_RADIUS;
    }

    public static void setSearchRadius(double searchRadius) {
        Settings.searchRadius = searchRadius;
    }

    public static String getTypesToSearch() {
        return typesToSearch != null ? typesToSearch : DEFAULT_TYPES_TO_SEARCH;
    }

    public static void setTypesToSearch(String typesToSearch) {
        Settings.typesToSearch = typesToSearch;
    }

    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public static void setCurrentLocation(Location currentLocation) {
        Settings.currentLocation = currentLocation;
    }
}
