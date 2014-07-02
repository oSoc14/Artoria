package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import be.artoria.belfortapp.R;

public class MainActivity extends BaseActivity {
    ArrayAdapter<String> menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGui();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /*initialize the GUI content and clickhandlers*/
    private void initGui(){
        final ListView lstMenu = (ListView)findViewById(R.id.lstMenu);
        final Button btnSettings = (Button)findViewById(R.id.btnSettings);
        final Button btnAbout = (Button)findViewById(R.id.btnAbout);
        final Button btnRoute = (Button)findViewById(R.id.btnRoute);


        menuAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.lstMenu));
        lstMenu.setAdapter(menuAdapter);

        lstMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /* The second item are the buildings */
                if(i == 1){
                    final Intent intent = new Intent(MainActivity.this,MonumentDetailActivity.class);
                    intent.putExtra("id",0);
                    startActivity(intent);
                }
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Go to settings*/
                Intent i = new Intent(MainActivity.this,LanguageChoiceActivity.class);
                startActivity(i);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Go to the Artoria website*/
                Uri webpage = Uri.parse(getResources().getString(R.string.artoria_url));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });

        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Go to the route overview*/
                Intent i = new Intent(MainActivity.this,RouteActivity.class);
                startActivity(i);
            }
        });
    }
}
