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
import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.app.Floor;
import be.artoria.belfortapp.app.FloorExhibit;
import be.artoria.belfortapp.app.PrefUtils;

public class ExhibitAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<FloorExhibit> exhibits;
    private static final int[] ids = { R.drawable.belfort3, R.drawable.lakenhalle,R.drawable.mammelokker,
    R.drawable.belfort_feest,R.drawable.belfortbinnen032,R.drawable.belfortbinnen039,R.drawable.belfort,R.drawable.draak_enkel,
    R.drawable.klokken_closeup,R.drawable.klokken,R.drawable.klokkenkamer,R.drawable.belfort2,R.drawable.trommel,
    R.drawable.belfortbinnen147,R.drawable.img_not_found};

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
        final TextView txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(exhibits.get(position).getName());

        final TextView txtContent =  ((TextView)convertView.findViewById(R.id.txtContent));
        txtContent.setText(exhibits.get(position).getDescription());
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        final ImageView imgCnt = (ImageView)convertView.findViewById(R.id.imgContent);
        imgCnt.setScaleType(ImageView.ScaleType.FIT_XY);
        imgCnt.setImageResource(ids[getImageIndex(position)]);

        return convertView;
    }

   private int getImageIndex(int position){
       if(!exhibits.isEmpty()){
           int floor = exhibits.get(0).floor;
           int toReturn = 0;
           final List<Floor> floors = DataManager.getFloorList();
           for(int i = 0; i < floor; i++){
               toReturn += floors.get(i).exhibits.size();
           }
           toReturn += position;
           if(toReturn < ids.length)
                return toReturn;
           else
               return ids.length -1; /*invalid index, return not found */
       }else{
           return ids.length -1; /*last position is image not found ...*/
       }

   }
}
