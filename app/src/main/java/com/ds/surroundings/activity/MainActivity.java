package com.ds.surroundings.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.ds.surroundings.R;
import com.ds.surroundings.application.ApplicationComponent;
import com.ds.surroundings.application.Surroundings;
import com.ds.surroundings.camera.CameraPreview;
import com.ds.surroundings.camera.service.CameraService;
import com.ds.surroundings.place.async.LoadPlaceTask;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import static com.ds.surroundings.settings.Settings.getCurrentLocation;
import static com.ds.surroundings.settings.Settings.getSearchRadius;
import static com.ds.surroundings.settings.Settings.getTypesToSearch;

public class MainActivity extends Activity implements Observer {

    private CameraService cameraService;
    private PlaceService placeService;

    public ApplicationComponent getAppComponent() {
        return Surroundings.getApplicationComponent();
    }

    @Inject
    public void setCameraService(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @Inject
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        Surroundings application = (Surroundings) getApplication();

        PlaceList observer = application.getObserver();
        observer.addObserver(this);
        observer.update();

        Log.d("Camera service isnull: ", String.valueOf((cameraService == null)));
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
        Log.d("Camera service isnull: ", String.valueOf((cameraService == null)));

        setCameraView();
        try {
            new LoadPlaceTask(placeService).execute(getCurrentLocation(), getSearchRadius(), getTypesToSearch()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

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
        CameraPreview cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);
    }
}
