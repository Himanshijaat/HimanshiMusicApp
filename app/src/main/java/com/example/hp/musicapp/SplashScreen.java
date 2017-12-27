package com.example.hp.musicapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    private final int splashLenght=5000;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash_screen);
        mediaPlayer=MediaPlayer.create(SplashScreen.this,R.raw.om_splash);
        mediaPlayer.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(SplashScreen.this, Login_Page.class);
                startActivity(main);
                finish();
            }
        }, splashLenght);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
        finish();
    }
}
