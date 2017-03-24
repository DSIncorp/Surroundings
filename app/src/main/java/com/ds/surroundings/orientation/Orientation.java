package com.ds.surroundings.orientation;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

public class Orientation implements SensorEventListener {

    public interface Listener {

        void onOrientationChanged(float pitch, float roll, float yaw);
    }

    private static final int SENSOR_DELAY_MICROS = 50 * 1000; // 50ms

    private final WindowManager mWindowManager;

    private final SensorManager mSensorManager;
    @Nullable
    private final Sensor mRotationSensor;

    private int mLastAccuracy;

    private Listener mListener;

    public Orientation(Activity activity) {
        mWindowManager = activity.getWindow().getWindowManager();
        mSensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    public void startListening(Listener listener) {
        if (mListener == listener) {
            return;
        }
        mListener = listener;
        if (mRotationSensor == null) {
            Log.w("Orientation :: ", "Rotation vector sensor not available; will not provide orientation data.");
            return;
        }
        mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stopListening() {
        mSensorManager.unregisterListener(this);
        mListener = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener == null) {
            return;
        }
        if (mLastAccuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }
        if (event.sensor == mRotationSensor) {
            updateOrientation(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (mLastAccuracy != accuracy) {
            mLastAccuracy = accuracy;
        }
    }

    long mLastT = 0;

    private void updateOrientation(SensorEvent event) {
        float[] rotationMatrix = new float[16];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

        final int worldAxisForDeviceAxisX = SensorManager.AXIS_Z;
        final int worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X;

        float[] adjustedRotationMatrix = new float[16];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisForDeviceAxisX,
                worldAxisForDeviceAxisY, adjustedRotationMatrix);

        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);

        //TODO consider to use Math.toDegrees()
        float yaw = (float) Math.round(Math.toDegrees(orientation[0]));
        float pitch = (float) Math.round(Math.toDegrees(orientation[1]));
        float roll = (float) Math.round(Math.toDegrees(orientation[2]));

//        if (event.timestamp - mLastT > 500000000) {
//            mLastT = event.timestamp;
            mListener.onOrientationChanged(pitch, roll, yaw);
//        }
    }
}
