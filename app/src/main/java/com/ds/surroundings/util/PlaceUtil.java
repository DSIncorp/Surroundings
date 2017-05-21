package com.ds.surroundings.util;

import android.location.Location;

import com.ds.surroundings.place.Geometry;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.round;
import static java.lang.Math.toDegrees;

public class PlaceUtil {

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
        }
        else if (isFourthQuarter(placeLocation, userLocation)) {
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
}
