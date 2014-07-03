package be.artoria.belfortapp.activities;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
    private static final String MAP_QUEST_API_KEY = "Fmjtd%7Cluur206a2d%2C82%3Do5-9at0dr"; //TODO request key for Artoria, this key now is 'licensed' to Dieter Beelaert
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //disable hardware acceleration, known issue of osmdroid bonus pack see: https://code.google.com/p/osmbonuspack/issues/detail?id=16
        MapController mapCtrl = (MapController) mapView.getController();
        mapCtrl.setZoom(DEFAULT_ZOOM);
        mapCtrl.setCenter(new GeoPoint(DataManager.BELFORT_LAT,DataManager.BELFORT_LON));
        new RouteCalcTask().execute();
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

    /* Transfer the route to an ArrayList of GeoPoint objects */
    private ArrayList<GeoPoint> getGeoPointsFromRoute(){
        ArrayList<GeoPoint>toReturn = new ArrayList<GeoPoint>();
        toReturn.add(new GeoPoint(DataManager.BELFORT_LAT,DataManager.BELFORT_LON));
        for(POI poi : RouteManager.getInstance().getWaypoints()){
            toReturn.add(new GeoPoint(Double.parseDouble(poi.lat),Double.parseDouble(poi.lon)));
        }
        return toReturn;
    }

    /*Gets the route from the web and draws it on the map when done*/
    private class RouteCalcTask extends AsyncTask {

        @Override
        protected Polyline doInBackground(Object[] objects) {
            RoadManager roadManager = new MapQuestRoadManager(MAP_QUEST_API_KEY);
            //RoadManager roadManager = new OSRMRoadManager();
            roadManager.addRequestOption("routeType=pedestrian");
            Road road = roadManager.getRoad(getGeoPointsFromRoute());
            return  RoadManager.buildRoadOverlay(road, MapActivity.this);
        }

        @Override
        protected void onPostExecute(Object result){
            mapView.getOverlays().add((Polyline)result);
            mapView.invalidate();
        }
    }
}
