package be.artoria.belfortapp.app;

import java.util.Locale;

/**
 * Created by Laurens on 01/07/2014.
 */
public class POI {
    public int id;
    public String lat;
    public String lon;
    public String NL_name;
    public String FR_name;
    public String ENG_name;
    public String image_link;
    public String NL_description;
    public String FR_description;
    public String ENG_description;

    public POI(int id, String lat, String lon, String name, String description, String image_url){
        this.id = id;
        this.image_link = image_url;
        this.lat = lat;
        this.lon = lon;
        this.NL_name = name+ "NL";
        this.FR_name = name + "FR";
        this.ENG_name = name+ "ENG";
        this.NL_description = description + "NL";
        this.ENG_description = description + "ENG";
        this.FR_description = description + "FR";
    }
    /* Empty constructor needed by gson*/
    public POI(){};

    @Override
    public boolean equals(Object o) {
        if( o == null ) return false;
        if(!( o instanceof POI)) return false;
        POI poi = (POI) o;
        return poi.id == this.id;
    }

    public String getName() {
        DataManager.Language lang = DataManager.getInstance().getCurrentLanguage();
        switch(lang){
            case ENGLISH:
            return this.ENG_name;
        case FRENCH:
            return this.FR_name;
        case DUTCH:
        default:
            /*Default is Dutch*/
            return this.NL_name;
        }
    }

    public String getDescription() {
        DataManager.Language lang = DataManager.getInstance().getCurrentLanguage();
        switch(lang){
            case ENGLISH:
                return this.ENG_description;
            case FRENCH:
                return this.FR_description;
            case DUTCH:
            default:
            /*Default is Dutch*/
                return this.NL_description;
        }
    }

    @Override
    public String toString(){
        return getName();
    }
}
