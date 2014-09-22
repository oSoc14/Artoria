package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.NavigationCircles;
import be.artoria.belfortapp.app.PrefUtils;

public class MuseumActivity extends BaseActivity {
    public static final String ARG_FLOOR = "be.artoria.MuseumActivity.floor";
    public static final long IMAGE_SWITCH_TIME = 5000;
    private Floor currentFloor;
    private ImageView imgCnt;
    private ProgressBar prgWait;
    private NavigationCircles circles;
    private FloorExhibit currentexhbit = null;
    private int indexOfExhibit = 0;
    private Handler handler;
    private boolean runSlideshow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);
        initGui();
    }

    private void initGui(){
        TextView txtContent = (TextView)findViewById(R.id.txtContent);
        txtContent.setMovementMethod(new ScrollingMovementMethod());
        imgCnt = (ImageView)findViewById(R.id.imgContent);

        prgWait = (ProgressBar)findViewById(R.id.prgWait);


        Intent i = getIntent();
        int floor = i.getIntExtra(ARG_FLOOR,0);
        currentFloor = DataManager.getFloorList().get(floor);
        currentexhbit = currentFloor.exhibits.get(indexOfExhibit);
        setTitle(currentexhbit.getName());

        txtContent.setText(currentexhbit.getDescription());

        imgCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runSlideshow = false;
                nextImage();
            }
        });

        handler = new Handler();
        handler.postDelayed(imageSwitcher,0);

        /* Adding the circles, circle representing current item is empty */
        circles = (NavigationCircles) findViewById(R.id.circles);
        circles.setNumberOfCircles(currentFloor.exhibits.size());
    }

    private Runnable imageSwitcher = new Runnable() {
        @Override
        public void run() {
            if(runSlideshow) {
                nextImage();
                handler.postDelayed(this, IMAGE_SWITCH_TIME);
            }
        }
    };

    private void nextImage(){
        setLoading();
        Picasso.with(PrefUtils.getContext()).load(currentexhbit.image).into(imgCnt, new Callback() {
            @Override
            public void onSuccess() {
                setDoneLoading();
                indexOfExhibit = (indexOfExhibit + 1) % currentFloor.exhibits.size();
            }

            @Override
            public void onError() {
                setDoneLoading();
                System.err.println("Failed loading image ...");
                imgCnt.setImageDrawable(getResources().getDrawable(R.drawable.img_not_found));
            }
        });
        circles.setSelectedCircle((indexOfExhibit +1) % currentFloor.exhibits.size());
    }

    private void setLoading(){
        imgCnt.setVisibility(View.GONE);
        prgWait.setVisibility(View.VISIBLE);
    }

    private void setDoneLoading(){
        prgWait.setVisibility(View.GONE);
        imgCnt.setVisibility(View.VISIBLE);
    }

    public static Intent createIntent(Context ctx, int floor){
        Intent i = new Intent(ctx,MuseumActivity.class);
        i.putExtra(ARG_FLOOR,floor);
        return i;
    }
}
