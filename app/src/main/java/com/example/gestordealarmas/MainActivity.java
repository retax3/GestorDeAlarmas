package com.example.gestordealarmas;

import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private JSONObject user;
    private TextView correoUser;
    private String idAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AlarmasFragment f=new AlarmasFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.contenedor,f).commit();

        FloatingActionButton fab = findViewById(R.id.agregarAlarma);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                CrearAlarmaFragment fragment = new CrearAlarmaFragment();
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.contenedor,fragment).commit();


            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle=getIntent().getExtras();
        if (getIntent().hasExtra("json")){
            try {
                user = new JSONObject(getIntent().getStringExtra("json"));
                Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.setTitle("Gestor de Alarmas");

        View headView= navigationView.getHeaderView(0);
        try {
            correoUser=headView.findViewById(R.id.correoUsuario);
            correoUser.setText(user.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public JSONObject enviarUsuario(){
        return user;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        Boolean fragmentSeleccionado=false;

        if (id == R.id.nav_alarmas) {
            // Handle the camera action
            fragment=new AlarmasFragment();
            fragmentSeleccionado=true;

        } else if (id == R.id.nav_perfil) {
            fragment=new PerfilFragment();
            fragmentSeleccionado=true;

        } else if (id == R.id.nav_registros) {
            fragment=new RegistrosFragment();
            fragmentSeleccionado=true;

        }
        if (fragmentSeleccionado){
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.contenedor,fragment).commit();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setIdAlarm(String idAlarm){
        this.idAlarm=idAlarm;
    }

    public String getIdAlarm(){
        return idAlarm;
    }

    public String getIdUser() throws JSONException {
        return user.getString("id");
    }

}
