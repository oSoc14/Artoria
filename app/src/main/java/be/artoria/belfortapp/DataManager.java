package be.artoria.belfortapp;


public class DataManager {

    /* Available languages*/
    public enum Language{
        DUTCH,FRENCH,ENGLISH
    }

    private static final DataManager INSTANCE = new DataManager();

    public static Language lang = Language.DUTCH;

    // Private constructor prevents instantiation from other classes
    private DataManager() {}

    public static DataManager getInstance() {
        return INSTANCE;
    }
}