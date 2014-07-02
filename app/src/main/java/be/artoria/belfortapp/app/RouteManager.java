package be.artoria.belfortapp.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dieter Beelaert on 1/07/2014.
 */
public class RouteManager {

    private static RouteManager instance;
    private List<POI> waypoints;

    private RouteManager(){
        /* get routes from storage ...*/
        /* initialize test data */
        waypoints = new ArrayList<POI>();
        waypoints.add(new POI(0,"51.053939", "3.722958","Sint-Niklaaskerk","SINT_NIKLAAS KERK WAUW","http://www.sintniklaaskerk.be/media/photos/560/021f552775075dd9e053e30072ea21045d628ef3.jpg"));
        waypoints.add(new POI(1,"51.053952", "3.722196","Korenmarkt","YEEEEEEE","http://fideel.files.wordpress.com/2009/08/korenmarkt-ca-1910-2.jpg"));
        waypoints.add(new POI(2,"51.054562", "3.724862","Stadhuis","WOOOHOOOOOOOOO HOOOO HOOOO","http://upload.wikimedia.org/wikipedia/commons/2/2c/Gent_stadhuis_belfort_1890-1900.jpg"));
    }

    public static RouteManager getInstance(){
        if(instance == null){
            instance = new RouteManager();
        }
        return instance;
    }

    public List<POI> getWaypoints(){
        return waypoints;
    }

    public void addWayPoint(POI wp){
        /*Save waypoints to shared preferences or sqllite ...*/
        waypoints.add(wp);
    }
}
