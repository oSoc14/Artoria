package be.artoria.belfortapp.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import be.artoria.belfortapp.R;

/**
 * Created by Laurens on 01/07/2014.
 */
public class POI {


   private static final int BOAT      = 0;
   private static final int CIVIL     = 1;
   private static final int RELIGIOUS = 2;
   private static final int TOWER     = 3;
   private static final int THEATRE   = 4;
   private static final int CASTLE    = 5;
   private static final int MONUMENT  = 6;


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
    public POI(){}

    @Override
    public boolean equals(Object o) {
        if( o == null ) return false;
        if(!( o instanceof POI)) return false;
        POI poi = (POI) o;
        return poi.id == this.id;
    }

    public String getName() {
        final DataManager.Language lang = DataManager.getInstance().getCurrentLanguage();
        switch(lang){
            case FRENCH:
                return this.FR_name;
            case DUTCH:
                return this.NL_name;
            case ENGLISH:
            default:
                return this.ENG_name;

        }
    }

    public String getDescription() {
        final DataManager.Language lang = DataManager.getInstance().getCurrentLanguage();
        switch(lang){
            case FRENCH:
                return this.FR_description;
            case DUTCH:
                return this.NL_description;
            case ENGLISH:
            default:
                return this.ENG_description;
        }
    }

    @Override
    public String toString(){
        return getName();
    }


    public static Drawable getTypeImg(int type,Context context){
        return getTypeImage(type,context,DRAG_HANDLE);
    }

    public static Drawable getTypePopupImg(int type, Context context){
       return getTypeImage(type,context,POPUP_PORTRAIT);
    }

    public static Drawable getTypePopupImgLandscape(int type, Context context){
       return getTypeImage(type,context,POPUP_LANDSCAPE);
    }

    private static final int DRAG_HANDLE = 0;
    private static final int POPUP_PORTRAIT = 1;
    private static final int POPUP_LANDSCAPE = 2;

    private static Drawable getTypeImage(int type, Context context, int viewType){
        final Resources res = context.getResources();
        switch(type){
            case BOAT:      return res.getDrawable(getCorrectImageId(R.drawable.drag_boat,R.drawable.popup_boat,R.drawable.popup_boat_land,viewType));
            case CASTLE:    return res.getDrawable(getCorrectImageId(R.drawable.drag_castle,R.drawable.popup_castle,R.drawable.popup_castle_land,viewType));
            case CIVIL:     return res.getDrawable(getCorrectImageId(R.drawable.drag_city,R.drawable.popup_city,R.drawable.popup_city_land,viewType));
            case MONUMENT:  return res.getDrawable(getCorrectImageId(R.drawable.drag_monument,R.drawable.popup_monument,R.drawable.popup_monument_land,viewType));
            case RELIGIOUS: return res.getDrawable(getCorrectImageId(R.drawable.drag_religion,R.drawable.popup_religion,R.drawable.popup_religion_land,viewType));
            case THEATRE:   return res.getDrawable(getCorrectImageId(R.drawable.drag_theater,R.drawable.popup_theater,R.drawable.popup_theater_land,viewType));
            case TOWER:     return res.getDrawable(getCorrectImageId(R.drawable.drag_skyscraper,R.drawable.popup_skyscraper,R.drawable.popup_skyscraper_land,viewType));
            default:        return res.getDrawable(getCorrectImageId(R.drawable.drag_monument,R.drawable.popup_monument,R.drawable.popup_default_land,viewType));
        }
    }

    private static int getCorrectImageId(int drag, int popupPortrait, int popupLandscape,int viewType){
        if(viewType == DRAG_HANDLE){
            return drag;
        }else if(viewType == POPUP_PORTRAIT){
            return popupPortrait;
        }else{
            return popupLandscape;
        }
    }
}
