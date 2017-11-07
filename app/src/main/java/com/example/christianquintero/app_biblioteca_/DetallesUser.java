package com.example.christianquintero.app_biblioteca_;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetallesUser extends Fragment {

    public TextView detailsName, detailsEmail, detailsIdent, detailsmulta;
    public ImageView detailsImg;
    public ListView lv;


    public DetallesUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] titlesList = {getString(R.string.hisPres), getString(R.string.reservas), getString(R.string.certi)};
        String[] des = {"Descripcion 1", "Descripcion 2", "Descripcion 3"};
        int[] image = {R.mipmap.ic_prestamos, R.mipmap.ic_reservas, R.mipmap.ic_certificados};

        detailsName = (TextView)view.findViewById(R.id.details_name);
        detailsEmail = (TextView)view.findViewById(R.id.details_email);
        detailsIdent = (TextView)view.findViewById(R.id.details_ident);
        detailsmulta = (TextView)view.findViewById(R.id.details_multa);
        detailsImg = (ImageView)view.findViewById(R.id.imageDetailsUser);

        lv = (ListView)view.findViewById(R.id.listLista);

        MyAdapter adapter = new MyAdapter(getActivity(), titlesList, des, image);
        lv.setAdapter(adapter);



        AsyncTask<String, Void, String> asyncTask;
        asyncTask = new SendPostRequest();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Login.nameFyle, Context.MODE_PRIVATE);
        String documento = sharedPreferences.getString(getString(R.string.identi), null);

        asyncTask.execute(documento, getString(R.string.appkey));
        try {

            JSONArray jsonArray = new JSONArray(asyncTask.get());
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            jsonArray = new JSONArray(jsonObject.getString("respuesta"));
            jsonObject = jsonArray.getJSONObject(0);

            detailsName.setText(jsonObject.getString("nombre"));
            detailsIdent.setText(jsonObject.getString("identificacion"));
            detailsEmail.setText(jsonObject.getString("email"));

            if(Integer.valueOf(jsonObject.getString("multa_actual")) == 0){
                detailsmulta.setText(getString(R.string.multaNula));
                detailsImg.setImageResource(R.mipmap.ic_like);
            }else{
                detailsmulta.setText(jsonObject.getString("multa_actual"));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public class SendPostRequest extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://cirene.udea.edu.co/services_OLIB/APP_ConsultarDetallesUsuario.php");

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
        int[] imagenArray;
        String[] titleArray;
        String[] descriptionArray;

        public MyAdapter(Context context, String[] titles1, String[] description1, int[] img1) {
            super(context, R.layout.listview_row, R.id.listTitle, titles1);
            this.imagenArray = img1;
            this.titleArray = titles1;
            this.descriptionArray = description1;
        }

        public View getView(int position,  View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listview_row, parent, false);

            ImageView myImage = (ImageView) row.findViewById(R.id.listIMage);
            TextView myTitle = (TextView)row.findViewById(R.id.listTitle);
            TextView myDesc = (TextView)row.findViewById(R.id.listDescription);

            myImage.setImageResource(imagenArray[position]);
            myTitle.setText(titleArray[position]);
            myDesc.setText(descriptionArray[position]);


            return row;
        }
    }
}



