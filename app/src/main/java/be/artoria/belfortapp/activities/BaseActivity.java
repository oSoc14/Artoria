package be.artoria.belfortapp.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Waypoint;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        /*Temporary !!*/
        DataManager manager = DataManager.getInstance();
        manager.wayPoints = new ArrayList<Waypoint>();
            /*Test data*/
        //id, lat, lon, name, desc
        manager.wayPoints.add(new Waypoint(0,51.053939, 3.722958,"Sint-Niklaaskerk",""));
        manager.wayPoints.add(new Waypoint(0,51.053952, 3.722196,"Korenmarkt",""));
        manager.wayPoints.add(new Waypoint(0,51.054562, 3.724862,"Stadhuis",""));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
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
