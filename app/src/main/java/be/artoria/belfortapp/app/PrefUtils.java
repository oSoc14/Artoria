package be.artoria.belfortapp.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.activities.LanguageChoiceActivity;

/**
 * Created by Michael Vanderpoorten
 * Adapted by Laurens De Graeve & Dieter Beelaert
 */
public class PrefUtils {
    public static final String TAG = "Belfort";
    public static final String DATASET_URL = "https://raw.githubusercontent.com/oSoc14/ArtoriaData/master/poi.json";

    private static final String ARG_USER_KEY = "be.artoria.belfort";
    private static final String ARG_DOWNLOAD = "be.artoria.belfort.downloadtimes";
    private static final String ARG_FIRS_TTIME = "be.artoria.belfort.firstTime";
    private static final String ARG_ROUTE = "be.artoria.belfort.route";
    private static final String ARG_LANG = "be.artoria.belfort.lang";
    private static final String ARG_FIRS_PANORAMA_TIME = "be.artoria.belfort.panorama_firstTime";

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

    public static DataManager.Language getLanguage() {
        final Locale locale = getContext().getResources().getConfiguration().locale;
        final String lang = locale.getLanguage();
        if("en".equals(lang)){ return DataManager.Language.ENGLISH;}
        if("fr".equals(lang)){ return DataManager.Language.FRENCH;}
        /* default choice is dutch */
        return DataManager.Language.DUTCH;
    }

    public static void saveLanguage(DataManager.Language lang){
        String lng = "nl";//default dutch
        if(lang == DataManager.Language.ENGLISH){lng="en";}
        if(lang == DataManager.Language.FRENCH){lng ="fr";}
        getPrefs().edit().putString(ARG_LANG,lng).apply();
    }

    public static void loadLanguage(Context context){
        final String lang = getPrefs().getString(ARG_LANG,"nl");/*Default dutch*/
        final Locale locale = new Locale(lang);
        LanguageChoiceActivity.setLang(locale,context);
    }

    public static List<POI> getSavedRoute(){
        final String poiIds = getPrefs().getString(ARG_ROUTE,"");
        final List<POI> toReturn = new ArrayList<POI>();
        if(!poiIds.equals("")){
            final String[] splitted = poiIds.split(",");
            for(String s: splitted){
                try {
                    final int id = Integer.parseInt(s);
                    final POI toAdd = DataManager.getPOIbyID(id);
                    toReturn.add(toAdd);
                }catch(Exception ex){
                    //can't parse string to int ....
                }
            }
        }
        return toReturn;
    }

    public static void saveRoute(List<POI> route){
        final StringBuilder sb = new StringBuilder();
        for(POI poi : route){
            sb.append(poi.id);
            sb.append(",");
        }
        if(sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        getPrefs().edit().putString(ARG_ROUTE,sb.toString()).apply();
    }

    public static void setPanoramaNotFirstTime() {
        getPrefs()
                .edit()
                .putBoolean(ARG_FIRS_PANORAMA_TIME, false)
                .apply();
    }
    public static boolean isFirstPanoramaTime() {
        return getPrefs().getBoolean(ARG_FIRS_PANORAMA_TIME, true);
    }

}
