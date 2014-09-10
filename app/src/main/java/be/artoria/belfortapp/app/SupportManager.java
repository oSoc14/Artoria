package be.artoria.belfortapp.app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.Toast;

import be.artoria.belfortapp.R;

/**
 * Created by dietn on 10.09.14.
 */
public class SupportManager {

    public static boolean isDeviceSupported(){
        Context ctx = PrefUtils.getContext();
        final SensorManager mSensorManager = (SensorManager) ctx.getSystemService(ctx.SENSOR_SERVICE);
        boolean isSupported = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null && mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null;
        if(!isSupported){
                Toast.makeText(PrefUtils.getContext(), R.string.unsupported, Toast.LENGTH_LONG).show();
        }
        return isSupported;
    }
}
