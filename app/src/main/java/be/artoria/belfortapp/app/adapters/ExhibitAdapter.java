
package be.artoria.belfortapp.app.adapters;

import android.content.Context;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.MuseumImageMapper;
import be.artoria.belfortapp.app.PrefUtils;

public class ExhibitAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<FloorExhibit> exhibits;
    private static final int MUSEUM_TITLE_SIZE = 32;
    private static final int IMAGE_HEIGHT = 350;

    public ExhibitAdapter(Context context, List<FloorExhibit> exhibits) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.exhibits = exhibits;
    }

    @Override
    public int getCount() {return exhibits.size();}

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.exhibit_item, null);
        }

        ScrollView scrMuseum = (ScrollView)convertView.findViewById(R.id.scrlMuseum);
        scrMuseum.fullScroll(ScrollView.FOCUS_UP);

        Floor currentFloor = DataManager.getFloorList().get(position);
        LinearLayout lnrMuseum = (LinearLayout)convertView.findViewById(R.id.lnrMuseum);
        lnrMuseum.removeAllViews();

        ImageView imgHeader = (ImageView)convertView.findViewById(R.id.imgHeader);
        imgHeader.setImageDrawable(MuseumImageMapper.getDrawableForId(Integer.parseInt(currentFloor.exhibits.get(0).getImage())));

        TextView txtTitle = (TextView)convertView.findViewById(R.id.txtChapterTitle);
        txtTitle.setText(currentFloor.exhibits.get(0).getName());

        for(int j = 0; j < currentFloor.exhibits.size();j++){
            addExhibit(currentFloor.exhibits.get(j),lnrMuseum,position,j+1);
        }

        return convertView;
    }

    private void addExhibit(FloorExhibit ex, View parentView, int floor, int exhibit){
        final LinearLayout parent = (LinearLayout) parentView;
        final LinearLayout lnrTitle = new LinearLayout(PrefUtils.getContext());
        final FrameLayout fmlImage = new FrameLayout(PrefUtils.getContext());
        final View vwGradient = new View(PrefUtils.getContext());
        lnrTitle.setOrientation(LinearLayout.HORIZONTAL);


        final TextView txtNumber = new TextView(PrefUtils.getContext());
        txtNumber.setTextSize(MUSEUM_TITLE_SIZE);
        txtNumber.setTextColor(Color.GRAY);
        txtNumber.setText(floor + "." + exhibit +"  ");
        txtNumber.setPadding(10,0,0,0);

        final TextView txtTitle = new TextView(PrefUtils.getContext());
        txtTitle.setTextSize(MUSEUM_TITLE_SIZE);
        txtTitle.setText(ex.getName());

        lnrTitle.addView(txtNumber);
        lnrTitle.addView(txtTitle);

        final TextView txtContent = new TextView(PrefUtils.getContext());
        txtContent.setText(ex.getDescription());
        txtContent.setPadding(10,10,10,10);
        if(exhibit != 1){
            ImageView img = new ImageView(PrefUtils.getContext());
            Drawable drwb = MuseumImageMapper.getDrawableForId(Integer.parseInt(ex.getImage()));
            if(drwb != null)
                img.setImageDrawable(drwb);
            /*img.setBackgroundColor(PrefUtils.getContext().getResources().getColor(R.color.color2));*/
            img.setMaxHeight(IMAGE_HEIGHT);
            img.setMinimumHeight(IMAGE_HEIGHT);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setAdjustViewBounds(true);
            //img.setPadding(0,5,0,5);


            //add the image to a framelayout together with the gradient.xml shape, this is only available from Android 4.2
            fmlImage.addView(img);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vwGradient.setBackground(PrefUtils.getContext().getResources().getDrawable(R.drawable.gradient));
                fmlImage.addView(vwGradient);
            }
            parent.addView(fmlImage);
        }
        parent.addView(lnrTitle);
        parent.addView(txtContent);

    }
}

