package be.artoria.belfortapp.activities;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ResourceBundle;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.MuseumImageMapper;
import be.artoria.belfortapp.app.PrefUtils;
import be.artoria.belfortapp.app.adapters.ExhibitAdapter;
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

        ActionBar actionBar = getActionBar();
        actionBar.hide();
        Intent i = getIntent();
        int floor = i.getIntExtra(ARG_FLOOR,0);
        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        ExhibitAdapter adapter = new ExhibitAdapter(this,DataManager.getFloorList().get(0).exhibits);
        viewFlow.setAdapter(adapter,floor);
        final CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        viewFlow.setFlowIndicator(indic);
        indic.setFillColor(Color.WHITE);
        indic.setStrokeColor(Color.WHITE);
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
