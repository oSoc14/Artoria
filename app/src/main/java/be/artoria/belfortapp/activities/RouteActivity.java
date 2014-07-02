package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.RouteManager;
import be.artoria.belfortapp.app.Waypoint;



public class RouteActivity extends BaseActivity {
    private ArrayAdapter<String> routeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initGUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.route, menu);
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

    /*Initialize GUI */
    private void initGUI(){
        ListView lstRoute = (ListView)findViewById(R.id.lstRoute);
        Button btnCalcRoute = (Button)findViewById(R.id.btnCalcRoute);


        /*TODO make the list sortable, this might be interesting: http://jasonmcreynolds.com/?p=423 */
        routeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getWaypointsAsStringList());
        lstRoute.setAdapter(routeAdapter);

        btnCalcRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RouteActivity.this,MapActivity.class);
                startActivity(i);

            }
        });
    }


    private List<String> getWaypointsAsStringList(){
      List<String> toReturn = new ArrayList<String>();
      for(Waypoint w : RouteManager.getInstance().getWaypoints()){
          toReturn.add(w.name);
      }
      return toReturn;
    }
}
