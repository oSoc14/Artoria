package be.artoria.belfortapp.app;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dieter Beelaert on 1/07/2014.
 */
public class RouteManager {

    private static RouteManager instance;
    private List<Waypoint> waypoints;

    private RouteManager(){
        /* get routes from storage ...*/
        /* initialize test data */
        waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(0,51.053939, 3.722958,"Sint-Niklaaskerk",""));
        waypoints.add(new Waypoint(0,51.053952, 3.722196,"Korenmarkt",""));
        waypoints.add(new Waypoint(0,51.054562, 3.724862,"Stadhuis",""));
    }

    public static RouteManager getInstance(){
        if(instance == null){
            instance = new RouteManager();
        }
        return instance;
    }

    public List<Waypoint> getWaypoints(){
        return waypoints;
    }

    public void addWayPoint(Waypoint wp){
        /*Save waypoints to shared preferences or sqllite ...*/
        waypoints.add(wp);
    }

    public List<GeoPoint> getDirections(){
        return new ArrayList<GeoPoint>();
    }
}
