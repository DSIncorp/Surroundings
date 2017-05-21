package com.ds.surroundings.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ds.surroundings.R;
import com.ds.surroundings.application.ApplicationComponent;
import com.ds.surroundings.application.Surroundings;
import com.ds.surroundings.camera.CameraPreview;
import com.ds.surroundings.camera.service.CameraService;
import com.ds.surroundings.orientation.Orientation;
import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.PlaceButton;
import com.ds.surroundings.place.container.PlaceList;
import com.ds.surroundings.place.service.PlaceService;
import com.ds.surroundings.util.PlaceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import static com.ds.surroundings.settings.Settings.getCurrentLocation;
import static java.lang.Math.round;

public class MainActivity extends Activity implements Observer, Orientation.Listener {

    private CameraService cameraService;
    private PlaceService placeService;
    private Orientation orientation;
    private List<PlaceButton> placeButtons;

    public ApplicationComponent getAppComponent() {
        return Surroundings.getApplicationComponent();
    }

    double cameraAngle;
    DisplayMetrics metrics;
    CameraPreview cameraPreview;

    @Inject
    public void setCameraService(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @Inject
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    TextView yawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        Surroundings application = (Surroundings) getApplication();
        placeButtons = new ArrayList<>();
        PlaceList observer = application.getObserver();
        observer.addObserver(this);

        orientation = new Orientation(this);

        Log.d("Camera service isnull: ", String.valueOf((cameraService == null)));

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Camera.Parameters parameters = cameraService.getCamera(this).getParameters();
        cameraAngle = parameters.getVerticalViewAngle();

        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        setUpView();

        yawView = new TextView(this);
        preview.addView(yawView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        orientation.startListening(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraService.releaseCamera();
        orientation.stopListening();
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        preview.removeView(cameraPreview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraService.releaseCamera();
        orientation.stopListening();
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        preview.removeView(cameraPreview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Camera service isnull: ", String.valueOf((cameraService == null)));
        setCameraView();
        orientation.startListening(this);
//        try {
//            new LoadPlaceTask(placeService, this).execute(getCurrentLocation(),
//                    getSearchRadius(), getTypesToSearch()).get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onOrientationChanged(float yaw) {
        yawView.setText(String.valueOf(yaw));
        for (PlaceButton button : placeButtons) {
            Place place = button.getPlace();
            float deltaAngle = PlaceUtil.getPlaceAzimut(place.getGeometry().getLocation(),
                    getCurrentLocation()) - yaw;
            if (deltaAngle > 180) deltaAngle -= 360;
            button.setX(round(metrics.widthPixels * deltaAngle / cameraAngle));
        }

    }

    @Override
    public void update(Observable observable, Object data) {
        PlaceList placeList = (PlaceList) observable;
        List<Place> places = placeList.getPlaces();

        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        for (PlaceButton button : placeButtons) {
            preview.removeView(button);
        }
        for (Place place : places) {
            createButton(place);
        }
        for (PlaceButton button : placeButtons) {
            preview.addView(button);
        }

    }

    private void createButton(Place place) {
        PlaceButton button = new PlaceButton(this, place);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        button.setY(metrics.heightPixels / 2 + button.getHeight() / 2);

        button.setText(place.getName());
        button.setTextColor(-1);
        button.setBackgroundColor(0xff444444);
        button.setBackgroundResource(R.drawable.button_shape);
        button.setPadding(10, 0, 0, 0);
        button.getBackground().setAlpha(100);
        button.setOnClickListener(v -> startSinglePlaceActivity(place));
        placeButtons.add(button);
    }

    private void startSinglePlaceActivity(Place place) {
        Intent singlePlaceIntent = new Intent(this, SinglePlaceActivity.class);
        singlePlaceIntent.putExtra("place", place);
        startActivity(singlePlaceIntent);
    }

    private void setUpView() {
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(view -> {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        });
    }

    private void setCameraView() {
        Camera camera = cameraService.getCamera(this);
        cameraPreview = new CameraPreview(this, camera);
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview, 0);
    }

}
