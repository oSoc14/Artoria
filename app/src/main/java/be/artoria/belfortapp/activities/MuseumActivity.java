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
        /* Initialize the current floor */
        Intent i = getIntent();
        int floor = i.getIntExtra(ARG_FLOOR,0);
        currentFloor = DataManager.getFloorList().get(floor);

        imgCnt = (ImageView)findViewById(R.id.imgContent);
        prgWait = (ProgressBar)findViewById(R.id.prgWait);
        txtContent = (TextView)findViewById(R.id.txtContent);

        /* Make the text scrollable */
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        /*Add gesture listener so we can swipe left and right between POI's*/
        gDetect = new GestureDetectorCompat(this,new GestureListener());

        setContent();
    }

    /**
     *
     * @param direction -1 for previous, 1 for the next image
     */
    private void changeImage(int direction){
        setLoading();
        indexOfExhibit = (indexOfExhibit + direction) % currentFloor.exhibits.size();
        indexOfExhibit = indexOfExhibit == -1 ? currentFloor.exhibits.size() -1 : indexOfExhibit;
        setContent();
        setDoneLoading();
    }

    /**
     * Updates this museumActivity to use the current exhibit
     */
    private void setContent() {
        currentExhibit = currentFloor.exhibits.get(indexOfExhibit);

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

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float horizontalDiff = e2.getX() - e1.getX();
            if(horizontalDiff > 0){
                changeImage(-1);
            }else {
                changeImage(1);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
