package be.artoria.belfortapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.PrefUtils;

public class MuseumActivity extends BaseActivity {
    public static final String ARG_FLOOR = "be.artoria.MuseumActivity.floor";
    public static final String TMP_IMG_LINK = "http://users.skynet.be/ovo/GentseDraak.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);
        initGui();
    }

    private void initGui(){
        ImageSwitcher is = (ImageSwitcher)findViewById(R.id.imgContent);
        TextView txtContent = (TextView)findViewById(R.id.txtContent);
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        Intent i = getIntent();
        int floor = i.getIntExtra(ARG_FLOOR,0);
        Floor fl = DataManager.getFloorList().get(floor);
        setTitle(fl.getName());

        txtContent.setText(fl.getDescription());
        //TODO find more images online to use in the imageSwitcher

        is.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final ImageView toReturn = new ImageView(PrefUtils.getContext());
                Picasso.with(PrefUtils.getContext()).load(TMP_IMG_LINK).into(toReturn, new Callback() {
                    @Override
                    public void onSuccess() {
                        //feestjee
                    }

                    @Override
                    public void onError() {
                        toReturn.setImageDrawable(getResources().getDrawable(R.drawable.img_not_found));
                        System.out.println("Failed to load image");
                    }
                });
                return toReturn;
            }
        });
    }

    public static Intent createIntent(Context ctx, int floor){
        Intent i = new Intent(ctx,MuseumActivity.class);
        i.putExtra(ARG_FLOOR,floor);
        return i;
    }
}
