package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Lista;

/**
 * Created by nacho on 30/05/15.
 */
public class ListaAdapter extends BaseAdapter {
    ArrayList<Lista> data;
    LayoutInflater inflater;

    public ListaAdapter(Context context, ArrayList<Lista> data) {
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
        return ((Lista) getItem(position)).getId();
    }

    private static class ViewHolder {
        TextView tvNombre;
        TextView tvCreador;
        TextView tvUltima_modificacion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_listas, null);
            viewHolder = new ViewHolder();
            viewHolder.tvNombre = (TextView) convertView
                    .findViewById(R.id.tvNombre);
            viewHolder.tvCreador = (TextView) convertView
                    .findViewById(R.id.tvCreador);
            viewHolder.tvUltima_modificacion = (TextView) convertView
                    .findViewById(R.id.tvUltima_modificacion);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String nombre = data.get(position).getNombre();
        String creador = data.get(position).getCreador();
        String ultima_modificacion = SimpleDateFormat.getInstance().format(
                data.get(position).getUltima_modificacion());
        viewHolder.tvNombre.setText(nombre);
        viewHolder.tvCreador.setText(creador);
        viewHolder.tvUltima_modificacion.setText(ultima_modificacion);
        return convertView;
    }



}
