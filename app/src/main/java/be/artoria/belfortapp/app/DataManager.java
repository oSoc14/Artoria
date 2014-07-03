package be.artoria.belfortapp.app;

import android.content.res.Resources
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import be.artoria.belfortapp.sql.POIDAO;

/**
 * Created by Laurens on 01/07/2014.
 */
public class DataManager {

    public static int numberOfPOIs = 0;

    public enum Language{
        DUTCH,ENGLISH,FRENCH
    }
    public static Language lang = null;

    private static final DataManager INSTANCE = new DataManager();
    private static List<POI> poiList = new ArrayList<POI>();
    public static POIDAO poidao = new POIDAO(PrefUtils.getContext());


    /*Route start point (belfort Ghent) needs to be replaced by current location in the future*/
    public static final double BELFORT_LON = 3.724833;
    public static final double BELFORT_LAT = 51.053623;

    // Private constructor prevents instantiation from other classes
    private DataManager() {    }

    static{
       // poiList.add(new POI(0,"51.053939", "3.722958","Sint-Niklaaskerk","SINT_NIKLAAS KERK WAUW","http://www.sintniklaaskerk.be/media/photos/560/021f552775075dd9e053e30072ea21045d628ef3.jpg"));
       // poiList.add(new POI(1,"51.053952", "3.722196","Korenmarkt","YEEEEEEE","http://fideel.files.wordpress.com/2009/08/korenmarkt-ca-1910-2.jpg"));
       // poiList.add(new POI(2,"51.054562", "3.724862","Stadhuis","WOOOHOOOOOOOOO HOOOO HOOOO","http://upload.wikimedia.org/wikipedia/commons/2/2c/Gent_stadhuis_belfort_1890-1900.jpg"));
    static{
        //poiList.add(new POI(0,"51.053939", "3.722958","Sint-Niklaaskerk","SINT_NIKLAAS KERK WAUW","http://www.sintniklaaskerk.be/media/photos/560/021f552775075dd9e053e30072ea21045d628ef3.jpg"));
        //poiList.add(new POI(1,"51.053952", "3.722196","Korenmarkt","YEEEEEEE","http://fideel.files.wordpress.com/2009/08/korenmarkt-ca-1910-2.jpg"));
        //poiList.add(new POI(2,"51.054562", "3.724862","Stadhuis","WOOOHOOOOOOOOO HOOOO HOOOO","http://upload.wikimedia.org/wikipedia/commons/2/2c/Gent_stadhuis_belfort_1890-1900.jpg"));
    public Language getCurrentLanguage(){
        if(lang == null) lang = PrefUtils.getLanguage();
        return lang;
    }

    public static POI getPOIbyID(int id){
        /* poiList might very well be empty after resuming the app */
        if(poiList.isEmpty()){
            try {
                poidao.open();
            } catch (SQLException e) {
                // TODO catch exceptions in a sane manner.
            }
            poiList.addAll(poidao.getAllPOIs());
            /* Worst case scenario, the local database is empty and we're asked for a poi */
            if(poiList.isEmpty()){

            }
        }
        poidao.close();
        return poiList.get(id);
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
}
