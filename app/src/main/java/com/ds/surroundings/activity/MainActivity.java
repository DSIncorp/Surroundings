package com.ds.surroundings.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ds.surroundings.R;
import com.ds.surroundings.application.ApplicationComponent;
import com.ds.surroundings.application.Surroundings;
import com.ds.surroundings.camera.CameraPreview;
import com.ds.surroundings.camera.service.CameraService;
import com.ds.surroundings.orientation.Orientation;
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

public class MainActivity extends Activity implements Observer, Orientation.Listener {

    private CameraService cameraService;
    private PlaceService placeService;
    private Orientation orientation;

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

        orientation = new Orientation(this);

        Log.d("Camera service isnull: ", String.valueOf((cameraService == null)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        orientation.startListening(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        orientation.stopListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraService.releaseCamera();
        orientation.stopListening();
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.removeAllViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Camera service isnull: ", String.valueOf((cameraService == null)));

        setCameraView();
        orientation.startListening(this);
        try {
            new LoadPlaceTask(placeService).execute(getCurrentLocation(), getSearchRadius(), getTypesToSearch()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    int count = 0;
    TextView degreesLabel;
    TextView degreesLabel2;
    TextView degreesLabel3;
    TextView degreesLabel4;

    @Override
    public void onOrientationChanged(float pitch, float roll, float yaw) {

        Log.d("rotation", String.valueOf(this.getWindowManager().getDefaultDisplay().getRotation()));
        degreesLabel.setText(pitch + "°");
        degreesLabel2.setText(roll + "°");
        degreesLabel3.setText(yaw + "°");
        count++;
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

        degreesLabel = new TextView(this);
        degreesLabel2 = new TextView(this);
        degreesLabel2.setY(50);
        degreesLabel3 = new TextView(this);
        degreesLabel3.setY(100);
        degreesLabel4 = (TextView) findViewById(R.id.degreesLabel);
        preview.addView(degreesLabel);
        preview.addView(degreesLabel2);
        preview.addView(degreesLabel3);
    }
}
