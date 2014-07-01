package be.artoria.belfortapp.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Waypoint;

public class MonumentDetailActivity extends ActionBarActivity {
    static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_detail);
        id = (Integer) getIntent().getExtras().get("id");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            DataManager dm = DataManager.getInstance();
            Waypoint wp = dm.wayPoints.get(id);
            View rootView = inflater.inflate(R.layout.fragment_monument_detail, container, false);
            TextView tv =(TextView) rootView.findViewById(R.id.monument_name);
            TextView tvs =(TextView) rootView.findViewById(R.id.monument_name_smaller);
            tv.setText(wp.name);
            tvs.setText(wp.name);

            ImageView img = (ImageView) rootView.findViewById(R.id.imageView);
            try {
                URL url = new URL(wp.url);
                HttpGet httpRequest = null;
                httpRequest = new HttpGet(url.toURI());

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = (HttpResponse) httpclient
                        .execute(httpRequest);

                HttpEntity entity = response.getEntity();
                BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                InputStream input = b_entity.getContent();

                Bitmap bitmap = BitmapFactory.decodeStream(input);

                img.setImageBitmap(bitmap);

            } catch (Exception ex) {
                /* Lol hackathon */
            }

            return rootView;
        }
    }
}
