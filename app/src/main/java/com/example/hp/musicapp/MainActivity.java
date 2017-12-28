package com.example.hp.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hp on 12/18/2017.
 */

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    TextToSpeech textToSpeech;
    String Formateddate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Date date=Calendar.getInstance().getTime();
        SimpleDateFormat sm=new SimpleDateFormat("EEEE,dd-MMMM-yyyy");
        Formateddate=sm.format(date);
        Toast.makeText(MainActivity.this, ""+Formateddate, Toast.LENGTH_SHORT).show();
        textToSpeech=new TextToSpeech(MainActivity.this,this);

        onInit(0);
        changeFrag(0);
    }
    public void changeFrag(int position) {
        switch (position) {
            case 0:
                HomeFragment frag0 = new HomeFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, frag0).commit();
                break;

        }
    }


    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
        int result = textToSpeech.setLanguage(new Locale("hi"));
        if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS", "This Language is not supported");
        } else {
            speakOut();
        }

    } else {
        Log.e("TTS", "Initilization Failed!");
    }
}
    private void speakOut() {
        String text1="Namaskar        ".toString();
        String text = Formateddate.toString();
        String total=text1.concat(text);
        textToSpeech.speak(total, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
