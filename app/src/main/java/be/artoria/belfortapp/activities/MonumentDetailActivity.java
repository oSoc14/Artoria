package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.RouteManager;

public class MonumentDetailActivity extends BaseActivity {
    public final static String ARG_ID = "be.belfort.monumentid";
    private GestureDetectorCompat gDetect;

    private static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_detail);
        id = (Integer) getIntent().getExtras().get(ARG_ID);
        initGui();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
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
        id = item.getItemId();
        if(id == R.id.btnRoute){
           viewRoute();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addToRoute(View view) {
        final RouteManager rm = RouteManager.getInstance();
        final DataManager dm = DataManager.getInstance();
        rm.addWayPoint(dm.getPOIbyID(id));
        Toast.makeText(this,getString(R.string.added_to_route),Toast.LENGTH_SHORT).show();
    }

    public void prevDetail(View view) {
        id = id == 0 ? (DataManager.numberOfPOIs -1) : (id -1);
        initGui();

    }

    public void nextDetail(View view) {
        id = (id +1) % DataManager.numberOfPOIs ;
        initGui();
    }

    public void viewRoute() {
        /*Go to the route overview*/
        final Intent i = new Intent(MonumentDetailActivity.this,RouteActivity.class);
        startActivity(i);
    }

    public void returnToMain(View view) {
        /*Go to the main page*/
        final Intent i = new Intent(MonumentDetailActivity.this,MainActivity.class);
        startActivity(i);
    }

    /*initialize the GUI content and clickhandlers*/
    private void initGui() {
        final DataManager dm = DataManager.getInstance();
        final POI wp = dm.getPOIbyID(id);

        final TextView tvs = (TextView) findViewById(R.id.monument_name_smaller);
        final TextView desc = (TextView) findViewById(R.id.monument_description);
        final ImageView img = (ImageView) findViewById(R.id.imageView);
        final LinearLayout cntMonumentView = (LinearLayout) findViewById(R.id.cntMonumentView);
        final RelativeLayout prgWait = (RelativeLayout) findViewById(R.id.prgWait);
        img.setVisibility(View.GONE);
        prgWait.setVisibility(View.VISIBLE);

        /* Setting the correct data */
        String name = wp.getName();
        getActionBar().setTitle(name);
        tvs.setText(name);
        desc.setMovementMethod(new ScrollingMovementMethod());
        desc.setText(wp.getDescription());
        Picasso.with(this).load(wp.image_link).into(img, new Callback() {
            @Override
            public void onSuccess() {
                switchImages();
            }

            @Override
            public void onError() {
                img.setImageDrawable(getResources().getDrawable(R.drawable.img_not_found));
                switchImages();
                System.out.println("Failed to load image");
            }

            private void switchImages() {
                prgWait.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
            }
        });

        /*Add gesture listener so we can swipe left and right between POI's*/
       gDetect = new GestureDetectorCompat(this,new GestureListener());
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
           return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float horizontalDiff = e2.getX() - e1.getX();
            if(horizontalDiff>0){
                nextDetail(null);
            }else {
                prevDetail(null);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    public static Intent newIntent(Context ctx, int new_id)
    {
        final Intent toReturn = new Intent(ctx,MonumentDetailActivity.class);
        toReturn.putExtra(ARG_ID,new_id);
        return toReturn;

    }
}
