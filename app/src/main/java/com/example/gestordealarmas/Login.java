package com.example.gestordealarmas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
private EditText email,pass;
private Button bIngresar,bRegistrar;
private String correo,contraseña;
private ProgressDialog verificando;
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

        bIngresar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bIngresar.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = email.getText().toString();
                contraseña = pass.getText().toString();
                verificando= new ProgressDialog(Login.this);
                verificando.setTitle("Verificando");
                verificando.setMessage("Autenticando datos");
                verificando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                verificando.setMax(100);
                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        verificando.cancel();
                    }
                };
                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 3000);
                
                Toast.makeText(Login.this,correo+" "+contraseña,Toast.LENGTH_LONG);
                if (!validarEmail(correo)){
                    email.setError("Email Invalido");
                }else {
                    if (correo=="" && contraseña==""){
                        Toast.makeText(Login.this, "Por favor llenar todos los campos", Toast.LENGTH_SHORT).show();

                    } else {login(correo,contraseña);}
                }
            }
        });


    }

    private void login(String email, String pass) {
        String PLACES_URL = "http://www.testproyecto.com/users/login";
        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);



        //Prepare the Request
        final JSONObject user, aux;
        aux=new JSONObject();
        try {
            aux.put("error","No autorizado");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

                        if (aux.length()==response.length()){
                            Toast.makeText(Login.this,
                                    "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Login.this,
                                    response.toString() + " usuario encontrado",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            intent.putExtra("json",response.toString());
                            startActivityForResult(intent,1);
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
