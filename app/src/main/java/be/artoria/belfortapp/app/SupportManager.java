package be.artoria.belfortapp.app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.widget.Toast;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.sql.POIDAO;

/**
 * Created by dietn on 10.09.14.
 */
public class SupportManager {

    public static boolean isDeviceSupported(){
        final Context ctx = PrefUtils.getContext();
        final SensorManager mSensorManager = (SensorManager) ctx.getSystemService(ctx.SENSOR_SERVICE);
        boolean isSupported = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null && mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null;
        if(!isSupported){
                Toast.makeText(PrefUtils.getContext(), R.string.unsupported, Toast.LENGTH_LONG).show();
        }
        return isSupported;
    }

    public static boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        final ConnectivityManager cm = (ConnectivityManager) PrefUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        final boolean hasConnection =  haveConnectedWifi || haveConnectedMobile;
        if(!hasConnection){
            Toast.makeText(PrefUtils.getContext(),R.string.no_connection,Toast.LENGTH_LONG).show();
        }
        return hasConnection;
    }

    //checks if there are monuments in the database, so the user can enjoy some of the features while being offline
    public static boolean hasMonumentsInDatabase(){
        return DataManager.getAll().size() > 0;
    }

    public static boolean hasMuseumDataInDatabase(){
        return DataManager.getFloorList().size() > 0;
    }
}
