package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.PrefUtils;


public class LaunchActivity extends BaseActivity {

    private static final int SPLASH_DISPLAY_TIME = 4000; // splash screen delay time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final Context cthis = this;

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                if(PrefUtils.isFirstTime()){
                    intent.setClass(LaunchActivity.this, LanguageChoiceActivity.class);
                } else {
                    PrefUtils.loadLanguage(cthis);
                    intent.setClass(LaunchActivity.this, MainActivity.class);
                }

                LaunchActivity.this.startActivity(intent);
                LaunchActivity.this.finish();
                // transition from splash to main menu
                overridePendingTransition(R.anim.activityfadein,
                        R.anim.splashfadeout);

            }
        }, SPLASH_DISPLAY_TIME);

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

        return super.onOptionsItemSelected(item);
    }
}
