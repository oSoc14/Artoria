package be.artoria.belfortapp.app;


/**
 * Created by Laurens on 01/07/2014.
 */
public class DataManager {

    /* Available languages*/
    public enum Language{
        DUTCH,FRENCH,ENGLISH
    }

    private static final DataManager INSTANCE = new DataManager();

    public static Language lang = Language.DUTCH;

    /*Route start point (belfort Ghent) needs to be replaced by current location in the future*/
    public static final double BELFORT_LON = 3.724833;
    public static final double BELFORT_LAT = 51.053623;
    // Private constructor prevents instantiation from other classes
    private DataManager() {}

    public static DataManager getInstance() {
        return INSTANCE;
    }
}
