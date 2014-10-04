package be.artoria.belfortapp.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.PrefUtils;

public class ExhibitAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<FloorExhibit> exhibits;

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
        final TextView txtContent =  ((TextView)convertView.findViewById(R.id.txtContent));
        txtContent.setText(exhibits.get(position).getDescription());
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        final ImageView imgCnt = (ImageView)convertView.findViewById(R.id.imgContent);
        final ProgressBar prgWait = (ProgressBar)convertView.findViewById(R.id.prgWait);
        setLoading(imgCnt,prgWait);

        try {
            Picasso.with(PrefUtils.getContext()).load(exhibits.get(position).getImage()).into(imgCnt, new Callback() {
                @Override
                public void onSuccess() {
                    showImage(imgCnt, prgWait);
                    System.out.println("image loaded ...");
                /*Image loaded*/
                }

                @Override
                public void onError() {
                    setImageNotFound(imgCnt);
                    showImage(imgCnt, prgWait);
                }
            });
        }catch(Exception ex){
            System.out.println(ex);
            showImage(imgCnt,prgWait);
            setImageNotFound(imgCnt);
        }

        return convertView;
    }

    private void setLoading(ImageView v, ProgressBar p){
        v.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
    }

    private void showImage(ImageView v, ProgressBar p){
        p.setVisibility(View.GONE);
        v.setVisibility(View.VISIBLE);;
    }

    private void setImageNotFound(ImageView img){
        img.setImageDrawable(PrefUtils.getContext().getResources().getDrawable(R.drawable.img_not_found));
    }
}
