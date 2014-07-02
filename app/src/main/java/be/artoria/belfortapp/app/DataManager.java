package be.artoria.belfortapp.app;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurens on 01/07/2014.
 */
public class DataManager {
    public enum Language{
        DUTCH,ENGLISH,FRENCH
    }
    private static final Language lang = null;

    private static final DataManager INSTANCE = new DataManager();

    public static final List<POI> poiList = new ArrayList<POI>();

    /* TEST DATA*/
    static{
        poiList.add(new POI(0,"51.053939", "3.722958","Sint-Niklaaskerk","SINT_NIKLAAS KERK WAUW","http://www.sintniklaaskerk.be/media/photos/560/021f552775075dd9e053e30072ea21045d628ef3.jpg"));
        poiList.add(new POI(1,"51.053952", "3.722196","Korenmarkt","YEEEEEEE","http://fideel.files.wordpress.com/2009/08/korenmarkt-ca-1910-2.jpg"));
        poiList.add(new POI(2,"51.054562", "3.724862","Stadhuis","WOOOHOOOOOOOOO HOOOO HOOOO","http://upload.wikimedia.org/wikipedia/commons/2/2c/Gent_stadhuis_belfort_1890-1900.jpg"));
    }

    /*Route start point (belfort Ghent) needs to be replaced by current location in the future*/
    public static final double BELFORT_LON = 3.724833;
    public static final double BELFORT_LAT = 51.053623;


    // Private constructor prevents instantiation from other classes
    private DataManager() {}

    public Language getCurrentLanguage(){

        return Language.DUTCH;
    }

    public static DataManager getInstance() {
        return INSTANCE;
    }
}
