package com.example.christianquintero.app_biblioteca_;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bibliotecas extends AppCompatActivity {

    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    TextView txtTitulo, txtDir, txtTel, txtLunVie, txtSab, txtDom, txtBody;
    FloatingActionButton floatingActionButton;
    ScrollView scrollView;
    ActionBar actionBar;
    Toolbar toolbar;
    private int group, child;
    private String titulo, latitud, longitud;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotecas);
        toolbar = (Toolbar)findViewById(R.id.toolbar_biblio);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();
        setGroup(bundle.getInt("group"));
        setChild(bundle.getInt("child"));
        actionBar.setDisplayHomeAsUpEnabled(true);

        scrollView = (ScrollView) findViewById(R.id.layout_info);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.action_button);
        txtTitulo = (TextView) findViewById(R.id.textoTitle);
        txtDir = (TextView) findViewById(R.id.dir);
        txtTel = (TextView) findViewById(R.id.tel);
        txtLunVie = (TextView) findViewById(R.id.lun_vie);
        txtSab = (TextView) findViewById(R.id.sab);
        txtDom = (TextView) findViewById(R.id.dom);
        txtBody = (TextView) findViewById(R.id.textBody);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(getApplicationContext(), MostrarMapas.class);
                intent.putExtra("TituloMarcador", getTitulo());
                intent.putExtra("Lat", getLatitud());
                intent.putExtra("Lon", getLongitud());
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        expandableListDetail = ExpandableListaData.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        System.out.println("Titulo"+ expandableListTitle.get(getGroup()));
        switch(expandableListTitle.get(getGroup())){
            case "Centros de Documentación"://centro de documentacion
                infoCentro(getChild());
                actionBar.setTitle("Centro de Documentación");
                break;
            case "Medellín"://medellin
                actionBar.setTitle("Medellín");
                infoMede(getChild());
                break;

            case "Seccionales y Regionales"://seccionales
                actionBar.setTitle("Seccionales y Regionales");
                infoSecc(getChild());
                break;

        }

    }

    public void infoBiblio(String title, String dir, String tel, String lunVie, String sab, String dom, String body){
        txtTitulo.setText(title);
        txtDir.setText(dir);
        txtTel.setText(tel);
        txtLunVie.setText(lunVie);
        txtSab.setText(sab);
        txtDom.setText(dom);
        txtBody.setText(body);
    }

    public void infoCentro(int pos){
        switch (pos){
            case 0:
                infoBiblio(getString(R.string.textTiCEN), getString(R.string.dirCEN), getString(R.string.telCEN), getString(R.string.lvCEN), getString(R.string.sabCEN), getString(R.string.domCEN), getString(R.string.TextoCEN));
                titLatLon(getString(R.string.TitleCEN), getString(R.string.LatCEN), getString(R.string.LonCEN));
                break;
            case 1:
                infoBiblio(getString(R.string.textTiArt), getString(R.string.dirArt), getString(R.string.telArt), getString(R.string.lvArt), getString(R.string.sabArt), getString(R.string.domArt), getString(R.string.TextoArt));
                titLatLon(getString(R.string.TitleArt), getString(R.string.LatArt), getString(R.string.LonArt));
                break;
            case 2:
                infoBiblio(getString(R.string.textTiEco), getString(R.string.dirEco), getString(R.string.telEco), getString(R.string.lvEco), getString(R.string.sabEco), getString(R.string.domEco), getString(R.string.TextoEco));
                titLatLon(getString(R.string.TitleEco), getString(R.string.LatEco), getString(R.string.LonEco));
                break;
            case 3:
                infoBiblio(getString(R.string.textTiEdu), getString(R.string.dirEdu), getString(R.string.telEdu), getString(R.string.lvEdu), getString(R.string.sabEdu), getString(R.string.domEdu), getString(R.string.TextoEdu));
                titLatLon(getString(R.string.TitleEdu), getString(R.string.LatEdu), getString(R.string.LonEdu));
                break;
            case 4:
                infoBiblio(getString(R.string.textTiIdi), getString(R.string.dirIdi), getString(R.string.telIdi), getString(R.string.lvIdi), getString(R.string.sabIdi), getString(R.string.domIdi), getString(R.string.TextoIdi));
                titLatLon(getString(R.string.TitleIdi), getString(R.string.LatIdi), getString(R.string.LonIdi));
                break;
            case 5:
                infoBiblio(getString(R.string.textTiEC), getString(R.string.dirEC), getString(R.string.telEC), getString(R.string.lvEC), getString(R.string.sabEC), getString(R.string.domEC), getString(R.string.TextoEC));
                titLatLon(getString(R.string.TitleEC), getString(R.string.LatEC), getString(R.string.LonEC));
                break;
            case 6:
                infoBiblio(getString(R.string.textTiEP), getString(R.string.dirEP), getString(R.string.telEP), getString(R.string.lvEP), getString(R.string.sabEP), getString(R.string.domEP), getString(R.string.TextoEP));
                titLatLon(getString(R.string.TitleEP), getString(R.string.LatEP), getString(R.string.LonEP));
                break;
            case 7:
                titLatLon(getString(R.string.TitleJL), getString(R.string.LatJL), getString(R.string.LonJL));
                infoBiblio(getString(R.string.textTiJL), getString(R.string.dirJL), getString(R.string.telJL), getString(R.string.lvJL), getString(R.string.sabJL), getString(R.string.domJL), getString(R.string.TextoJL));
                break;

            case 8:
                infoBiblio(getString(R.string.textTiCIDUA), getString(R.string.dirCIDUA), getString(R.string.telCIDUA), getString(R.string.lvCIDUA), getString(R.string.sabCIDUA), getString(R.string.domCIDUA), getString(R.string.TextoCIDUA));
                titLatLon(getString(R.string.TitleCIDUA), getString(R.string.LatCIDUA), getString(R.string.LonCIDUA));
                break;
            case 9:
                infoBiblio(getString(R.string.textTiCIIC), getString(R.string.dirCIIC), getString(R.string.telCIIC), getString(R.string.lvCIIC), getString(R.string.sabCIIC), getString(R.string.domCIIC), getString(R.string.TextoCIIC));
                titLatLon(getString(R.string.TitleCIIC), getString(R.string.LatCIIC), getString(R.string.LonCIIC));
                break;
            case 10:
                infoBiblio(getString(R.string.textTiCISH), getString(R.string.dirCISH), getString(R.string.telCISH), getString(R.string.lvCISH), getString(R.string.sabCISH), getString(R.string.domCISH), getString(R.string.TextoCISH));
                titLatLon(getString(R.string.TitleCISH), getString(R.string.LatCISH), getString(R.string.LonCISH));
                break;
            case 11:
                infoBiblio(getString(R.string.textTiMR), getString(R.string.dirMR), getString(R.string.telMR), getString(R.string.lvMR), getString(R.string.sabMR), getString(R.string.domMR), getString(R.string.TextoMR));
                titLatLon(getString(R.string.TitleMR), getString(R.string.LatMR), getString(R.string.LonMR));
                break;
            case 12:
                infoBiblio(getString(R.string.textTiINER), getString(R.string.dirINER), getString(R.string.telINER), getString(R.string.lvINER), getString(R.string.sabINER), getString(R.string.domINER), getString(R.string.TextoINER));
                titLatLon(getString(R.string.TitleINER), getString(R.string.LatINER), getString(R.string.LonINER));
                break;

        }

    }

    public void infoMede(int pos){
        switch (pos){
            case 0:
                infoBiblio(getString(R.string.textTiCGD), getString(R.string.dirCGD), getString(R.string.telCGD), getString(R.string.lvCGD), getString(R.string.sabCGD), getString(R.string.domCGD), getString(R.string.TextoCGD));
                titLatLon(getString(R.string.TitleCGD), getString(R.string.LatCGD), getString(R.string.LonCGD));
                break;
            case 1:
                infoBiblio(getString(R.string.textTiMedica), getString(R.string.dirMedica), getString(R.string.telMedica), getString(R.string.lvMedica), getString(R.string.sabMedica), getString(R.string.domMedica), getString(R.string.TextoMedica));
                titLatLon(getString(R.string.TitleMedica), getString(R.string.LatMedica), getString(R.string.LonMedica));
                break;
            case 2:
                infoBiblio(getString(R.string.textTiSaP), getString(R.string.dirSaP), getString(R.string.telSaP), getString(R.string.lvSaP), getString(R.string.sabSap), getString(R.string.domSaP), getString(R.string.TextoSaP));
                titLatLon(getString(R.string.TitleSaP), getString(R.string.LatSaP), getString(R.string.LonSaP));
                break;
            case 3:
                infoBiblio(getString(R.string.textTiOdo), getString(R.string.dirOdo), getString(R.string.telOdo), getString(R.string.lvOdo), getString(R.string.sabOdo), getString(R.string.domOdo), getString(R.string.TextoOdo));
                titLatLon(getString(R.string.TitleOdo), getString(R.string.LatOdo), getString(R.string.LonOdo));
                break;
            case 4:
                infoBiblio(getString(R.string.textTiEn), getString(R.string.dirEn), getString(R.string.telEn), getString(R.string.lvEn), getString(R.string.sabEn), getString(R.string.domEn), getString(R.string.TextoEn));
                titLatLon(getString(R.string.TitleEn), getString(R.string.LatEn), getString(R.string.LonEn));
                break;
            case 5:
                infoBiblio(getString(R.string.textTiRo), getString(R.string.dirRo), getString(R.string.telRo), getString(R.string.lvRo), getString(R.string.sabRo), getString(R.string.domRo), getString(R.string.TextoRo));
                titLatLon(getString(R.string.TitleRo), getString(R.string.LatRo), getString(R.string.LonRo));
                break;
            case 6:
                infoBiblio(getString(R.string.textTiBBT), getString(R.string.dirBBT), getString(R.string.telBBT), getString(R.string.lvBBT), getString(R.string.sabBBT), getString(R.string.domBBT), getString(R.string.TextoBBT));
                titLatLon(getString(R.string.TitleBBT), getString(R.string.LatBBT), getString(R.string.LonBBT));
                break;
            case 7:
                infoBiblio(getString(R.string.textTiBN), getString(R.string.dirBN), getString(R.string.telBN), getString(R.string.lvBN), getString(R.string.sabBN), getString(R.string.domBN), getString(R.string.TextoBN));
                titLatLon(getString(R.string.TitleBN), getString(R.string.LatBN), getString(R.string.LonBN));
                break;

        }
    }

    public void infoSecc(int pos) {
        switch (pos) {
            case 0:
                infoBiblio(getString(R.string.textTiCaucasia), getString(R.string.dirCaucasia), getString(R.string.telCaucasia), getString(R.string.lvCaucasia), getString(R.string.sabCaucasia), getString(R.string.domCaucasia), getString(R.string.TextoCaucasia));
                titLatLon(getString(R.string.TitleCaucasia), getString(R.string.LatCaucasia), getString(R.string.LonCaucasia));
                break;
            case 1:
                infoBiblio(getString(R.string.textTiMagdalena), getString(R.string.dirMagdalena), getString(R.string.telMagdalena), getString(R.string.lvMagdalena), getString(R.string.sabMagdalena), getString(R.string.domMagdalena), getString(R.string.TextoMagdalena));
                titLatLon(getString(R.string.TitleMagdalena), getString(R.string.LatMagdalena), getString(R.string.LonMagdalena));
                break;
            case 2:
                infoBiblio(getString(R.string.textTiYarumal), getString(R.string.dirYarumal), getString(R.string.telYarumal), getString(R.string.lvYarumal), getString(R.string.sabYarumal), getString(R.string.domYarumal), getString(R.string.TextoYarumal));
                titLatLon(getString(R.string.TitleYarumal), getString(R.string.LatYarumal), getString(R.string.LonYarumal));
                break;
            case 3:
                infoBiblio(getString(R.string.textTiSdA), getString(R.string.dirSdA), getString(R.string.telSdA), getString(R.string.lvSdA), getString(R.string.sabSdA), getString(R.string.domSdA), getString(R.string.TextoSdA));
                titLatLon(getString(R.string.TitleSdA), getString(R.string.LatSdA), getString(R.string.LonSdA));
                break;
            case 4:
                infoBiblio(getString(R.string.textTiCV), getString(R.string.dirCV), getString(R.string.telCV), getString(R.string.lvCV), getString(R.string.sabCV), getString(R.string.domCV), getString(R.string.TextoCV));
                titLatLon(getString(R.string.TitleCV), getString(R.string.LatCV), getString(R.string.LonCV));
                break;
            case 5:
                infoBiblio(getString(R.string.textTiSonson), getString(R.string.dirSonson), getString(R.string.telSonson), getString(R.string.lvSonson), getString(R.string.sabSonson), getString(R.string.domSonson), getString(R.string.TextoSonson));
                titLatLon(getString(R.string.TitleSonson), getString(R.string.LatSonson), getString(R.string.LonSonson));
                break;
            case 6:
                infoBiblio(getString(R.string.textTiAnd), getString(R.string.dirAnd), getString(R.string.telAnd), getString(R.string.lvAnd), getString(R.string.sabAnd), getString(R.string.domAnd), getString(R.string.TextoAnd));
                titLatLon(getString(R.string.TitleAnd), getString(R.string.LatAnd), getString(R.string.LonAnd));
                break;
            case 7:
                infoBiblio(getString(R.string.textTiTurbo), getString(R.string.dirTurbo), getString(R.string.telTurbo), getString(R.string.lvTurbo), getString(R.string.sabTurbo), getString(R.string.domTurbo), getString(R.string.TextoTurbo));
                titLatLon(getString(R.string.TitleTurbo), getString(R.string.LatTurbo), getString(R.string.LonTurbo));
                break;
        }
    }

    public void titLatLon(String tit, String lat, String lon){
        setTitulo(tit);
        setLatitud(lat);
        setLongitud(lon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("ActionBar", "Atrás!");
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
