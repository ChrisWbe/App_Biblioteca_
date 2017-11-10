package com.example.christianquintero.app_biblioteca_;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.security.acl.Group;


public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public Toolbar toolbar;
    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Drawable drawable;
    public TextView titulo, subTitulo;
    public Group grupo;
    public MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        titulo = (TextView)findViewById(R.id.headerTitulo);
        subTitulo = (TextView)findViewById(R.id.headerSubTitulo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        Fragment fragmentInicial;


        if(bundle.getBoolean("ref")){
           fragmentInicial = new DetallesUser();

        }else{
            fragmentInicial = new Inicio();
        }



        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_content, fragmentInicial).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_principal);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.abierto, R.string.cerrado);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        titulo = (TextView)hView.findViewById(R.id.headerTitulo);
        subTitulo = (TextView)hView.findViewById(R.id.headerSubTitulo);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        item = menu.findItem(R.id.item_logout);

        SharedPreferences titShared = getSharedPreferences(Login.nameFyle, Context.MODE_PRIVATE);
        if(titShared.getString("Usuario", null)==null){
            titulo.setText("Bienvenido");
            item.setVisible(false);
        }else{
            titulo.setText(titShared.getString("Usuario", null));
            subTitulo.setText("Hola");
            item.setVisible(true);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        boolean fragmentTransaction = false;
        switch (id) {
            case R.id.item_Inicio:
                getSupportActionBar().setTitle(getString(R.string.ini));
                fragment = new Inicio();
                fragmentTransaction = true;
                break;

            case R.id.item_mapas:
                getSupportActionBar().setTitle(getString(R.string.map));
                fragment = new listaBiblio();
                fragmentTransaction = true;
                break;

            case R.id.item_apli:
                getSupportActionBar().setTitle(getString(R.string.apli));
                fragment = new ListaAplicaciones();
                fragmentTransaction = true;
                break;
            case R.id.item_login:
                SharedPreferences sharedPreferences = getSharedPreferences(Login.nameFyle, Context.MODE_PRIVATE);
                if(sharedPreferences.getString("Usuario", null) == null){
                    Intent intent = new Intent().setClass(this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out );
                    finish();

                }else{
                    getSupportActionBar().setTitle(getString(R.string.miCuenta));
                    fragment = new DetallesUser();
                    fragmentTransaction = true;

                }
                break;

            case R.id.item_chat:
                getSupportActionBar().setTitle(getString(R.string.chat));
                fragment = new Chat();
                fragmentTransaction = true;

            case R.id.item_help:
                break;

            case R.id.item_logout:
                AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.logOut));
                builder.setMessage(getString(R.string.confirm));
                builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences(Login.nameFyle, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent().setClass(getBaseContext(), Splash.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_principal);
        drawer.closeDrawer(GravityCompat.START);
        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragments_content, fragment).commit();

        }
        return true;
    }



}
