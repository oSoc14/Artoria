package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.RouteManager;

public class MonumentDetailActivity extends BaseActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_detail);
        id = (Integer) getIntent().getExtras().get("id");
        initGui();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.monument_detail, menu);
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

    public void addToRoute(View view) {
        final RouteManager rm = RouteManager.getInstance();
        final DataManager dm = DataManager.getInstance();
        rm.addWayPoint(dm.poiList.get(id));
        Toast.makeText(this,getString(R.string.added_to_route),Toast.LENGTH_SHORT).show();
    }

    public void prevDetail(View view) {
        /*final Intent intent = new Intent(MonumentDetailActivity.this, MonumentDetailActivity.class);
        intent.putExtra("id", (id - 1 + DataManager.getInstance().poiList.size()) % DataManager.getInstance().poiList.size());
        startActivity(intent);*/
        id = id == 0 ? (DataManager.getInstance().poiList.size() -1) : (id -1);
        initGui();

    }

    public void nextDetail(View view) {
        /*final Intent intent = new Intent(MonumentDetailActivity.this, MonumentDetailActivity.class);
        intent.putExtra("id", (id + 1) % DataManager.getInstance().poiList.size());
        startActivity(intent);*/
        id = (id +1) % DataManager.getInstance().poiList.size();
        initGui();

    }

    public void viewRoute(View view) {
        /*Go to the route overview*/
        final Intent i = new Intent(MonumentDetailActivity.this,MapActivity.class);
        startActivity(i);
    }

    public void returnToMain(View view) {
        /*Go to the main page*/
        final Intent i = new Intent(MonumentDetailActivity.this,MainActivity.class);
        startActivity(i);
    }


    /*initialize the GUI content and clickhandlers*/
    private void initGui(){
        final DataManager rm = DataManager.getInstance();
        final POI wp = rm.poiList.get(id);
        final TextView tv =(TextView) findViewById(R.id.monument_name);
        final TextView tvs =(TextView) findViewById(R.id.monument_name_smaller);
        final TextView desc =(TextView)findViewById(R.id.monument_description);
        final ImageView img = (ImageView) findViewById(R.id.imageView);

        /* Setting the correct data */
        String name = wp.getName();
        tv.setText(name);
        tvs.setText(name);
        desc.setText(wp.getDescription());
        Picasso.with(this).load(wp.image_link).into(img);

    }

    private static class newIntent{
        static int id;
    }
}
