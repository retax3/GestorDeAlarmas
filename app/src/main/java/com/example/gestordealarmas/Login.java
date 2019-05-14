package com.example.gestordealarmas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;

public class Login extends AppCompatActivity {
private EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.editCorreo);
        pass=findViewById(R.id.editPass);

    }
}
