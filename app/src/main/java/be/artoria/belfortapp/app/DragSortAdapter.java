package be.artoria.belfortapp.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.artoria.belfortapp.R;

/**
 * Created by Laurens on 04/07/2014.
 */
public class DragSortAdapter extends ArrayAdapter<POI> {
        Context context;

public DragSortAdapter(Context context, int resourceId, List<POI> items) {
        super(context, resourceId, items);
        this.context = context;
        }

/*private view holder class*/
private class ViewHolder {
    TextView txtDesc;
    ImageView imgType;
}

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        POI rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.route_list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.route_list_name);
            holder.imgType = (ImageView) convertView.findViewById(R.id.drag_handle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDesc.setText(rowItem.getName());
        holder.imgType.setImageDrawable(getTypeImg(rowItem.type));

        return convertView;
    }

    private Drawable getTypeImg(int type){
        Resources res = context.getResources();
        switch(type){
            case POI.TYPE_CASTLE: return res.getDrawable(R.drawable.drag_castle);
            case POI.TYPE_CHURCH: return res.getDrawable(R.drawable.drag_church);
            default: return res.getDrawable(R.drawable.drag_default);
        }
    }
}
