package com.example.gestordealarmas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {
private Button bCancelar,bRegistrar;
private EditText email,name,secondName,phone,password;
private String correo,nombre,apellido,telefono,contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        bCancelar=findViewById(R.id.bCancelar);
        bRegistrar=findViewById(R.id.bRegistrar);

        email=findViewById(R.id.editCorreo);
        name=findViewById(R.id.editNombre);
        secondName=findViewById(R.id.editApellido);
        phone=findViewById(R.id.editPhone);
        password=findViewById(R.id.editPass);

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registro.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        bRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo=email.getText().toString();
                nombre=name.getText().toString();
                apellido=secondName.getText().toString();
                telefono=phone.getText().toString();
                contraseña=password.getText().toString();
                registrar(correo,contraseña,nombre,apellido,telefono);



            }
        });
    }


    private void registrar(String correo, String contraseña, String nombre, String apellido, String telefono) {
        String PLACES_URL = "http://67.23.253.235/~tes393/public/users";
        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        //Prepare the Request
        final JSONObject user, aux = null;
        user = new JSONObject();

        try {
            user.put("email", correo);
            user.put("name", nombre);
            user.put("second_name",apellido);
            user.put("phone",telefono);
            user.put("password",contraseña);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                PLACES_URL,
                user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject aux = response;
                        Toast.makeText(Registro.this,
                                response.toString() + " Usuario Registrado",
                                Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(Registro.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro.this, error.toString() + " Usuario No Registrado Intentelo Mas Tarde", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);


    }
}
