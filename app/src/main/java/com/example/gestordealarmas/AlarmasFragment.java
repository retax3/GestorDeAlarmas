package com.example.gestordealarmas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmasFragment extends Fragment {
    private JSONObject user;
    private ArrayList<Alarma> arrayList;


    public AlarmasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alarmas, container, false);


        //Obtener usuario desde activity
        MainActivity activity= (MainActivity) getActivity();
        user=activity.enviarUsuario();





        ListView lv= view.findViewById(R.id.listaAlarmas);

        arrayList= listarAlarmas(user);

        MyAdapter myadapter= new MyAdapter(getActivity(),arrayList);
        lv.setAdapter(myadapter);


        // Inflate the layout for this fragment
        return view;
    }


    public ArrayList<Alarma> listarAlarmas(JSONObject user){
        final ArrayList<Alarma> alarmas = new ArrayList<>();
        String url= "http://67.23.253.235/~tes393/public/alarms";

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, //GET or POST
                url, //URL
                null, //Parameters
                new Response.Listener<JSONArray>() { //Listener OK

                    @Override
                    public void onResponse(JSONArray responsePlaces) {
                        try {
                            JSONArray results = responsePlaces;

                            for(int i = 0; i < results.length(); i++) {
                                JSONObject alarm = results.getJSONObject(i);
                                boolean stateAlarm;
                                if (alarm.get("state")=="1"){
                                    stateAlarm=true;
                                }else stateAlarm=false;

                                Alarma alarmItem = new Alarma(
                                        (Integer) alarm.get("id"),
                                        alarm.getString("id_alarm"),
                                        alarm.getString("name"),
                                        stateAlarm,
                                        alarm.getString("latitude"),
                                        alarm.getString("longitude"));
                                alarmas.add(alarmItem);
                                // adapter.notifyDataSetChanged();
                                // Toast.makeText(MainActivity.this, tittle +"-"+ year, Toast.LENGTH_LONG).show();
                            }
                            //Toast.makeText(MainActivity.this, responsePlaces.toString(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() { //Listener ERROR
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        //Send the request to the requestQueue

        requestQueue.add(request);


        return alarmas;
    }



}
