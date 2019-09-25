package com.example.gestordealarmas;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmasFragment extends Fragment {
    private JSONObject user;
    private String url = "http://www.testproyecto.com/alarms";
    private Switch sw;


    public AlarmasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alarmas, container, false);
        final ArrayList<Alarma> arrayList = new ArrayList<>();

        //Obtener usuario desde activity
        MainActivity activity= (MainActivity) getActivity();
        user = activity.enviarUsuario();

        ListView lv= view.findViewById(R.id.listaAlarmas);
       // arrayList.add(new Alarma(1,"123","Casa",true,"-36.751819","-73.098878"));


        JSONObject id_user = new JSONObject();
        final MyAdapter myadapter= new MyAdapter(getActivity(),arrayList);
        lv.setAdapter(myadapter);

        try {
            id_user.put("id_user",user.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                id_user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results= new JSONArray(response.getString("alarmas"));
                            for(int i = 0; i < results.length(); i++) {
                                JSONObject alarm = results.getJSONObject(i);
                                boolean stateAlarm;
                                if (alarm.getString("state").equals("1")){
                                    stateAlarm=true;
                                }else stateAlarm=false;
                                int id= Integer.parseInt(alarm.getString("id"));

                                Alarma alarmItem = new Alarma(
                                        id,
                                        alarm.getString("id_alarm"),
                                        alarm.getString("name"),
                                        stateAlarm,
                                        alarm.getString("latitude"),
                                        alarm.getString("longitude"));
                                 arrayList.add(alarmItem);
                                 myadapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"error " +error,Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);



        return view;
    }



}
