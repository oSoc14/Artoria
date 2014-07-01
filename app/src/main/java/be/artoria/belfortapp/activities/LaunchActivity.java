package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import be.artoria.belfortapp.R;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String lang = settings.getString(getString(R.string.lang),null);
        Intent nextPage;
        if(lang == null){
            nextPage = new Intent(this, LanguageChoiceActivity.class);
        } else {
            nextPage = new Intent(this,MainActivity.class);
        }

        boolean silent = settings.getBoolean("silentMode", false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
