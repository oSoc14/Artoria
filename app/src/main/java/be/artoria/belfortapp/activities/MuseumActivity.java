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
        setTitle(getResources().getStringArray(R.array.lstFloor)[floor]);

        //TODO get content from database or web ...
        txtContent.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultricies dolor urna, ac dignissim nibh pharetra id. Pellentesque eu turpis pretium, eleifend purus eget, semper elit. Nulla fringilla lacinia urna, non mattis mauris suscipit ac. Cras velit nibh, tempor in dui in, efficitur suscipit nibh. Praesent quis tincidunt velit, sit amet commodo eros. Praesent scelerisque nulla id eros placerat sagittis ac a ex. Proin non magna ultricies, iaculis mauris eu, ultricies sapien. Aliquam eget neque vitae lectus tristique gravida in sed libero. Nullam sed tristique purus. Aenean iaculis lectus at lacus sodales dapibus. Sed at ante ipsum. Donec a nibh nibh. Nulla ut tincidunt tortor. Morbi suscipit imperdiet congue. Curabitur vel fermentum ligula.\n" +
                "\n" +
                "Curabitur feugiat molestie leo, commodo porta ligula porttitor sit amet. Nulla iaculis, orci in luctus pharetra, augue felis aliquam sapien, et molestie justo mi vel dui. Etiam odio augue, vehicula a erat porttitor, tristique accumsan lectus. Suspendisse semper fermentum dolor vitae commodo. Vestibulum posuere elementum sem et eleifend. Quisque nisl lorem, semper tempus facilisis eu, egestas eu enim. Aliquam elit turpis, pharetra in molestie quis, laoreet eget magna. Sed feugiat nisi ipsum, ac porttitor lectus tempor at.");

        //TODO find more images online to use in the imageSwitcher
        is.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final ImageView toReturn = new ImageView(PrefUtils.getContext());
                Picasso.with(PrefUtils.getContext()).load(TMP_IMG_LINK).into(toReturn, new Callback() {
                    @Override
                    public void onSuccess() {

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
