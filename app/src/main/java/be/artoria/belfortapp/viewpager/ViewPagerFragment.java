/*
Copyright 2014 David Morrissey, Adapted by Laurens De Graeve

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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.extension.CircledPOI;
import be.artoria.belfortapp.extension.views.CircleView;

public class ViewPagerFragment extends Fragment {

    private static final String BUNDLE_POSITION = "be.artoria.belfortapp.viewpager.ViewPagerFragment.position";
    private static final String BUNDLE_STATE = "be.artoria.belfortapp.viewpager.ViewPagerFragment.state";
    //private static final int[] IMAGES = { R.drawable.north, R.drawable.east, R.drawable.west, R.drawable.west  };
    private static final int [] IMAGES = {R.drawable.south,R.drawable.east,R.drawable.north,R.drawable.west};

    private static final List<Collection<CircledPOI>> CIRCLES_TO_PANORAMA ;

    static{
        Collection<CircledPOI> north_buildings =  Arrays.asList(
                new CircledPOI(0,0.036f,7266,2514),
                new CircledPOI(1,0.02f,1549,1286),
                new CircledPOI(2,0.036f,7945,1350),
                new CircledPOI(11,0.036f,7945,1350),
                new CircledPOI(4,0.052f,13764,2454),
                new CircledPOI(11,0.038f,6717,1089));
        Collection<CircledPOI> east_buildings =  Arrays.asList(
                new CircledPOI(3,0.085f,6941,1940),
                new CircledPOI(6,0.019f,12201,1999),
                new CircledPOI(7,0.01f,12554,1850));
        Collection<CircledPOI> south_buildings =  Arrays.asList(
                new CircledPOI(7,0.012f,2085,835),
                new CircledPOI(6,0.02f, 1725,955),
                new CircledPOI(8,0.01f,6019,917));
        Collection<CircledPOI> west_buildings =  Arrays.asList(
                new CircledPOI(10,0.06f,5524,1800),
                new CircledPOI(1,0.02f,9363,1356),
                new CircledPOI(9,0.02f,8251,3732));


        CIRCLES_TO_PANORAMA = Arrays.asList(south_buildings,east_buildings,north_buildings,west_buildings);
    }
    private int position = 0;

    public ViewPagerFragment() {
    }

    public ViewPagerFragment(int position) {
        this.position = position;
        Bundle args = new Bundle();
        args.putInt(BUNDLE_POSITION, position);
        setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_pager_page, container, false);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(BUNDLE_POSITION)) {
                position = savedInstanceState.getInt(BUNDLE_POSITION);
            }
        }

        final CircleView imageView = (CircleView)rootView.findViewById(R.id.PanormaCirclePageView);

        imageView.addCircles(CIRCLES_TO_PANORAMA.get(position));
        imageView.setImageResource(IMAGES[position],imageViewState);
        // TODO: center and fullscreen image here
        imageView.setScaleAndCenter(1f, imageView.getCenter());

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
            CircleView imageView = (CircleView)rootView.findViewById(R.id.PanormaCirclePageView);
            outState.putInt(BUNDLE_POSITION, position);
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
