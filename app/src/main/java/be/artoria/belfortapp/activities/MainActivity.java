package be.artoria.belfortapp.activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.SupportManager;
import be.artoria.belfortapp.app.adapters.DescriptionRow;
import be.artoria.belfortapp.app.adapters.MainAdapter;
import be.artoria.belfortapp.fragments.MapFragment;
import be.artoria.belfortapp.mixare.MixView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.PrefUtils;

public class MainActivity extends BaseActivity {
    MainAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGui();
    }

    private static boolean downloading = false;
    private static boolean downloadingMuseum = false;

    public static void downloadData() {
        if(SupportManager.haveNetworkConnection()) {
            final long lastDownload = PrefUtils.getTimeStampDownloads();
            final long timeSinceLastDownload = System.currentTimeMillis() - lastDownload;
            /* Either there is no last download ( case == 0)
            *  or it is older than 12 hours, which is 43200000 milliseconds according to google */
            if (lastDownload == 0 || timeSinceLastDownload > 1000 * 60 * 60 * 6 && !downloading ) {
                Log.i(PrefUtils.TAG, "Started downloading in the background");
                new DownloadDataTask().execute(PrefUtils.DATASET_URL);
            }
            final long lastMuseumDownload = PrefUtils.getTimeStampMuseumDownload();
            final long timeSinceLastMuseumDownload = System.currentTimeMillis() - lastDownload;
            if (lastMuseumDownload == 0 || timeSinceLastMuseumDownload > 1000 * 60 * 60 * 6 && ! downloadingMuseum) {
                Log.i(PrefUtils.TAG, "Started museum download in the background");
                new DownloadMuseumData().execute(PrefUtils.MUSEUM_URL);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    /*initialize the GUI content and clickhandlers*/
    private void initGui(){
        final ListView lstMenu = (ListView)findViewById(R.id.lstMenu);
        final Button btnSettings = (Button)findViewById(R.id.btnSettings);
        final Button btnAbout = (Button)findViewById(R.id.btnAbout);
        final String[] strings = getResources().getStringArray(R.array.lstMenu);
        final Drawable[] drawables = new Drawable[]{
                getResources().getDrawable((R.drawable.panorama)),
                getResources().getDrawable((R.drawable.menu)),
                getResources().getDrawable((R.drawable.route)),
                getResources().getDrawable((R.drawable.route)),
                getResources().getDrawable((R.drawable.route)),
                getResources().getDrawable((R.drawable.route)),
                getResources().getDrawable((R.drawable.route)),
                getResources().getDrawable((R.drawable.route))
        };
        final List<DescriptionRow> list = new ArrayList<DescriptionRow>();
        for (int i = 0; i < strings.length; i++) {
            list.add(new DescriptionRow(drawables[i],strings[i]));
        }



        menuAdapter = new MainAdapter(this,R.layout.main_list_item,list);
        lstMenu.setAdapter(menuAdapter);

        lstMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent;
                switch (i) {
                    /* The first item is the be.artoria.belfortapp.mixare panorama */
                    case 0:
                        if(SupportManager.isDeviceSupported()) {
                            intent = new Intent(MainActivity.this, MixView.class);
                            startActivity(intent);
                        }
                    break;
                    /* The second item are the buildings */
                    case 1:
                        if(SupportManager.hasMonumentsInDatabase() || SupportManager.haveNetworkConnection()) {
                            intent = new Intent(MainActivity.this, MonumentDetailActivity.class);
                            intent.putExtra(MonumentDetailActivity.ARG_ID, 1);
                            startActivity(intent);
                        }
                    break;
                    /* The third item is my route */
                    case 2:
                        intent = new Intent(MainActivity.this,NewRouteActivity.class);
                        startActivity(intent);
                        break;
                    //4th floor
                    case 3: startMuseumView(4);
                        break;
                    //3rd floor
                    case 4: startMuseumView(3);
                        break;
                    //2nd floor
                    case 5: startMuseumView(2);
                        break;
                    //1st floor
                    case 6: startMuseumView(1);
                        break;
                    //ground floor
                    case 7: startMuseumView(0);
                        break;
                }
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Go to settings*/
                final Intent i = new Intent(MainActivity.this, LanguageChoiceActivity.class);
                startActivity(i);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Go to the Artoria website*/
                final Uri webpage = Uri.parse(getResources().getString(R.string.artoria_url));
                final Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });

    }

    private void startMuseumView(int floor){
        if(SupportManager.hasMuseumDataInDatabase() || SupportManager.haveNetworkConnection()){
            startActivity(MuseumActivity.createIntent(MainActivity.this,floor));
        }
    }
    private static class DownloadDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            downloading = true;
            StringBuilder response = new StringBuilder();
            for (String url : urls) {
                final DefaultHttpClient client = new DefaultHttpClient();
                final HttpGet httpGet = new HttpGet(url);
                try {
                    final HttpResponse execute = client.execute(httpGet);
                    final InputStream content = execute.getEntity().getContent();

                    final BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s;
                    while ((s = buffer.readLine()) != null) {
                        response.append(s);
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            final Gson gson = new Gson();
            final List<POI> list = gson.fromJson(result, new TypeToken<List<POI>>(){}.getType());
            downloading = false;
            if(list == null || list.isEmpty()){
                Log.e(PrefUtils.TAG ,"Downloading failed");
            }
            else {
                PrefUtils.saveTimeStampDownloads();
                DataManager.clearpois();
                try {
                    DataManager.poidao.open();
                    DataManager.poidao.clearTable();

                for(POI poi : list){
                    DataManager.poidao.savePOI(poi);
                }
                DataManager.addAll(list);
                DataManager.poidao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
              }
            }
        }
    }

    private static class DownloadMuseumData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            downloadingMuseum = true;
            StringBuilder response = new StringBuilder();
            for (String url : urls) {
                final DefaultHttpClient client = new DefaultHttpClient();
                final HttpGet httpGet = new HttpGet(url);
                try {
                    final HttpResponse execute = client.execute(httpGet);
                    final InputStream content = execute.getEntity().getContent();

                    final BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s;
                    while ((s = buffer.readLine()) != null) {
                        response.append(s);
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            final Gson gson = new Gson();
            final List<FloorExhibit> list = gson.fromJson(result, new TypeToken<List<FloorExhibit>>(){}.getType());
            downloadingMuseum = false;
            System.out.println("size = " +list.size());
            if(list == null || list.isEmpty()){
                Log.e(PrefUtils.TAG ,"Downloading failed");
            }
            else {
                PrefUtils.saveTimeStampMuseumDownloads();
                DataManager.clearFloors();
                DataManager.setFloors(exhibitsToFloors(list));

                try {
                    DataManager.museumDAO.open();
                    System.out.println("started saving to DB");
                    for(FloorExhibit exhibit : list) {
                        DataManager.museumDAO.saveFloorExhibit(exhibit);
                        System.out.println("saved " + exhibit.getName());
                    }
                    DataManager.museumDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

        private List<Floor> exhibitsToFloors(List<FloorExhibit> list) {
            final List<Floor> result = new ArrayList<Floor>();
            for(final FloorExhibit exhibit : list){
                // This exhibit is on a floor higher than the current highest floor.
                int highestFloor =  result.size() - 1;
                if(exhibit.floor > highestFloor){
                    // Deal with it.
                    while(exhibit.floor > highestFloor )
                        result.add(new Floor(highestFloor++));
                }
                result.get(exhibit.floor).exhibits.add(exhibit);
            }
            return result;
        }
    }


}
