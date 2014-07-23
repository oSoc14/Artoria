package be.artoria.belfortapp.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.artoria.belfortapp.sql.POIDAO;

/**
 * Created by Laurens on 01/07/2014.
 */
public class DataManager {

    public static int numberOfPOIs = 0;

    /* Check if the data should be refreshed after a resume or whatever,
     * make sure the data exists is reasonably fresh.
     */
    public static void refresh() {
        // TODO implement
    }

    public enum Language{
        DUTCH,ENGLISH,FRENCH
    }
    public static Language lang = null;

    private static final DataManager INSTANCE = new DataManager();
    private static final List<POI> poiList = new ArrayList<POI>();
    public  static final POIDAO poidao = new POIDAO(PrefUtils.getContext());


    /*Route start point (belfort Ghent) needs to be replaced by current location in the future*/
    public static final double BELFORT_LON = 3.724833;
    public static final double BELFORT_LAT = 51.053623;

    // Private constructor prevents instantiation from other classes
    private DataManager() {    }

    public Language getCurrentLanguage(){
        if(lang == null) lang = PrefUtils.getLanguage();
        return lang;
    }
    
    public static List<POI> getAll(){
        /* poiList might very well be empty after resuming the app */
        if(poiList.isEmpty()){
            try {
                poidao.open();
            } catch (SQLException e) {
                // TODO catch exceptions in a sane manner.
            }
            poiList.addAll(poidao.getAllPOIs());
            numberOfPOIs = poiList.size();
            /* Worst case scenario, the local database is empty and we're asked for a poi */
            if(poiList.isEmpty()){
                // TODO worst case!
            }
        }
        poidao.close();
        return poiList;
    }

    public static POI getPOIbyID(int id){
        return getAll().get(id);
    }

    public static DataManager getInstance() {
        return INSTANCE;
    }

    public static void addAll(List<POI> list) {
        numberOfPOIs += list.size() ;
        poiList.addAll(list);
    }

    public static void clearpois() {
        numberOfPOIs = 0;
        poiList.clear();
    }

    public static int lastViewedPOI = 1;

    /*Only show the swipe... msg once*/
    public static boolean shownMonumentDetailLandscapeMsg = false;
}
