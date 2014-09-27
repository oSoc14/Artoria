package be.artoria.belfortapp.app.adapters;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.PrefUtils;

public class ExhibitAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ImageView imgCnt;
    private ProgressBar prgWait;
    private TextView txtContent;
    private View convertView;
    private List<FloorExhibit> exhibits;

    public ExhibitAdapter(Context context, View convertView, List<FloorExhibit> exhibits) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            this.convertView = mInflater.inflate(R.layout.exhibit_item, null);
        }
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.exhibit_item, null);
        }
        getViews();
        setContent(exhibits.get(position));
        //((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position]);
        return convertView;
    }

    private void setContent(FloorExhibit currentExhibit){

        txtContent.setText(currentExhibit.getDescription());
        Picasso.with(PrefUtils.getContext()).load(currentExhibit.image).into(imgCnt);
    }

    private void getViews(){
       /* Can't do this in the constructor :-( */
        imgCnt = (ImageView)convertView.findViewById(R.id.imgContent);
        prgWait = (ProgressBar)convertView.findViewById(R.id.prgWait);
        txtContent = (TextView)convertView.findViewById(R.id.txtContent);
        /* Make the text scrollable */
        txtContent.setMovementMethod(new ScrollingMovementMethod());
    }

}
