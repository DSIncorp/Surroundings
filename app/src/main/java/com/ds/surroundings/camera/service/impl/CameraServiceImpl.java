package com.ds.surroundings.camera.service.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.DisplayMetrics;

import com.ds.surroundings.camera.service.CameraService;

import java.util.List;

public class CameraServiceImpl implements CameraService {

    private static final double ASPECT_TOLERANCE = 0.1;

    private Camera camera;

    public Camera getCamera(Context context) {
        if (camera == null) {
            camera = getCameraInstance(context);
        }
        setUpCamera(context);
        return camera;
    }

    public Camera setUpCamera(Context context) {
        if (camera == null) {
            throw new RuntimeException("Camera is not available");
        }
        Camera.Parameters params = camera.getParameters();
        params.setJpegQuality(100);
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        setPreviewSize(context, params);
        camera.setParameters(params);
        return camera;
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
        }
        camera = null;
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private Camera getCameraInstance(Context context) {
        if (!checkCameraHardware(context)) {
            throw new RuntimeException("Camera doesn't exist!");
        }
        try {
            return camera = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            throw new RuntimeException("Camera is not available!");
        }
    }

    private void setPreviewSize(Context context, Camera.Parameters params) {
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Camera.Size optimalSize = getOptimalPreviewSize(sizes, displayMetrics.widthPixels, displayMetrics.heightPixels);
        params.setPreviewSize(optimalSize.width, optimalSize.height);
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {
        double targetRatio = (double) height / width;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - height) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - height);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - height) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - height);
                }
            }
        }
        return optimalSize;
    }
}
