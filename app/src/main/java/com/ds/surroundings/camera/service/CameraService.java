package com.ds.surroundings.camera.service;

import android.content.Context;
import android.hardware.Camera;

public interface CameraService {

    Camera getCamera(Context context);

    Camera setUpCamera(Context context);

    void releaseCamera();
}
