package com.ds.surroundings.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ds.surroundings.R;
import com.google.android.gms.maps.MapView;

public class SinglePlaceActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_place);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MapView mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
//        GoogleMap map = mMapView.getMap();
    }

}