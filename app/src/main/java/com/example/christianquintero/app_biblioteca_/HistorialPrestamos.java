package com.example.christianquintero.app_biblioteca_;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HistorialPrestamos extends AppCompatActivity {
    public Toolbar toolbar;
    public ListView listaHistorial;
    private String document;
    private int numero_prestamos = 0;

    public int getNumero_prestamos() {
        return numero_prestamos;
    }

    public void setNumero_prestamos(int numero_prestamos) {
        this.numero_prestamos = numero_prestamos;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_prestamos);

        toolbar = (Toolbar)findViewById(R.id.toolbar_historial_prestamos);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.hisPres));

        listaHistorial = (ListView)findViewById(R.id.listHistorialPrestamos);

        Bundle bundle = getIntent().getExtras();
        setDocument(bundle.getString("doc"));

        AsyncTask<String, Void, String> asyncTask;
        asyncTask = new SendPostRequest();

        asyncTask.execute(getDocument(), getString(R.string.appkey));

        try {
            JSONArray jsonArray = new JSONArray(asyncTask.get());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            jsonArray = new JSONArray(jsonObject.getString("respuesta"));

            setNumero_prestamos(jsonArray.length());
            if(getNumero_prestamos() == 0){
                Toast.makeText(getBaseContext() ,"No tienes prestamos en el momento", Toast.LENGTH_LONG).show();

            }else{
                int[] imagenes = new int[getNumero_prestamos()];
                String[] signature = new String[getNumero_prestamos()];
                String[] titles = new String[getNumero_prestamos()];
                String[] localizaciones = new String[getNumero_prestamos()];
                String[] devoluciones = new String[getNumero_prestamos()];
                String[] multas = new String[getNumero_prestamos()];

                for(int i = 0; i<getNumero_prestamos(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    System.out.println("Array length: "+jsonArray.getJSONObject(i));

                    imagenes[i] = android.R.drawable.ic_input_get;
                    signature[i] = jsonObject.getString("signatura");
                    titles[i] = jsonObject.getString("titulo");
                    localizaciones[i] = jsonObject.getString("localizacion");
                    devoluciones[i] = jsonObject.getString("fecha_devolucion");
                    multas[i] = jsonObject.getString("multa");
                    System.out.println("Url: "+jsonObject.getString("image_url"));

                }

                MyAdapter adapter = new MyAdapter(getBaseContext(), imagenes, signature, titles, localizaciones, devoluciones, multas);
                listaHistorial.setAdapter(adapter);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class SendPostRequest extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://cirene.udea.edu.co/services_OLIB/APP_ConsultarPrestados.php");

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("content-type", "application/json");

                JSONObject dato = new JSONObject();

                dato.accumulate("cedula" , params[0]);
                dato.accumulate("appKey", params[1]);

                JSONArray array = new JSONArray();
                array.put(dato);

                StringEntity entity = new StringEntity(array.toString());
                httpPost.setEntity(entity);

                HttpResponse response = httpClient.execute(httpPost);
                return EntityUtilsHC4.toString(response.getEntity());

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class MyAdapter extends ArrayAdapter{
        int[] imageArray;
        String[] signatureArray;
        String[] titleArray;
        String[] localizacionArray;
        String[] devolucionArray;
        String[] multaArray;

        public MyAdapter(Context context, int[] img, String[] signature, String[] title, String[] local, String[] dev, String[] multa) {
            super(context, R.layout.list_view_row_historial_prestamos, R.id.titleHistori, title);
            this.imageArray = img;
            this.signatureArray = signature;
            this.titleArray = title;
            this.localizacionArray = local;
            this.devolucionArray = dev;
            this.multaArray = multa;
        }

        public View getView(int position,  View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getApplication().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_view_row_historial_prestamos, parent, false);

            ImageView imageView = (ImageView)row.findViewById(R.id.imageHistori);
            TextView textViewSignature = (TextView)row.findViewById(R.id.SignatureHistori);
            TextView textViewTitle = (TextView)row.findViewById(R.id.titleHistori);
            TextView textViewLocal = (TextView)row.findViewById(R.id.localizacionHistori);
            TextView textViewDev = (TextView)row.findViewById(R.id.devolucionHistori);
            TextView textViewMulta = (TextView)row.findViewById(R.id.multaHistori);

            imageView.setImageResource(imageArray[position]);
            textViewSignature.setText(signatureArray[position]);
            textViewTitle.setText(titleArray[position]);
            textViewLocal.setText(localizacionArray[position]);
            textViewDev.setText(devolucionArray[position]);
            textViewMulta.setText(multaArray[position]);

            return row;
        }
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
