package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Item;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Lista;

/**
 * Created by nacho on 30/05/15.
 */
public class ItemAdapter extends BaseAdapter{



    ArrayList<Item> data;
    LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<Item> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView tvDescripcion;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvDescripcion = (TextView) convertView
                    .findViewById(R.id.tvDescripcion);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String description = data.get(position).getDescription();

        viewHolder.tvDescripcion.setText(description);

        return convertView;
    }
}
