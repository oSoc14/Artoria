package be.artoria.belfortapp.activities;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.RouteManager;

public class MapActivity extends ActionBarActivity {
    public static final int DEFAULT_ZOOM = 18;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        MapController mapCtrl = (MapController) mapView.getController();
        mapCtrl.setZoom(DEFAULT_ZOOM);
        mapCtrl.setCenter(new GeoPoint(DataManager.BELFORT_LAT,DataManager.BELFORT_LON));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        /*Example code how to draw a route on the osmdroid */
        /*
        DataManager mgr = DataManager.getInstance();
        PathOverlay myPath = new PathOverlay(Color.BLUE, this);
        myPath.addPoint(new GeoPoint(mgr.BELFORT_LAT,mgr.BELFORT_LON));
        POI point = mgr.poiList.get(0);
        myPath.addPoint(new GeoPoint(Double.parseDouble(point.lat),Double.parseDouble(point.lon)));
        point = mgr.poiList.get(1);
        myPath.addPoint(new GeoPoint(Double.parseDouble(point.lat),Double.parseDouble(point.lon)));
        mapView.getOverlays().add(myPath);
        */

        /*Example code using osmdroid bonuspack */

        DataManager mgr = DataManager.getInstance();
        //RoadManager roadManager = new MapQuestRoadManager("Fmjtd%7Cluur206a2d%2C82%3Do5-9at0dr");
        RoadManager roadManager = new OSRMRoadManager();
        Road road = roadManager.getRoad(getGeoPointsFromRoute());
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road, MapActivity.this);
        mapView.getOverlays().add(roadOverlay);
        mapView.invalidate();

        //drawRoute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<GeoPoint> getGeoPointsFromRoute(){
        ArrayList<GeoPoint>toReturn = new ArrayList<GeoPoint>();
        toReturn.add(new GeoPoint(DataManager.BELFORT_LAT,DataManager.BELFORT_LON));
        for(POI poi : RouteManager.getInstance().getWaypoints()){
            toReturn.add(new GeoPoint(Double.parseDouble(poi.lat),Double.parseDouble(poi.lon)));
        }
        return toReturn;
    }

}
