package com.example.gestordealarmas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<Alarma> arr;

    public MyAdapter(Context c, ArrayList<Alarma> arr){
        this.context=c;
        this.arr=arr;

    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.custom_list_view_style,parent,false);

        TextView nombreAlarma=convertView.findViewById(R.id.nombreAlarma);
        Switch sw=convertView.findViewById(R.id.estadoAlarma);

        nombreAlarma.setText(arr.get(position).getNombre());

        sw.setChecked(arr.get(position).getEstado());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked!=true){
                    arr.get(position).setEstado(false);
                }
                arr.get(position).setEstado(true);
            }
        });


        return convertView;
    }
}
