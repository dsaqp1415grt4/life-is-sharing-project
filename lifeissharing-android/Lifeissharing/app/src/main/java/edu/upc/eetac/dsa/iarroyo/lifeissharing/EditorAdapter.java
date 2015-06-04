package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Editor;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Item;

/**
 * Created by nacho on 4/06/15.
 */
public class EditorAdapter extends BaseAdapter{

    ArrayList<Editor> data;
    LayoutInflater inflater;

    public EditorAdapter(Context context, ArrayList<Editor> data) {
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
        TextView tvUsername;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_editores, null);
            viewHolder = new ViewHolder();
            viewHolder.tvUsername = (TextView) convertView
                    .findViewById(R.id.tvUsername);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String username = data.get(position).getUsername();

        viewHolder.tvUsername.setText(username);

        return convertView;
    }
}
