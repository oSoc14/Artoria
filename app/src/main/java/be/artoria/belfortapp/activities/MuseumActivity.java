package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.PrefUtils;

public class MuseumActivity extends BaseActivity {
    public static final String ARG_FLOOR = "be.artoria.MuseumActivity.floor";
    private Floor currentFloor;
    private ImageView imgCnt;
    private ProgressBar prgWait;
    private TextView txtContent;
    private FloorExhibit currentExhibit = null;
    private int indexOfExhibit = 0;
    private GestureDetectorCompat gDetect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);

        initGui();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void initGui(){

        Intent i = getIntent();
        int floor = i.getIntExtra(ARG_FLOOR,0);

        currentFloor = DataManager.getFloorList().get(floor);
        currentExhibit = currentFloor.exhibits.get(indexOfExhibit);

        txtContent = (TextView)findViewById(R.id.txtContent);
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        imgCnt = (ImageView)findViewById(R.id.imgContent);
        prgWait = (ProgressBar)findViewById(R.id.prgWait);

        /*Add gesture listener so we can swipe left and right between POI's*/
        gDetect = new GestureDetectorCompat(this,new GestureListener());

        setContent();
    }


    private void nextImage(){
        setLoading();
        indexOfExhibit = (indexOfExhibit + 1) % currentFloor.exhibits.size();
        setContent();
        setDoneLoading();
    }

    private void prevImage(){
        setLoading();
        indexOfExhibit = (indexOfExhibit + 1) % currentFloor.exhibits.size();
        setContent();
        setDoneLoading();
    }

    private void setContent() {
        setTitle(currentExhibit.getName());

        txtContent.setText(currentExhibit.getDescription());

        Picasso.with(PrefUtils.getContext()).load(currentExhibit.image).into(imgCnt);
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

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float horizontalDiff = e2.getX() - e1.getX();
            if(horizontalDiff > 0){
                prevImage();
            }else {
                nextImage();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
