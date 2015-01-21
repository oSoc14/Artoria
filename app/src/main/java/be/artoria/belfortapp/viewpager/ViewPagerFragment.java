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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.extension.views.CircleView;

public class ViewPagerFragment extends Fragment {

    private static final String BUNDLE_ASSET = "asset";

    private int resId = -1;

    public ViewPagerFragment() {
    }

    public ViewPagerFragment(int resId) {
        this.resId = resId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_pager_page, container, false);

        if (savedInstanceState != null) {
            if (resId == -1 && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                resId = savedInstanceState.getInt(BUNDLE_ASSET);
            }
        }
        if (resId != -1) {
            SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)rootView.findViewById(R.id.PanormaCirclePageView);
            imageView.setImageResource(resId);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View rootView = getView();
        if (rootView != null) {
            outState.putInt(BUNDLE_ASSET, resId);
        }
    }

}
