package com.example.christianquintero.app_biblioteca_;


import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetallesUser extends Fragment {


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

    }

    public class SendPostRequest extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://cirene.udea.edu.co/services_OLIB/APP_ConsultarDetallesUsuario.php");

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("content-type", "application/json");

            JSONObject dato = new JSONObject();
            return null;
        }
    }
}

