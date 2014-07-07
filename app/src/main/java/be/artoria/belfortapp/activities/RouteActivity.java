package be.artoria.belfortapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DragSortAdapter;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.app.RouteManager;


public class RouteActivity extends BaseActivity {

    DragSortListView listView;
    DragSortAdapter adapter;

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
        listView = (DragSortListView) findViewById(R.id.lstRoute);
        adapter = new DragSortAdapter(this,
                R.layout.route_list_item,  RouteManager.getInstance().getWaypoints());
        listView.setAdapter(adapter);
        listView.setDropListener(onDrop);
        listView.setRemoveListener(onRemove);

        final DragSortController controller = new DragSortController(listView);

        controller.setRemoveEnabled(true);


        listView.setFloatViewManager(controller);
        listView.setOnTouchListener(controller);
        listView.setDragEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final POI poi = adapter.getItem(i);
                final int id = poi.id;
                final Intent intent = new Intent(RouteActivity.this, MonumentDetailActivity.class);
                intent.putExtra(MonumentDetailActivity.ARG_ID, id);
                startActivity(intent);
            }
        });
    }

    public void goToCalcRoute(View view) {
        final Intent i = new Intent(RouteActivity.this,MapActivity.class);
        startActivity(i);
    }
}
