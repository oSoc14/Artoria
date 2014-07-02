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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
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
        initData();
        final ListView lstRoute = (ListView)findViewById(R.id.lstRoute);
        final Button btnCalcRoute = (Button)findViewById(R.id.btnCalcRoute);

        /*TODO make the list sortable, this might be interesting: http://jasonmcreynolds.com/?p=423 */
        routeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getWaypointsAsStringList());
        lstRoute.setAdapter(routeAdapter);

        btnCalcRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Calculate route from belfort to these points*/
                /*Google maps or open street map*/
                DataManager mgr = DataManager.getInstance();

                if(mgr.wayPoints != null) {
                    if(!mgr.wayPoints.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("http://maps.google.com/maps?f=d&hl=en&saddr=");
                        sb.append(mgr.BELFORT_LAT);
                        sb.append(",");
                        sb.append(mgr.BELFORT_LON);
                        sb.append("&daddr=");
                        sb.append(mgr.wayPoints.get(0).lat);
                        sb.append(",");
                        sb.append(mgr.wayPoints.get(0).lon);
                        for(int i = 1; i < mgr.wayPoints.size();i++){
                            sb.append("+to:");
                            sb.append(mgr.wayPoints.get(i).lat);
                            sb.append(",");
                            sb.append(mgr.wayPoints.get(i).lon);
                        }

                        System.out.println("URL maps: " + sb.toString() );
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(sb.toString()));
                        //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    /*Initialize route data*/
    private void initData(){
        DataManager manager = DataManager.getInstance();
        if(manager.wayPoints == null){
            /* Get the route from shared preferences */
            manager.wayPoints = new ArrayList<Waypoint>();
            /*Test data*/
            //id, lat, lon, name, desc
            manager.wayPoints.add(new Waypoint(0,51.053939, 3.722958,"Sint-Niklaaskerk",""));
            manager.wayPoints.add(new Waypoint(0,51.053952, 3.722196,"Korenmarkt",""));
            manager.wayPoints.add(new Waypoint(0,51.054562, 3.724862,"Stadhuis",""));
        }
    }

    private List<String> getWaypointsAsStringList(){
      final List<String> toReturn = new ArrayList<String>();
      for(Waypoint w : DataManager.wayPoints){
          toReturn.add(w.name);
      }
      return toReturn;
    }
}
