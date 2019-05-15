package com.example.gestordealarmas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
private EditText email,pass;
private Button bIngresar,bRegistrar;
private String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.editCorreo);
        pass=findViewById(R.id.editPass);
        bIngresar=findViewById(R.id.bIngresar);
        bRegistrar=findViewById(R.id.bRegistrar);

        bRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,Registro.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
