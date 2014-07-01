package be.artoria.belfortapp.app;

/**
 * Created by Laurens on 01/07/2014.
 */
public class DataManager {

    private static final DataManager INSTANCE = new DataManager();
    public static String language = null;

    private DataManager(){};

    public DataManager getInstance(){
        return INSTANCE;
    }
}
