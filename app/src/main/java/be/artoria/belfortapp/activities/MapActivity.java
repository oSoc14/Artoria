package be.artoria.belfortapp.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.POI;

public class MapActivity extends ActionBarActivity {
    public static final int DEFAULT_ZOOM = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapView mapView = (MapView)findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        MapController mapCtrl = (MapController) mapView.getController();
        mapCtrl.setZoom(DEFAULT_ZOOM);
        mapCtrl.setCenter(new GeoPoint(DataManager.BELFORT_LAT,DataManager.BELFORT_LON));

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


}
