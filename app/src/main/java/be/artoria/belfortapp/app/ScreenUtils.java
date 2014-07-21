package be.artoria.belfortapp.app;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Dieter Beelaert on 21/07/2014.
 */
public class ScreenUtils {
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
