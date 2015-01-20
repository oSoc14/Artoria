package be.artoria.belfortapp.activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import be.artoria.belfortapp.R;

/**
 * Created by Laurens on 20/01/2015.
 */
public class PanoramaActivity extends BaseActivity {

    private static final String BUNDLE_STATE = "ImageViewState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        // Restoring when changing orientation.
        ImageViewState imageViewState = null;
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_STATE)) {
            imageViewState = (ImageViewState)savedInstanceState.getSerializable(BUNDLE_STATE);
        }

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.PanoramaView);
        imageView.setImageResource(R.drawable.bangkokpanorama, imageViewState);
    }

    /**
     * Used to save the state when changing the orientation of the panorama view.
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.PanoramaView);
        ImageViewState state = imageView.getState();
        if (state != null) {
            outState.putSerializable(BUNDLE_STATE, imageView.getState());
        }
    }
}
