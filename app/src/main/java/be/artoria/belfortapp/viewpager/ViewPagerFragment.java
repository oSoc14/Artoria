/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package be.artoria.belfortapp.viewpager;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.Arrays;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.PrefUtils;
import be.artoria.belfortapp.extension.CircledPOI;
import be.artoria.belfortapp.extension.views.CircleView;

public class ViewPagerFragment extends Fragment {

    private static final String BUNDLE_ASSET = "be.artoria.belfortapp.viewpager.ViewPagerFragment.asset";
    private static final String BUNDLE_STATE = "be.artoria.belfortapp.viewpager.ViewPagerFragment.state";

    private int resId = -1;

    public ViewPagerFragment() {
    }

    public ViewPagerFragment(int resId) {
        this.resId = resId;
            Bundle args = new Bundle();
            args.putInt(BUNDLE_ASSET, resId);
            setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_pager_page, container, false);
        if (savedInstanceState != null) {
            if (resId == -1 && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                resId = savedInstanceState.getInt(BUNDLE_ASSET);
            }
        }
        if (resId == -1) {
            return rootView;
        }

        final CircleView imageView = (CircleView)rootView.findViewById(R.id.PanormaCirclePageView);

        imageView.addCircles(Arrays.asList(
                new CircledPOI(1, 0.1f, 500, 200),
                new CircledPOI(2, 0.2f, 600, 300),
                new CircledPOI(3, 0.3f, 1000, 400)
        ));
        imageView.setImageResource(resId,imageViewState);
        imageView.setScaleAndCenter(0.5f, imageView.getCenter());
        imageView.setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_INSIDE);
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);

        return rootView;

    }

    /**
     * Used to save the state when changing the orientation of the panorama view.
     * @param outState
     **/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        View rootView = getView();
        if (rootView != null) {
            SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)rootView.findViewById(R.id.PanormaCirclePageView);
            outState.putInt(BUNDLE_ASSET, resId);
            outState.putSerializable(BUNDLE_STATE, imageView.getState());
        }
    }

    private ImageViewState imageViewState = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restoring when changing orientation.

        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_STATE)) {
            imageViewState = (ImageViewState)savedInstanceState.getSerializable(BUNDLE_STATE);
        }
    }
}
