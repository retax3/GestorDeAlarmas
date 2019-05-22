package com.example.gestordealarmas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class PinActivity extends AppCompatActivity {
    private Button bCancelar,bAceptar,bHuella;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        Pinview pinview = findViewById(R.id.pinView);
        bCancelar=findViewById(R.id.bCancelar);
        bAceptar=findViewById(R.id.bAceptar);
        bHuella=findViewById(R.id.bHuella);

        
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Toast.makeText(PinActivity.this,
                        ""+pinview.getValue(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PinActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PinActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        bHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
