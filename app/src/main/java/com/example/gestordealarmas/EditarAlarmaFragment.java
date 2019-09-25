package com.example.gestordealarmas;


import android.media.Image;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarAlarmaFragment extends Fragment implements View.OnClickListener {
    private final String activ="Activada",desac="Desactivada";
    private TextView tvId;
    private Button bEditar,bCancelar;
    private ImageButton bLoc;
    private JSONObject alarma;
    private boolean estado;
    private String lat="12314",lon="15151",idAlarm,strNombre,strIdUser;
    private EditText nombreAlarma;


    public EditarAlarmaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_editar_alarma, container, false);
        tvId=view.findViewById(R.id.textViewIdAlarm);
        MainActivity activity= (MainActivity) getActivity();
        idAlarm=activity.getIdAlarm();
        try {
            strIdUser=activity.getIdUser();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Spinner spinner=view.findViewById(R.id.dropDownEditarAlarma);

        tvId.setText(activity.getIdAlarm());
        nombreAlarma=view.findViewById(R.id.editNombreAlarmaEditarAlarma);
        String[] items= new String[]{"Activada","Desactivada"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_selectable_list_item,items);
        // Inflate the layout for this fragment
        Spinner sp=view.findViewById(R.id.dropDownEditarAlarma);
        sp.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion=spinner.getSelectedItem().toString();
                if (seleccion.equals(activ)){
                    estado=true;
                }else if(seleccion.equals(desac)){
                    estado=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bCancelar=view.findViewById(R.id.botonCancelarEditarAlarma);
        bCancelar.setOnClickListener(this);
        bEditar=view.findViewById(R.id.botonEditarAlarma);
        bEditar.setOnClickListener(this);
        bLoc=view.findViewById(R.id.imageButtonEditarAlarma);
        bLoc.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        AlarmasFragment f;
        switch (v.getId()){
            case R.id.botonEditarAlarma:
                //llamar request editar

                validarDatos(idAlarm,strNombre,estado,lat,lon,strIdUser);
                f=new AlarmasFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor,f)
                        .commit();
                break;
            case R.id.botonCancelarEditarAlarma:
                f=new AlarmasFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor,f)
                        .commit();
                break;
        }
    }

    public void validarDatos(String idAlarm,String nombreAlarm,boolean state,String lat,String lon,String id_user){
        alarma= new JSONObject();
        try {
            alarma.put("id_alarm", idAlarm);
            alarma.put("name", nombreAlarm);
            if (state == true) {
                alarma.put("state", true);
            } else if (state == false) {
                alarma.put("state", false);
            }
            alarma.put("latitude", lat);
            alarma.put("longitude", lon);
            alarma.put("id_user", id_user);
        } catch (JSONException e){

        }
        editarAlarma(alarma);
    }

    public void editarAlarma(JSONObject alarm){
        String PLACES_URL = "http://www.testproyecto.com/alarms/update";
        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                PLACES_URL,
                alarm,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject aux = response;
                        Toast.makeText(getActivity(),aux.toString(),Toast.LENGTH_SHORT);
                        Log.d("correct","Alarma editada");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error ",error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);


    }
}
