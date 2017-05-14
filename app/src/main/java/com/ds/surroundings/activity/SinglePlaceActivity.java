package com.ds.surroundings.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.ds.surroundings.R;
import com.ds.surroundings.place.Place;
import com.ds.surroundings.settings.Settings;
import com.ds.surroundings.util.OnMapAndViewReadyListener;
import com.ds.surroundings.util.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SinglePlaceActivity extends FragmentActivity implements
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_place);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        new OnMapAndViewReadyListener(mapFragment, this);
    }

    private Place getPlace() {
        Intent intent = getIntent();
        return (Place) intent.getSerializableExtra("place");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
        addPlaceMarker();

    }

    private void addPlaceMarker() {
        Place currentPlace = getPlace();
        LatLng placeLatLng = getPlaceLatLng(currentPlace);
        LatLng userLatLng = getUserLatLng();
        Marker placeMarker = mMap.addMarker(new MarkerOptions()
                .position(placeLatLng)
                .title(currentPlace.getName())
                .draggable(true));
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(placeLatLng)
                .include(userLatLng)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
    }

    @NonNull
    private LatLng getPlaceLatLng(Place currentPlace) {
        double placeLatitude = currentPlace.getGeometry().getLocation().getLatitude();
        double placeLongitude = currentPlace.getGeometry().getLocation().getLongitude();
        return new LatLng(placeLatitude, placeLongitude);
    }

    private LatLng getUserLatLng() {
        Location currentLocation = Settings.getCurrentLocation();
        return new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}