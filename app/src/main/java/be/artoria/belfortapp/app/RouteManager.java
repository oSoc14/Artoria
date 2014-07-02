package be.artoria.belfortapp.app;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dieter Beelaert on 1/07/2014.
 */
public class RouteManager {
    private static RouteManager instance;
    private List<POI> waypoints;
    private static final String MAP_QUEST_API_KEY = "Fmjtd%7Cluur206a2d%2C82%3Do5-9at0dr"; //TODO request key for Artoria, this key now is 'licensed' to Dieter Beelaert
    private static final String MAP_QUEST_URL_START = "http://open.mapquestapi.com/directions/v2/route?key=" + MAP_QUEST_API_KEY + "&unit=k&routeType=pedestrian&fullShape=true";

    private RouteManager(){
        /* get routes from storage ...*/
        /* initialize test data */
        waypoints = new ArrayList<POI>();
        DataManager mngr = DataManager.getInstance();
        waypoints.add(mngr.poiList.get(0));
        waypoints.add(mngr.poiList.get(1));
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

    public List<GeoPoint> getDirections(){
        return new ArrayList<GeoPoint>();
    }
}
