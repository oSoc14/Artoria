package be.artoria.belfortapp.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.artoria.belfortapp.R;

/**
 * Created by Dieter Beelaert on 4/07/2014.
 */
public class RouteDescAdapter extends ArrayAdapter<DescriptionRow> {
    Context context;

    public RouteDescAdapter(Context context, int resourceId,
                                 List<DescriptionRow> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final DescriptionRow rowItem = getItem(position);

        final LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.description_list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.imageView = (ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDesc.setText(rowItem.description);
        holder.imageView.setImageDrawable(rowItem.imageDescription);

        return convertView;
    }
}
