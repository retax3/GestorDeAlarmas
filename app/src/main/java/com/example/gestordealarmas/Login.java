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

public class Login extends AppCompatActivity {
private EditText email,pass;
private Button bIngresar,bRegistrar;
private String correo,contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.editCorreo);
        pass=findViewById(R.id.editPass);
        bIngresar=findViewById(R.id.bIngresar);
        bRegistrar=findViewById(R.id.bRegistrar);

        correo="";
        contraseña="";

        //Botones

        bRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,Registro.class);
                startActivity(intent);
                //finish();
            }
        });

        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = email.getText().toString();
                contraseña = pass.getText().toString();
                System.out.println(correo+" "+contraseña);
                Toast.makeText(Login.this,correo+" "+contraseña,Toast.LENGTH_LONG);
                login(correo,contraseña);
            }
        });


    }

    private void login(String email, String pass) {
        String PLACES_URL = "http://10.3.2.79:8089/users/login";
        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        //Prepare the Request
        final JSONObject user, aux = null;
        user = new JSONObject();

        try {
            user.put("email", correo);
            user.put("password", contraseña);
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
                        Toast.makeText(Login.this, response.toString() + " usuario encontrado", Toast.LENGTH_LONG).show();
                        if (aux.equals(user)){
                            Intent intent= new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString() + " usuario no encontrado", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        

    }
}
