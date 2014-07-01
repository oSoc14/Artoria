package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
        /* Saving the language preference in response to a button click*/
        SharedPreferences.Editor prefEditor = getPreferences(MODE_PRIVATE).edit();
        switch (view.getId()) {
            case(R.id.english):
                prefEditor.putString(getString(R.string.lang),getString(R.string.english));
                break;
            case(R.id.french):
                prefEditor.putString(getString(R.string.lang),getString(R.string.french));
                break;
            case(R.id.dutch):
            default:
                prefEditor.putString(getString(R.string.lang),getString(R.string.dutch));
                break;
        }
        /* Opening the Main screen */
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
