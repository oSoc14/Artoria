package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import java.util.ArrayList;
import java.util.Arrays;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.RouteManager;


public class RouteActivity extends BaseActivity {

    DragSortListView listView;
    ArrayAdapter<POI> adapter;

    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener()
    {
        @Override
        public void drop(int from, int to)
        {
            if (from != to)
            {
                POI item = adapter.getItem(from);
                adapter.remove(item);
                adapter.insert(item, to);
            }
        }
    };

    private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener()
    {
        @Override
        public void remove(int which)
        {
            adapter.remove(adapter.getItem(which));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initGUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private ArrayAdapter<POI> routeAdapter;


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

    /*Initialize GUI */
    private void initGUI(){
        ///////// old code /////////
        //final ListView lstRoute = (ListView)findViewById(R.id.lstRoute);
        //final Button btnCalcRoute = (Button)findViewById(R.id.btnCalcRoute);


        ///*TODO make the list sortable, this might be interesting: http://jasonmcreynolds.com/?p=423 */
        //routeAdapter = new ArrayAdapter<POI>(this,android.R.layout.simple_list_item_1, RouteManager.getInstance().getWaypoints());
        //lstRoute.setAdapter(routeAdapter);

        /* new code */

        listView = (DragSortListView) findViewById(R.id.lstRoute);
        adapter = new ArrayAdapter<POI>(this,
                android.R.layout.simple_list_item_1,  RouteManager.getInstance().getWaypoints());
        listView.setAdapter(adapter);
        listView.setDropListener(onDrop);
        listView.setRemoveListener(onRemove);

        final DragSortController controller = new DragSortController(listView);
        controller.setDragHandleId(R.id.lstRoute);
        //controller.setClickRemoveId(R.id.);
        controller.setRemoveEnabled(true);
        controller.setSortEnabled(true);
        controller.setDragInitMode(1);
        //controller.setRemoveMode(removeMode);

        listView.setFloatViewManager(controller);
        listView.setOnTouchListener(controller);
        listView.setDragEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final POI poi = (POI)listView.getSelectedItem();
                final int id = poi.id;
                final Intent intent = new Intent(RouteActivity.this, MonumentDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    public void goToCalcRoute(View view) {
        final Intent i = new Intent(RouteActivity.this,MapActivity.class);
        startActivity(i);
    }
}
