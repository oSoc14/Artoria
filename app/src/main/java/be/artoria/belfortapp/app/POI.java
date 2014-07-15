package be.artoria.belfortapp.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.Locale;

import be.artoria.belfortapp.R;

/**
 * Created by Laurens on 01/07/2014.
 */
public class POI {

    public static final int TYPE_CHURCH = 1;
    public static final int TYPE_CASTLE = 2;

    public int id;
    public String lat;
    public String lon;
    public String height;
    public String NL_name;
    public String FR_name;
    public String ENG_name;
    public String image_link;
    public String NL_description;
    public String FR_description;
    public String ENG_description;
    public int type;


    public POI(int id, String lat, String lon,String height, String name, String description, String image_url,int type){
        this.id = id;
        this.image_link = image_url;
        this.lat = lat;
        this.lon = lon;
        this.height = height;
        this.NL_name = name+ "NL";
        this.FR_name = name + "FR";
        this.ENG_name = name+ "ENG";
        this.NL_description = description + "NL";
        this.ENG_description = description + "ENG";
        this.FR_description = description + "FR";
        this.type = type;
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


    public static Drawable getTypeImg(int type,Context context){
        return getTypeImage(type,context,false);
    }

    public static Drawable getTypePopupImg(int type, Context context){
       return getTypeImage(type,context,true);
    }

    private static Drawable getTypeImage(int type, Context context, boolean popup){
        Resources res = context.getResources();
        switch(type){
            case POI.TYPE_CASTLE: return popup ? res.getDrawable(R.drawable.popup_castle) : res.getDrawable(R.drawable.drag_castle);
            case POI.TYPE_CHURCH: return popup ? res.getDrawable(R.drawable.popup_church) : res.getDrawable(R.drawable.drag_church);
            default: return popup ? res.getDrawable(R.drawable.popup_default) : res.getDrawable(R.drawable.drag_default);
        }
    }
}
