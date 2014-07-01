package be.artoria.belfortapp.app;

import java.util.List;

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

    /*The current route*/
    public static List<String> wayPoints;

    // Private constructor prevents instantiation from other classes
    private DataManager() {}

    public static DataManager getInstance() {
        return INSTANCE;
    }
}
