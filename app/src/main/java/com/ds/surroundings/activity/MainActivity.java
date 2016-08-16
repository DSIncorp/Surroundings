package com.ds.surroundings.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ds.surroundings.R;
import com.ds.surroundings.camera.CameraPreview;
import com.ds.surroundings.camera.service.CameraService;
import com.ds.surroundings.camera.service.impl.CameraServiceImpl;

public class MainActivity extends Activity {

    private CameraService cameraService = new CameraServiceImpl();
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setCameraView();
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
