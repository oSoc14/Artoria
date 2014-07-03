package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Locale;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.RouteManager;

public class    MapActivity extends ActionBarActivity {
    public static final int DEFAULT_ZOOM = 18;
    private static final String MAP_QUEST_API_KEY = "Fmjtd%7Cluur206a2d%2C82%3Do5-9at0dr"; //TODO request key for Artoria, this key now is 'licensed' to Dieter Beelaert
    public static final String LANG_ENG = "en_GB";
    public static final String LANG_NL = "nl_NL";
    private MapView mapView;
    private boolean showsMap = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView)findViewById(R.id.mapview);
        initGUI();
    }

    private void initGUI(){
        /*Setup map and draw route*/
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //disable hardware acceleration, known issue of osmdroid bonus pack see: https://code.google.com/p/osmbonuspack/issues/detail?id=16
        MapController mapCtrl = (MapController) mapView.getController();
        mapCtrl.setZoom(DEFAULT_ZOOM);
        mapCtrl.setCenter(new GeoPoint(DataManager.BELFORT_LAT,DataManager.BELFORT_LON));
        new RouteCalcTask().execute();

        /*Initially show map*/
        toggleMap(true);

        final Button btnTogglemap = (Button)findViewById(R.id.btnRouteDesc);
        btnTogglemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showsMap = !showsMap;
                toggleMap(showsMap);
                if(showsMap){
                   btnTogglemap.setText(getResources().getString(R.string.route_desc));
                }else{
                   btnTogglemap.setText(getResources().getString(R.string.show_map));
                }
            }
        });

        final Button btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this,RouteActivity.class);
                startActivity(i);
            }
        });

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
        protected Road doInBackground(Object[] objects) {
            RoadManager roadManager = new MapQuestRoadManager(MAP_QUEST_API_KEY);
            //RoadManager roadManager = new OSRMRoadManager();
            roadManager.addRequestOption("routeType=pedestrian");
            String lang = DataManager.getInstance().getCurrentLanguage() == DataManager.Language.ENGLISH ? LANG_ENG : LANG_NL;
            roadManager.addRequestOption("locale="+lang );
            Road road = roadManager.getRoad(getGeoPointsFromRoute());
            return road;
        }

        @Override
        protected void onPostExecute(Object result){
            Road road = (Road)result;
            Polyline routeOverlay = RoadManager.buildRoadOverlay(road, MapActivity.this);
            initRouteInstructions(road);
            mapView.getOverlays().add(routeOverlay);
            mapView.invalidate();
        }
    }

    private void initRouteInstructions(Road road){
        if(road.mNodes != null) {
            final TextView lblRouteDesc = (TextView)findViewById(R.id.lblRouteDesc);
            StringBuilder sb = new StringBuilder();
            for (RoadNode node : road.mNodes) {
                //System.out.println(node.mInstructions);
                sb.append(node.mInstructions);
                sb.append("\r\n");
            }
            lblRouteDesc.setText(sb.toString());
        }
    }


    private void toggleMap(boolean showMap){
        final MapView mapview = (MapView)findViewById(R.id.mapview);
        mapview.setVisibility( showMap ? View.VISIBLE :View.GONE);
        final LinearLayout cntDesc = (LinearLayout)findViewById(R.id.cntRouteDesc);
        cntDesc.setVisibility( showMap ? View.GONE : View.VISIBLE);
    }

}
