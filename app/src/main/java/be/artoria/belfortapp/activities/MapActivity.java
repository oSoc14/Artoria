package be.artoria.belfortapp.activities;

import android.os.Bundle;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.fragments.MapFragment;

public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MapFragment())
                    .commit();
        }
    }
}
