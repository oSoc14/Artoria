package be.artoria.belfortapp.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.app.FontManager;

/**
 * Created by dietn on 24/11/14.
 */
public class MainAdapterNumber extends ArrayAdapter {
    private Context context;
    private LayoutInflater mInflater;

    public MainAdapterNumber(Context context, int resource, String[] items) {
        super(context, resource, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.main_list_item_number,null);
        }
        final TextView txtDesc = (TextView)convertView.findViewById(R.id.main_desc);
        txtDesc.setTypeface(FontManager.athelas);
        final TextView txtNumber = (TextView)convertView.findViewById(R.id.main_order_number);
        txtNumber.setTypeface(FontManager.athelas);
        txtDesc.setText((String)getItem(position));
        int pos = position +1;
        txtNumber.setText(""+pos);
        
        return convertView;
    }
}
