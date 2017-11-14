package com.example.christianquintero.app_biblioteca_;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

        SendPostRequest asyncTask;
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
                Bitmap[] imagenes = new Bitmap[getNumero_prestamos()];
                String[] signature = new String[getNumero_prestamos()];
                String[] titles = new String[getNumero_prestamos()];
                String[] localizaciones = new String[getNumero_prestamos()];
                String[] devoluciones = new String[getNumero_prestamos()];
                String[] multas = new String[getNumero_prestamos()];

                DownLoadFilesTask downLoadImages;

                for(int i = 0; i<getNumero_prestamos(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    signature[i] = jsonObject.getString("signatura");
                    titles[i] = jsonObject.getString("titulo");
                    localizaciones[i] = jsonObject.getString("localizacion");
                    devoluciones[i] = jsonObject.getString("fecha_devolucion");
                    multas[i] = jsonObject.getString("multa");

                    downLoadImages = new DownLoadFilesTask();
                    downLoadImages.execute(jsonObject.getString("image_url"));

                    imagenes[i] = downLoadImages.get();

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

    public class SendPostRequest extends AsyncTask<String, Integer, String>{
        private ProgressDialog progresoHis;

        @Override
        protected void onPreExecute() {
            progresoHis = new ProgressDialog(HistorialPrestamos.this);
            progresoHis.setMessage("Procesando...");
            progresoHis.setCancelable(false);
            progresoHis.setProgress(0);
            progresoHis.setMax(100);
            progresoHis.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                for(int i = 0; i<10; i++){
                    publishProgress(i*10);
                }
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

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            progresoHis.setProgress(progreso);
        }

        @Override
        protected void onPostExecute(String s) {
            progresoHis.dismiss();
        }
    }

    private class DownLoadFilesTask extends AsyncTask<String, Void, Bitmap>{
        Bitmap bm = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL urlImage = new URL(params[0]);
                URLConnection con = urlImage.openConnection();
                con.connect();
                InputStream is = con.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return bm;
        }

    }


    public class MyAdapter extends ArrayAdapter{
        Bitmap[] imageArray;
        String[] signatureArray;
        String[] titleArray;
        String[] localizacionArray;
        String[] devolucionArray;
        String[] multaArray;

        public MyAdapter(Context context, Bitmap[] img, String[] signature, String[] title, String[] local, String[] dev, String[] multa) {
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

            imageView.setImageBitmap(imageArray[position]);
            textViewSignature.setText(signatureArray[position]);
            textViewTitle.setText(titleArray[position]);
            textViewLocal.setText(localizacionArray[position]);
            textViewDev.setText(devolucionArray[position]);
            if(Integer.valueOf(multaArray[position]) == 0){
                TextView tituloMulta = (TextView)row.findViewById(R.id.tituloMultaHistori);
                tituloMulta.setVisibility(View.GONE);
                textViewMulta.setVisibility(View.GONE);
            }else{
                textViewMulta.setText(multaArray[position]);
            }


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
