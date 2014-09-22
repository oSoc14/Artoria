package be.artoria.belfortapp.app;

/**
 * Created by dietn on 15.09.14.
 */
public class FloorExhibit {

    public int floor;
    public String image;
    public String NL_name;
    public String NL_desc;

    public String EN_name;
    public String EN_desc;

    public String FR_name;
    public String FR_desc;

    public String IT_name;
    public String IT_desc;

    public String ES_name;
    public String ES_desc;

    public String RU_name;
    public String RU_desc;

    public String DE_name;
    public String DE_desc;

    //empty constructor for GSON
    public FloorExhibit(){}

    public String getName(){
        switch(PrefUtils.getLanguage()){
            case DUTCH:   return NL_name;
            case FRENCH:  return FR_name;
            case ITALIAN: return IT_name;
            case SPANISH: return ES_name;
            case RUSSIAN: return RU_name;
            case GERMAN:  return DE_name;
            case ENGLISH:
            default:      return EN_name;
        }
    }

    public String getDescription(){
        switch(PrefUtils.getLanguage()){
            case DUTCH:   return NL_desc;
            case FRENCH:  return FR_desc;
            case ITALIAN: return IT_desc;
            case SPANISH: return ES_desc;
            case RUSSIAN: return RU_desc;
            case GERMAN:  return DE_desc;
            case ENGLISH:
            default:      return EN_desc;
        }
    }
    public String getImage(){
        return image;
    }

}
