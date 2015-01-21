package be.artoria.belfortapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.FontManager;
import be.artoria.belfortapp.app.SupportManager;
import be.artoria.belfortapp.app.adapters.DescriptionRow;
import be.artoria.belfortapp.app.adapters.MainAdapter;
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
import be.artoria.belfortapp.viewpager.ViewPagerActivity;

public class MainActivity extends BaseActivity {
    private static boolean downloading = false;
    private static boolean downloadingMuseum = false;
    private ProgressDialog plg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.stopWaiting();
        initGui();
    }

    public static void downloadData() {
        if (SupportManager.haveNetworkConnection()) {
            final long lastDownload = PrefUtils.getTimeStampDownloads();
            final long timeSinceLastDownload = System.currentTimeMillis() - lastDownload;
            /* Either there is no last download ( case == 0)
            *  or it is older than 6 hours */
            if (lastDownload == 0 || timeSinceLastDownload > 1000 * 60 * 60 * 6 && !downloading) {
                Log.i(PrefUtils.TAG, "Started downloading in the background");
                new DownloadDataTask().execute(PrefUtils.DATASET_URL);
            }
            final long lastMuseumDownload = PrefUtils.getTimeStampMuseumDownload();
            final long timeSinceLastMuseumDownload = System.currentTimeMillis() - lastDownload;
            if (lastMuseumDownload == 0 || timeSinceLastMuseumDownload > 1000 * 60 * 60 * 6 && !downloadingMuseum) {
                Log.i(PrefUtils.TAG, "Started museum download in the background");
                new DownloadMuseumData().execute(PrefUtils.MUSEUM_URL);
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        this.stopWaiting();
    }

    /*initialize the GUI content and clickhandlers*/
    private void initGui() {
        initMuseumItems();

        //set font type for headings
        final TextView[] textViews =
                {(TextView) findViewById(R.id.txtMuseumTitle),
                        (TextView) findViewById(R.id.txtGhentTitle),
                        (TextView) findViewById(R.id.txtMoreTitle)};
        for (TextView txt : textViews) {
            txt.setTypeface(FontManager.uniSansThin);
        }
        //set font type for items
        final TextView[] textViews_athelas =
                {(TextView) findViewById(R.id.gent_desc),
                        (TextView) findViewById(R.id.gent_museum),
                        (TextView) findViewById(R.id.gent_route),
                        (TextView) findViewById(R.id.more_desc),
                        (TextView) findViewById(R.id.more_parameters)};
        for (TextView tv : textViews_athelas)
            tv.setTypeface(FontManager.athelas);

    }

    public void startMyRoute(View v) {
        startActivity(new Intent(MainActivity.this, NewRouteActivity.class));
    }

    public void startArtoria(View v) {
        final Uri webpage = Uri.parse(getResources().getString(R.string.artoria_url));
        final Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    public void startSettings(View v) {
        startActivity(new Intent(MainActivity.this, LanguageChoiceActivity.class));
    }

    private void startMuseumView(int floor) {
        if (SupportManager.hasMuseumDataInDatabase() || SupportManager.haveNetworkConnection()) {
            setWaiting();
            startActivity(MuseumActivity.createIntent(MainActivity.this, floor));
        }
    }

    public void startPanorama(View view) {
        startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
    }

    public void startBuildings(View view) {
        if (SupportManager.hasMonumentsInDatabase() || SupportManager.haveNetworkConnection()) {
            final Intent intent = new Intent(MainActivity.this, MonumentDetailActivity.class);
            intent.putExtra(MonumentDetailActivity.ARG_ID, 1);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        /* Close the app when pressing back on the main menu */
        finish();
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
            final List<POI> list = gson.fromJson(result, new TypeToken<List<POI>>() {
            }.getType());
            downloading = false;
            if (list == null || list.isEmpty()) {
                Log.e(PrefUtils.TAG, "Downloading failed");
            } else {
                PrefUtils.saveTimeStampDownloads();
                DataManager.clearpois();
                try {
                    DataManager.poidao.open();
                    DataManager.poidao.clearTable();

                    for (POI poi : list) {
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

    private static class DownloadMuseumData extends AsyncTask<String, Void, String> {
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
            final List<FloorExhibit> list = gson.fromJson(result, new TypeToken<List<FloorExhibit>>() {
            }.getType());
            downloadingMuseum = false;
            if (list == null || list.isEmpty()) {
                Log.e(PrefUtils.TAG, "Downloading failed");
            } else {
                PrefUtils.saveTimeStampMuseumDownloads();
                DataManager.clearFloors();
                DataManager.setFloors(exhibitsToFloors(list));

                try {
                    DataManager.museumDAO.open();
                    DataManager.museumDAO.clearTable();
                    System.out.println("started saving to DB");
                    for (FloorExhibit exhibit : list) {
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
            for (final FloorExhibit exhibit : list) {
                // This exhibit is on a floor higher than the current highest floor.
                int highestFloor = result.size() - 1;
                if (exhibit.floor > highestFloor) {
                    // Deal with it.
                    while (exhibit.floor > highestFloor)
                        result.add(new Floor(highestFloor++));
                }
                result.get(exhibit.floor).exhibits.add(exhibit);
            }
            return result;
        }
    }

    private void initMuseumItems() {
        String[] textValues = getResources().getStringArray(R.array.lstMuseum);
        final LinearLayout[] buttons = {
                (LinearLayout) findViewById(R.id.btnFirstChapter),
                (LinearLayout) findViewById(R.id.btnSecondChapter),
                (LinearLayout) findViewById(R.id.btnThirdChapter),
                (LinearLayout) findViewById(R.id.btnFourthChapter)
        };

        final TextView[] txtViews = {
                (TextView) findViewById(R.id.txtFirstChapter),
                (TextView) findViewById(R.id.txtSecondChapter),
                (TextView) findViewById(R.id.txtThirdChapter),
                (TextView) findViewById(R.id.txtFourthChapter),
        };

        final TextView[] nrViews = {
                (TextView) findViewById(R.id.nmbrFirstChapter),
                (TextView) findViewById(R.id.nmbrSecondChapter),
                (TextView) findViewById(R.id.nmbrThirdChapter),
                (TextView) findViewById(R.id.nmbrFourthChapter),
        };

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;

            buttons[i].setClickable(true);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMuseumView(index);
                }
            });

            txtViews[i].setText(textValues[index]);
            txtViews[i].setTypeface(FontManager.athelas);
            nrViews[i].setTypeface(FontManager.athelas);
            buttons[i].setOnTouchListener(new View.OnTouchListener() {
                float x, y;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            this.x = event.getX();
                            this.y = event.getY();
                            return true;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            this.detectMovement(event.getX(), event.getY());
                            return true;
                        default:
                            return false;
                    }
                }

                private void detectMovement(float lastX, float lastY){

                    final float verticalMovement = Math.abs(lastY - this.y);
                    final float horizontalMovement = Math.abs(lastX - this.x);
                    //System.out.println("vertical movement: " + verticalMovement + " Horizontal movement: " + horizontalMovement);
                    //swiped left and it wasn't a scroll?
                    if(((lastX - this.x) <= 0 ) && horizontalMovement > 75){
                        startMuseumView(index);
                    }
                }
            });

        }
    }

    private void setWaiting(){
        this.plg = ProgressDialog.show(MainActivity.this,"",getResources().getString(R.string.wait));
    }

    private void stopWaiting(){
        if(this.plg != null){
            this.plg.hide();
        }
    }

    private MainAdapter getAdapter(Drawable[] images,String[] names){
        final List<DescriptionRow> descriptionRowList = new ArrayList<DescriptionRow>();
        for(int i = 0; i < names.length; i++){
            descriptionRowList.add(new DescriptionRow(images[i],names[i]));
        }
        return new MainAdapter(this,R.layout.main_list_item,descriptionRowList);
    }
}
