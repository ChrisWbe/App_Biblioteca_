package com.example.christianquintero.app_biblioteca_;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtilsHC4;
import org.apache.http.util.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import android.support.design.widget.TextInputEditText;

public class Login extends AppCompatActivity{

    public static final String nameFyle = "loginUser";
    Button buttonLog;
    TextInputEditText user, pass;
    ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);


        buttonLog = (Button) findViewById(R.id.loginButton);
        user = (TextInputEditText) findViewById(R.id.edit_user);
        pass = (TextInputEditText) findViewById(R.id.edit_pass);
        imagen = (ImageView) findViewById(R.id.imageView);

        if(!comprobarConexion(this)){
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_login), getText(R.string.conecction), Snackbar.LENGTH_INDEFINITE).setAction("Cerrar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            snackbar.show();
        }


        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(!comprobarConexion(getApplication())){
                final Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_login), getText(R.string.conecction), Snackbar.LENGTH_INDEFINITE).setAction("Cerrar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                snackbar.show();

            }else{
                String usuario, contraseña;
                usuario = user.getText().toString();
                contraseña = pass.getText().toString();


                if(TextUtils.isEmpty(usuario)){
                    user.setError(getText(R.string.errorVacio));
                    user.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(contraseña)){
                    pass.setError(getText(R.string.errorVacio));
                    pass.requestFocus();
                    return;
                }else{

                    AsyncTask<String, Void, String> asyncTask;
                    asyncTask = new SendPostRequest();
                    asyncTask.execute(user.getText().toString(), pass.getText().toString());
                    try {

                        JSONObject jsonObject = new JSONObject(asyncTask.get());
                        if(Boolean.valueOf(jsonObject.getBoolean("VALIDADO"))){

                            saveProfile();
                            Intent intent = new Intent(Login.this, Principal.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out );
                            finish();
                        }else{
                            Toast.makeText(getBaseContext(), jsonObject.getString("RESULTADO"), Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
            }
        });


    }


    private void saveProfile(){
        String nameUser = user.getText().toString();
        String passUser = pass.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(nameFyle, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.usuario), nameUser);
        editor.putString(getString(R.string.pass), passUser);
        editor.commit();

    }



    public static boolean comprobarConexion(Context context){
        boolean conect = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] redes = connectivityManager.getAllNetworkInfo();
        for(int i = 0; i < redes.length; i++){
            if(redes[i].getState() == NetworkInfo.State.CONNECTED){
                conect = true;
            }
        }

        return conect;
    }
    public class SendPostRequest extends AsyncTask<String, Void, String>{


        @Override
        protected  String doInBackground(String... params) {

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://cirene.udea.edu.co/services_globales/validarusuarioPortalInterface.php");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("content-type", "application/json");

                JSONObject dato = new JSONObject();

                dato.accumulate("usuario", params[0]);
                dato.accumulate("clave", params[1]);
                StringEntity entity = new StringEntity(dato.toString());
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
}



