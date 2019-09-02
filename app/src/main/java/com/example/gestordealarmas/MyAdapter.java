package com.example.gestordealarmas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
static class ViewHOlderItem {
    TextView nombreAlarma;
    Switch sw;
    ImageButton editar,eliminar;
}
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
        ImageButton editarButton=convertView.findViewById(R.id.editar);
        final ImageButton borrarButton=convertView.findViewById(R.id.borrar);

        editarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarAlarmaFragment f=new EditarAlarmaFragment();
                MainActivity activity=(MainActivity) v.getContext();
                activity.setIdAlarm(arr.get(position).getId_alarma());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,f).commit();

            }
        });

        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroy(arr.get(position).getId_alarma());
                arr.remove(position);
            }
        });

        nombreAlarma.setText(arr.get(position).getNombre());
        if (arr.get(position).getEstado()==true){
            sw.setChecked(true);
        }
        else if (arr.get(position).getEstado()==false){
            sw.setChecked(false);
        }
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    enviarEstado(isChecked,arr.get(position).getId_alarma());
            }
        });



        return convertView;
    }

    public void enviarEstado(boolean e,String id){
        JSONObject aux= new JSONObject();
        try {
            aux.put("id_alarm", id);
            if (e) {
                aux.put("state",1 );
            }else aux.put("state",0);
        } catch (JSONException err) {

        }
        try {
            String auxid= aux.getString("id_alarm");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        String PLACES_URL = "http://67.23.253.235/~tes393/public/alarms/state";
        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                PLACES_URL,
                aux,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error ",error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    public void destroy(String id){
        JSONObject aux= new JSONObject();
        try {
            aux.put("id_alarm", id);
        } catch (JSONException err) {

        }
        String PLACES_URL = "http://67.23.253.235/~tes393/public/alarms/delete";
        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                PLACES_URL,
                aux,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error ",error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);

        this.notifyDataSetChanged();
    }




}
