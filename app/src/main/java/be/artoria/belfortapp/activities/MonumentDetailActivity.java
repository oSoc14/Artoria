package be.artoria.belfortapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        initGui();
        id = (Integer) getIntent().getExtras().get("id");
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
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

    }

    public void prevDetail(View view) {

    }

    public void nextDetail(View view) {

    }

    public void viewRoute(View view) {
    }

    public void returnToMain(View view) {
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
        tv.setText(wp.name);
        tvs.setText(wp.name);
        desc.setText(wp.description);
        Picasso.with(this).load(wp.image_url).into(img);

    }
}
