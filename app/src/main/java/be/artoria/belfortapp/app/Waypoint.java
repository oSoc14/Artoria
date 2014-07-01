package be.artoria.belfortapp.app;

/**
 * Created by Laurens on 01/07/2014.
 */
public class Waypoint {

    public double lat;
    public int id;
    public double lon;
    public String name;
    public String description;
    public String url;

    public Waypoint(int id, double lat, double lon, String name,String description){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.description = description;
    }

}
