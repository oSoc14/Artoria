package be.artoria.belfortapp.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;


import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
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
