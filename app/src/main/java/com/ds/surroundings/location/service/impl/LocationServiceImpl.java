package com.ds.surroundings.location.service.impl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ds.surroundings.location.service.LocationService;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;
import com.ds.surroundings.util.Constants;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("ResourceType")
public class LocationServiceImpl extends Service implements LocationService, LocationListener {

    private final Context context;

    private Location currentLocation;
    private LocationManager locationManager;
    private PlaceService placeService;

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;

    public LocationServiceImpl(Context context, PlaceService placeService) {
        this.context = context;
        this.placeService = placeService;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        registerListener();
        obtainLastKnownLocation();
        Log.d("## Current Location :: ", currentLocation == null ? "<NULL>" : currentLocation.toString());
        getPlaces();
    }

    private void getPlaces() {
        if (currentLocation != null) {
            PlaceList places = null;
            try {
                places = placeService.searchPlaces(currentLocation, 10, "");
                Log.d("## LocationService:Places :: ", places.getStatus() + " " + places.getPlaces());
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (places != null) {
                Log.d("## LocationService", "refreshing!");
                placeService.refreshPlacesList(places);
            }
        }
    }

    @Override
    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("##LOCATION::", location.toString());
        if (isBetterLocation(location)) {
            currentLocation = location;
            getPlaces();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //TODO register for updates.
    }

    @Override
    public void onProviderDisabled(String provider) {
        //TODO show alert dialog.
    }

    private void registerListener() {
        locationManager.requestLocationUpdates(Constants.LOCATION_PROVIDER,
                Constants.MIN_TIME_BETWEEN_UPDATES, Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                Constants.MIN_TIME_BETWEEN_UPDATES, Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    private void obtainLastKnownLocation() {
        currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location lastKnownGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (isBetterLocation(lastKnownGpsLocation)) {
            currentLocation = lastKnownGpsLocation;
        }
    }

    private boolean isBetterLocation(Location location) {
        if (location == null) {
            return false;
        }
        if (currentLocation == null) {
            currentLocation = location;
            return true;
        }

        long timeDelta = location.getTime() - currentLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > Constants.MIN_TIME_BETWEEN_UPDATES;
        boolean isSignificantlyOlder = timeDelta < (-Constants.MIN_TIME_BETWEEN_UPDATES);
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int) (location.getAccuracy() - currentLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentLocation.getProvider());

        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
