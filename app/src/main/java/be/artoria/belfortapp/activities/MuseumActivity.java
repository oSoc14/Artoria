package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.PrefUtils;

public class MuseumActivity extends BaseActivity {
    public static final String ARG_FLOOR = "be.artoria.MuseumActivity.floor";
    private static final int MUSEUM_TITLE_SIZE = 32;
    private static final int IMAGE_HEIGHT = 350;
    private Floor currentFloor;
    private int currentFloorIndex;

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
        currentFloorIndex = i.getIntExtra(ARG_FLOOR,0);
        currentFloor = DataManager.getFloorList().get(currentFloorIndex);
        LinearLayout lnrMuseum = (LinearLayout)findViewById(R.id.lnrMuseum);
        lnrMuseum.removeAllViews();

        for(int j = 0; j < currentFloor.exhibits.size();j++){
            addExhibit(currentFloor.exhibits.get(j),lnrMuseum,currentFloorIndex,j+1);
        }

        //next floor button ...
        final LinearLayout btnNextFloor = (LinearLayout)findViewById(R.id.btnNextFloor);
        btnNextFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nextFloor();
            }
        });

        //TODO is not working fix this ...
        /*Add gesture listener so we can swipe left and right between POI's*/
        gDetect = new GestureDetectorCompat(this,new GestureListener());

        //set the title of the activity to the right floor name, strange way because the array is reversed ...
        String[] floorNames = getResources().getStringArray(R.array.lstMuseum);
        setTitle(floorNames[(floorNames.length -1) - currentFloorIndex]);
        System.out.println((floorNames.length -1) - currentFloorIndex);
    }

    public static Intent createIntent(Context ctx, int floor){
        Intent i = new Intent(ctx,MuseumActivity.class);
        i.putExtra(ARG_FLOOR,floor);
        return i;
    }

    private void addExhibit(FloorExhibit ex, View parentView, int floor, int exhibit){
        final LinearLayout parent = (LinearLayout) parentView;
        final LinearLayout lnrTitle = new LinearLayout(this);
        lnrTitle.setOrientation(LinearLayout.HORIZONTAL);

        final ProgressBar prgWait = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        prgWait.setVisibility(View.VISIBLE);
        prgWait.setMinimumHeight(IMAGE_HEIGHT);

        final ImageView img = new ImageView(this);
        img.setMaxHeight(IMAGE_HEIGHT);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setAdjustViewBounds(true);
        img.setVisibility(View.GONE);


        Picasso.with(this).load(ex.getImage()).into(img, new Callback() {
            @Override
            public void onSuccess() {
                prgWait.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                System.out.println("Image successfully loaded and displayed");
            }

            @Override
            public void onError() {
                prgWait.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                img.setImageDrawable(getResources().getDrawable(R.drawable.img_not_found));
            }
        });

        final TextView txtNumber = new TextView(this);
        txtNumber.setTextSize(MUSEUM_TITLE_SIZE);
        txtNumber.setTextColor(Color.GRAY);
        txtNumber.setText(floor + "." + exhibit +"  ");

        final TextView txtTitle = new TextView(this);
        txtTitle.setTextSize(MUSEUM_TITLE_SIZE);
        txtTitle.setText(ex.getName());

        lnrTitle.addView(txtNumber);
        lnrTitle.addView(txtTitle);

        final TextView txtContent = new TextView(this);
        txtContent.setText(ex.getDescription());
        /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)txtContent.getLayoutParams();
        params.setMargins(0, 0, 0, 20);
        txtContent.setLayoutParams(params);*/

        parent.addView(prgWait);
        parent.addView(img);
        parent.addView(lnrTitle);
        parent.addView(txtContent);
    }

    private void nextFloor(){
        int next = (currentFloorIndex +1) % DataManager.getFloorList().size();
        startNewMuseumActivity(next);
    }

    private void previousFloor(){
        int prev = (currentFloorIndex -1) % DataManager.getFloorList().size();
        startNewMuseumActivity(prev);
    }

    private void startNewMuseumActivity(int floor){
        Intent toStart = createIntent(this,floor);
        startActivity(toStart);
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
                previousFloor();
            }else {
                nextFloor();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
