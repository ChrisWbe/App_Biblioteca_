package com.example.christianquintero.app_biblioteca_;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private final long SPLASH_DELAY =  3000;
    ImageView fondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        fondo  = (ImageView)findViewById(R.id.splash_fondo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fondo.startAnimation(animation);


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            //Toast.makeText(this, sharedPreferences.getString(getString(R.string.pass), "No está"), Toast.LENGTH_LONG).show();
            Intent intent = new Intent().setClass(Splash.this, Principal.class);
            startActivity(intent);
            finish();

            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, SPLASH_DELAY);
    }
}
