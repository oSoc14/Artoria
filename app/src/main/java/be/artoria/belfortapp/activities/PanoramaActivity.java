package be.artoria.belfortapp.activities;

import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import be.artoria.belfortapp.R;

/**
 * Created by Laurens on 20/01/2015.
 */
public class PanoramaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        initGui();
    }

    private void initGui() {
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.PanoramaView);
        imageView.setImageResource(R.drawable.bangkokpanorama);
    }
}
