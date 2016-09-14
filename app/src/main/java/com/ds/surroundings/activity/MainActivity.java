package com.ds.surroundings.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.ds.surroundings.R;
import com.ds.surroundings.application.Surroundings;
import com.ds.surroundings.camera.CameraPreview;
import com.ds.surroundings.camera.service.CameraService;
import com.ds.surroundings.camera.service.impl.CameraServiceImpl;
import com.ds.surroundings.place.container.PlaceList;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer {

    private Surroundings application;
    private CameraService cameraService = new CameraServiceImpl();
    private CameraPreview cameraPreview;
    private PlaceList observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = (Surroundings) getApplication();
        observer = application.getObserver();

        //setCameraView();
        observer.addObserver(this);
        observer.update();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraService.releaseCamera();
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.removeAllViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCameraView();

    }

    @Override
    public void update(Observable observable, Object data) {
        //TODO update view
        Log.d("## Main Activity:", "ObserverUpdate");
    }

    private void setCameraView() {
        Camera camera = cameraService.getCamera(this);
        createCameraPreview(camera);
    }

    private void createCameraPreview(Camera camera) {
        cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);
    }
}
