package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.DescriptionRow;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.RouteDescAdapter;
import be.artoria.belfortapp.app.RouteManager;
import be.artoria.belfortapp.app.ManeuverType;

public class MapActivity extends ActionBarActivity {
    public static final int DEFAULT_ZOOM = 18;
    private String MAP_QUEST_API_KEY; //TODO request key for Artoria, this key now is 'licensed' to Dieter Beelaert
    public static final String LANG_ENG = "en_GB";
    public static final String LANG_NL = "nl_NL";
    private MapView mapView;
    private boolean showsMap = true;
    //use viewTreeObserver to align the map according to the route issue: #24

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView)findViewById(R.id.mapview);
        MAP_QUEST_API_KEY = getResources().getString(R.string.map_quest_api_key);
        initGUI();
    }

    private void initGUI(){
        /*Setup map and draw route*/
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        //mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //disable hardware acceleration, known issue of osmdroid bonus pack see: https://code.google.com/p/osmbonuspack/issues/detail?id=16
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
            roadManager.addRequestOption("locale="+lang ); //display the directions in the selected language
            roadManager.addRequestOption("unit=k"); //display the distance in kilometers
            Road road = roadManager.getRoad(getGeoPointsFromRoute());
            return road;
        }

        @Override
        protected void onPostExecute(Object result){
            Road road = (Road)result;
            Polyline routeOverlay = RoadManager.buildRoadOverlay(road, MapActivity.this);
            initRouteInstructions(road);
            final TextView lblDistance = (TextView)findViewById(R.id.lblDistance);
            lblDistance.setText(String.format("%.2f", road.mLength)  + "km");
            final TextView lblTime = (TextView)findViewById(R.id.lblTime);
            lblTime.setText(String.format("%.0f",(road.mDuration / 60)) + " " + getResources().getString(R.string.minutes)); //set estimated time in minutes
            mapView.getOverlays().add(routeOverlay);
            mapView.invalidate();
        }
    }

    private void initRouteInstructions(Road road){
        if(road.mNodes != null) {
            //LinearLayout cntDesc = (LinearLayout)findViewById(R.id.cntRouteDesc);
            ListView lstRouteDesc = (ListView)findViewById(R.id.lstRouteDesc);
            List<DescriptionRow> descriptions = new ArrayList<DescriptionRow>();
            for (RoadNode node : road.mNodes) {
                descriptions.add(new DescriptionRow(getIconForManeuver(node.mManeuverType),node.mInstructions));
            }
            lstRouteDesc.setAdapter(new RouteDescAdapter(this,android.R.layout.simple_list_item_1,descriptions));
        }
    }

    /*Get the correct icon for each maneuver*/
    private Drawable getIconForManeuver(int maneuver){
        int toReturn = R.drawable.ic_empty;
        switch(maneuver){
            case ManeuverType.NONE:
            case ManeuverType.TRANSIT_TAKE:
            case ManeuverType.TRANSIT_TRANSFER:
            case ManeuverType.TRANSIT_ENTER:
            case ManeuverType.TRANSIT_EXIT:
            case ManeuverType.TRANSIT_REMAIN_ON:
            case ManeuverType.ENTERING:
            case ManeuverType.BECOMES: toReturn = R.drawable.ic_empty;
                break;

            case ManeuverType.STRAIGHT:
            case ManeuverType.MERGE_STRAIGHT:
            case ManeuverType.RAMP_STRAIGHT:
            case ManeuverType.STAY_STRAIGHT: toReturn = R.drawable.ic_continue;
                break;

            case ManeuverType.ROUNDABOUT1:
            case ManeuverType.ROUNDABOUT2:
            case ManeuverType.ROUNDABOUT3:
            case ManeuverType.ROUNDABOUT4:
            case ManeuverType.ROUNDABOUT5:
            case ManeuverType.ROUNDABOUT6:
            case ManeuverType.ROUNDABOUT7:
            case ManeuverType.ROUNDABOUT8: toReturn = R.drawable.ic_roundabout;
                break;

            case ManeuverType.DESTINATION:
            case ManeuverType.DESTINATION_RIGHT:
            case ManeuverType.DESTINATION_LEFT: toReturn = R.drawable.ic_arrived;
                break;

            case ManeuverType.SLIGHT_LEFT:
            case ManeuverType.EXIT_LEFT:
            case ManeuverType.STAY_LEFT:
            case ManeuverType.MERGE_LEFT: toReturn = R.drawable.ic_slight_left;
                break;

            case ManeuverType.SLIGHT_RIGHT:
            case ManeuverType.EXIT_RIGHT:
            case ManeuverType.STAY_RIGHT:
            case ManeuverType.MERGE_RIGHT: toReturn = R.drawable.ic_slight_right;
                break;

            case ManeuverType.UTURN:
            case ManeuverType.UTURN_LEFT:
            case ManeuverType.UTURN_RIGHT: toReturn = R.drawable.ic_u_turn;
                break;

            case ManeuverType.RAMP_LEFT:
            case ManeuverType.LEFT: toReturn = R.drawable.ic_turn_left;
                break;

            case ManeuverType.RAMP_RIGHT:
            case ManeuverType.RIGHT: toReturn = R.drawable.ic_turn_right;
                break;

            case ManeuverType.SHARP_LEFT: toReturn = R.drawable.ic_sharp_left;
                break;

            case ManeuverType.SHARP_RIGHT : toReturn = R.drawable.ic_sharp_right;
                break;

        }
        return getResources().getDrawable(toReturn);
    }





    private void toggleMap(boolean showMap){
        final MapView mapview = (MapView)findViewById(R.id.mapview);
        mapview.setVisibility( showMap ? View.VISIBLE :View.GONE);
        final ListView cntDesc = (ListView)findViewById(R.id.lstRouteDesc);
        cntDesc.setVisibility( showMap ? View.GONE : View.VISIBLE);
    }

}
