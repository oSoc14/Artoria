package be.artoria.belfortapp.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by Michael Vanderpoorten
 */
public class PrefUtils {

    private static final String ARG_USER_KEY = "be.artoria.belfort";
    private static Context CONTEXT;

    public static Context getContext()
    {
        return CONTEXT;
    }

    public static void initialize(Application application)
    {
        CONTEXT = application;
    }

    public static void saveTimeStampDownloads(String args)
    {
        getPrefs()
                .edit()
                .putLong(args, System.currentTimeMillis())
                .apply();
    }

    public static long getTimeStamp()
    {
        return getPrefs().getLong("test", 0);
    }

    public static long getTimeStampDownloads(String args)
    {
        return getPrefs().getLong(args, 0);
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
