package be.artoria.belfortapp.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import be.artoria.belfortapp.R;

/**
 * Created by Michael Vanderpoorten
 * Adapted by Laurens De Graeve
 */
public class PrefUtils {

    private static final String ARG_USER_KEY = "be.artoria.belfort";
    private static final String ARG_DOWNLOAD = "be.artoria.belfort.downloadtimes";
    private static final String ARG_FIRS_TTIME = "be.artoria.belfort.firstTime";

    private static Context CONTEXT;

    public static Context getContext()
    {
        return CONTEXT;
    }

    public static void initialize(Application application)
    {
        CONTEXT = application;
    }

    public static void saveTimeStampDownloads()
    {
        getPrefs()
                .edit()
                .putLong(ARG_DOWNLOAD, System.currentTimeMillis())
                .apply();
    }

    public static boolean isFirstTime() {
        return getPrefs().getBoolean(ARG_FIRS_TTIME, true);
    }

    public static void setNotFirstTime() {
        getPrefs()
                .edit()
                .putBoolean(ARG_FIRS_TTIME, false)
                .apply();
    }

    public static long getTimeStamp()
    {
        return getPrefs().getLong("test", 0);
    }

    public static long getTimeStampDownloads()
    {
        return getPrefs().getLong(ARG_DOWNLOAD, 0);
    }

    public static void removeAll()
    {
        getPrefs().edit().clear().apply();
    }

    // get preferences
    @SuppressWarnings("NewApi")
    private static SharedPreferences getPrefs()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)        {
            return CONTEXT.getSharedPreferences(ARG_USER_KEY, Context.MODE_MULTI_PROCESS);
        } else {
            return CONTEXT.getSharedPreferences(ARG_USER_KEY, Context.MODE_PRIVATE);
        }
    }

}