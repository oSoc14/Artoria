package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
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
import be.artoria.belfortapp.app.adapters.ExhibitAdapter;
import be.artoria.belfortapp.app.adapters.ImageAdapter;
import be.artoria.belfortapp.viewflow.CircleFlowIndicator;
import be.artoria.belfortapp.viewflow.ViewFlow;

public class MuseumActivity extends BaseActivity {
    public static final String ARG_FLOOR = "be.artoria.MuseumActivity.floor";
    private ViewFlow viewFlow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);

        initGui();
    }

    private void initGui(){
        /* Initialize the current floor */
        Intent i = getIntent();
        int floor = i.getIntExtra(ARG_FLOOR,0);

        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        ExhibitAdapter adapter = new ExhibitAdapter(this,DataManager.getFloorList().get(floor).exhibits);
        //viewFlow.setAdapter(new ImageAdapter(this));
        viewFlow.setAdapter(adapter);
        final CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        viewFlow.setFlowIndicator(indic);
        indic.setFillColor(Color.BLACK);
        indic.setStrokeColor(Color.BLACK);
        indic.setSoundEffectsEnabled(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        viewFlow.onConfigurationChanged(newConfig);
    }

    public static Intent createIntent(Context ctx, int floor){
        Intent i = new Intent(ctx,MuseumActivity.class);
        i.putExtra(ARG_FLOOR,floor);
        return i;
    }
}
