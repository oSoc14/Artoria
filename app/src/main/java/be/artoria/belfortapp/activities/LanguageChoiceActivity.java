package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

import be.artoria.belfortapp.R;


public class LanguageChoiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_choice);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.language_choice, menu);
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

    public void langSelected(View view) {
        /* Setting the locale in response to a button click*/
        final Locale locale;
        switch (view.getId()) {
            case(R.id.english):
                locale = new Locale("en");
                break;
            case(R.id.french):
                locale = new Locale("fr");
                break;
            case(R.id.dutch):
            default:
                /* Default case is dutch */
                locale = new Locale("nl");
        }

        Locale.setDefault(locale);
        final Configuration config = new Configuration();
        config.locale = locale;
        view.getContext().getApplicationContext().getResources().updateConfiguration(config, null);
        /* Saving preferences in the background */
        new Thread(){
            @Override
            public void run() {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.firstTime), false);
                editor.commit();
            }
        }.run();

        /* Opening the Main screen */
        final Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
