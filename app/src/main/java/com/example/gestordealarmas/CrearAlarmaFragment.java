package com.example.gestordealarmas;


import android.content.Intent;
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
public class CrearAlarmaFragment extends Fragment implements View.OnClickListener {
private Button cancelar,agregar;
private ImageButton ubicacion;
private EditText editId,editNombre;
private String lon="124125",lat="12451",strId,strNombre,idUser;
private boolean estado;
private JSONObject user,alarma;
private final String activ="Activada",desac="Desactivada";

    public CrearAlarmaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_crear_alarma, container, false);
        MainActivity activity= (MainActivity) getActivity();
        user = activity.enviarUsuario();
        final Spinner spinner=view.findViewById(R.id.dropDown);
        String[] items= new String[]{"Activada","Desactivada"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_selectable_list_item,items);
        spinner.setAdapter(adapter);

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


        // Inflate the layout for this fragment
        cancelar=view.findViewById(R.id.botonCancelar);
        cancelar.setOnClickListener(this);
        agregar=view.findViewById(R.id.botonAgregar);
        agregar.setOnClickListener(this);
        ubicacion=view.findViewById(R.id.imageButton);
        editId=view.findViewById(R.id.editId);
        editNombre=view.findViewById(R.id.editNombreAlarma);


        return view;
    }

    @Override
    public void onClick(View v) {
        AlarmasFragment f;
        switch (v.getId()){
            case R.id.botonAgregar:
                //Llamar Volley request post
                strId=editId.getText().toString();
                strNombre=editNombre.getText().toString();
                try {
                    idUser=user.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                validarDatos(strId,strNombre,estado,lat,lon,idUser);
                f=new AlarmasFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor,f)
                        .commit();
                break;
            case R.id.botonCancelar:
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
            agregarAlarma(alarma);
    }
    public void agregarAlarma(JSONObject alarm){
        String PLACES_URL = "http://www.testproyecto.com/alarms/create";
        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                PLACES_URL,
                alarm,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject aux = response;
                        Log.d("correct","Alarma creada");

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
