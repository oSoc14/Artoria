package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String lang = settings.getString(getString(R.string.lang),null);

        Intent nextPage;
        if(lang == null){
            nextPage = new Intent(this, LanguageChoiceActivity.class);
        } else {
            /* We can't switch on strings :( */
            if(lang.equals(R.string.french)) {
                DataManager.lang = DataManager.Language.FRENCH;
            } else {
                if (lang.equals(R.string.english)){
                    DataManager.lang = DataManager.Language.FRENCH;
                } else {
                    DataManager.lang = DataManager.Language.DUTCH;
                }
            }

            nextPage = new Intent(this,MainActivity.class);
        }

        startActivity(nextPage);

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
