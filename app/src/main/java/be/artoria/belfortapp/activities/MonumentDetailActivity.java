package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.view.OrientationEventListener;
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
import be.artoria.belfortapp.app.ScreenUtils;
import be.artoria.belfortapp.mixare.MixView;

public class MonumentDetailActivity extends BaseActivity {
    public final static String ARG_ID = "be.belfort.monumentid";
    public final static String ARG_FROM_PANORAMA = "be.belfort.fromPanorama";
    public final static String ARG_USER_INSTANTIATED = "be.belfort.userInstantiated"; //used to handle the tilting problem
    private GestureDetectorCompat gDetect;
    private boolean fromPanorama;

    private static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_detail);
        boolean userInstantiated = getIntent().getBooleanExtra(ARG_USER_INSTANTIATED,false);
        id = userInstantiated ? (Integer) getIntent().getExtras().get(ARG_ID) : DataManager.lastViewedPOI;
        fromPanorama = getIntent().getBooleanExtra(ARG_FROM_PANORAMA,false);
        initGui(fromPanorama);
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
            return true;
        }
        if(id == android.R.id.home){
           if(fromPanorama){
               backToPanorama();
               return true;
           }
        }
        return super.onOptionsItemSelected(item);
    }

    public void addToRoute(View view) {
        final RouteManager rm = RouteManager.getInstance();
        final DataManager dm = DataManager.getInstance();
        rm.addWayPoint(dm.getPOIbyID(id));
        Toast.makeText(this,getString(R.string.added_to_route),Toast.LENGTH_SHORT).show();
        if(fromPanorama){
           backToPanorama();
        }
    }

    public void prevDetail(View view) {
        id = id == 0 ? (DataManager.numberOfPOIs -1) : (id -1);
        DataManager.lastViewedPOI = id;
        initGui(false);

    }

    public void nextDetail(View view) {
        id = (id +1) % DataManager.numberOfPOIs ;
        DataManager.lastViewedPOI = id;
        initGui(false);
    }

    public void viewRoute() {
        /*Go to the route overview*/
        final Intent i = new Intent(MonumentDetailActivity.this,NewRouteActivity.class);
        startActivity(i);
    }

    public void returnToMain(View view) {
        /*Go to the main page*/
        final Intent i = new Intent(MonumentDetailActivity.this,MainActivity.class);
        startActivity(i);
    }

    /*initialize the GUI content and clickhandlers*/
    private void initGui(boolean fromPanorama) {
        this.fromPanorama = fromPanorama;
        final DataManager dm = DataManager.getInstance();
        final POI wp = dm.getPOIbyID(id);

        final TextView tvs = (TextView) findViewById(R.id.monument_name_smaller);
        final TextView desc = (TextView) findViewById(R.id.monument_description);
        final ImageView img = (ImageView) findViewById(R.id.imageView);
        final ImageView imgType = (ImageView)findViewById(R.id.imgType);
        final RelativeLayout prgWait = (RelativeLayout) findViewById(R.id.prgWait);
        img.setVisibility(View.GONE);
        imgType.setVisibility(View.GONE);
        prgWait.setVisibility(View.VISIBLE);
        desc.scrollTo(0,0);//Scroll back to the top of the view
        /* Setting the correct data */
        String name = wp.getName();
        getActionBar().setTitle(name);
        tvs.setText(name);
        desc.setMovementMethod(new ScrollingMovementMethod());
        desc.setText(wp.getDescription());
        imgType.setImageDrawable(POI.getTypePopupImg(wp.type,this));

        /*if the device is a phone, the screen can't be tilted see #66 on GitHub*/
        if(!ScreenUtils.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        }

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
                imgType.setVisibility(View.VISIBLE);
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

    public static Intent newIntent(Context ctx, int new_id, boolean fromPanorama)
    {
        final Intent toReturn = new Intent(ctx,MonumentDetailActivity.class);
        toReturn.putExtra(ARG_ID,new_id);
        toReturn.putExtra(ARG_FROM_PANORAMA,fromPanorama);
        toReturn.putExtra(ARG_USER_INSTANTIATED,true);
        return toReturn;

    }

    private void backToPanorama(){
        Intent i = new Intent(this, MixView.class);
        startActivity(i);
    }
}
