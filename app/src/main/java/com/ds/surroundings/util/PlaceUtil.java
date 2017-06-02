package com.ds.surroundings.util;

import android.location.Location;
import android.support.annotation.NonNull;

import com.ds.surroundings.place.Geometry;
import com.ds.surroundings.settings.Settings;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.round;
import static java.lang.Math.toDegrees;

public final class PlaceUtil {

    private PlaceUtil() {
        throw new IllegalStateException("Class is not designed for instantiation.");
    }

    public static float getPlaceAzimut(Geometry.Location placeLocation, Location userLocation) {

        double deltaLatitude = abs(placeLocation.getLatitude() - userLocation.getLatitude());
        double deltaLongitude = abs(placeLocation.getLongitude() - userLocation.getLongitude());

        double tgAlpha = deltaLongitude / deltaLatitude;
        float azimut = round(toDegrees(atan(tgAlpha)));

        return getFullAngle(placeLocation, userLocation, azimut);
    }

    private static float getFullAngle(Geometry.Location placeLocation, Location userLocation, float azimut) {
        if (isFirstQuarter(placeLocation, userLocation)) {
            return azimut;
        }
        if (isSecondQuarter(placeLocation, userLocation)) {
            return azimut + 270;
        }
        if (isThirdQuarter(placeLocation, userLocation)) {
            return azimut + 180;
        } else if (isFourthQuarter(placeLocation, userLocation)) {
            return azimut + 90;
        }
        return azimut;
    }

    private static boolean isFirstQuarter(Geometry.Location placeLocation, Location userLocation) {
        return placeLocation.getLongitude() >= userLocation.getLongitude() &&
                placeLocation.getLatitude() >= userLocation.getLatitude();
    }

    private static boolean isSecondQuarter(Geometry.Location placeLocation, Location userLocation) {
        return placeLocation.getLongitude() <= userLocation.getLongitude() &&
                placeLocation.getLatitude() >= userLocation.getLatitude();
    }

    private static boolean isThirdQuarter(Geometry.Location placeLocation, Location userLocation) {
        return placeLocation.getLongitude() <= userLocation.getLongitude() &&
                placeLocation.getLatitude() <= userLocation.getLatitude();
    }

    private static boolean isFourthQuarter(Geometry.Location placeLocation, Location userLocation) {
        return placeLocation.getLongitude() >= userLocation.getLongitude() &&
                placeLocation.getLatitude() <= userLocation.getLatitude();
    }

    public static double getComparativeRange(Geometry.Location placeLocation, Location userLocation) {

        final int R = 6371;

        double latDistance = Math.toRadians(userLocation.getLatitude() - placeLocation.getLatitude());
        double lonDistance = Math.toRadians(userLocation.getLongitude() - placeLocation.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(placeLocation.getLatitude())) * Math.cos(Math.toRadians(userLocation.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        return distance / Settings.getSearchRadius();
    }

    public static String getButtonText(String placeName){
        return placeName.length() > 18 ? getAbbreviation(placeName) : placeName;
    }

    @NonNull
    private static String getAbbreviation(String placeName) {
        return placeName.substring(0, 14) + "...";
    }

}
