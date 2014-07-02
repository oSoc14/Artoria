package be.artoria.belfortapp.app;

/**
 * Created by Laurens on 01/07/2014.
 */
public class POI {
    public int id;
    public String lat;
    public String lon;
    public String name;
    public String image_url;
    public String description;

    public POI(int id, String lat, String lon, String name, String description, String image_url){
        this.id = id;
        this.image_url = image_url;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if( o == null ) return false;
        if(!( o instanceof POI)) return false;
        POI poi = (POI) o;
        return poi.id == this.id;
    }   
}
