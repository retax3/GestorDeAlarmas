package com.example.gestordealarmas;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView tv;
    private long duracion=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logo = findViewById(R.id.logo);
        tv= findViewById(R.id.textSplash);

        Animation miAnimacion= AnimationUtils.loadAnimation(this,R.anim.transicion);
        tv.setAnimation(miAnimacion);
        logo.setAnimation(miAnimacion);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,Login.class);
                startActivity(intent);
                finish();
            };
        },duracion);
    }


}
