package com.ds.surroundings.location.listener;

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

import com.ds.surroundings.place.async.LoadPlaceThread;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;
import com.ds.surroundings.util.Constants;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.ds.surroundings.settings.Settings.getCurrentLocation;
import static com.ds.surroundings.settings.Settings.setCurrentLocation;

@SuppressWarnings("ResourceType")
public class LocationListenerImpl extends Service implements LocationListener {

    private LocationManager locationManager;
    private PlaceList observablePlaceList;
    private PlaceService placeService;

    //TODO what the fucking flags
    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    public LocationListenerImpl(Context context, PlaceList observablePlaceList, PlaceService placeService) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.observablePlaceList = observablePlaceList;
        this.placeService = placeService;
        registerListener();
        obtainLastKnownLocation();
        Log.d("## Current Location :: ", getCurrentLocation() == null ? "<NULL>" :
                getCurrentLocation().toString());
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("##LOCATION::", location.toString());
        if (observablePlaceList == null || observablePlaceList.getPlaces() == null ||
                observablePlaceList.getPlaces().size() == 0 || isBetterLocation(location)) {
            Log.d("##LOCATION IS BETTER:", "!");
            setCurrentLocation(location);
            setPlacesToContext();
        }
    }

    private void setPlacesToContext() {
        if (getCurrentLocation() != null) {
            PlaceList placeList = getPlaceList();
            if (placeList != null) {
                observablePlaceList.setPlaces(placeList.getPlaces());
                Log.d("## LocationService", ":Places :: " + placeList.getStatus() + " " + placeList.getPlaces());
                Log.d("## LocationService", "refreshing!");
            }
        }
    }

    private PlaceList getPlaceList() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        LoadPlaceThread loadPlaceThread = new LoadPlaceThread(placeService);
        Future<PlaceList> placeListFuture = executorService.submit(loadPlaceThread);
        try {
            return placeListFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("##LocationService Error", e.getMessage());
            return null;
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                Constants.MIN_TIME_BETWEEN_UPDATES, Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                Constants.MIN_TIME_BETWEEN_UPDATES, Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    private void obtainLastKnownLocation() {
        setCurrentLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        Location lastKnownLocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (isBetterLocation(lastKnownLocationGps)) {
            setCurrentLocation(lastKnownLocationGps);
        }
    }

    private boolean isBetterLocation(Location location) {
        if (location == null) {
            return false;
        }
        if (getCurrentLocation() == null) {
            setCurrentLocation(location);
            return true;
        }

        long timeDelta = location.getTime() - getCurrentLocation().getTime();
        boolean isSignificantlyNewer = timeDelta > Constants.MIN_TIME_BETWEEN_UPDATES;
        boolean isSignificantlyOlder = timeDelta < (-Constants.MIN_TIME_BETWEEN_UPDATES);
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int) (location.getAccuracy() - getCurrentLocation().getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                getCurrentLocation().getProvider());

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
