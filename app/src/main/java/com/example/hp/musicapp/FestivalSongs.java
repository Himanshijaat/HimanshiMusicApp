package com.example.hp.musicapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class FestivalSongs extends AppCompatActivity {

    ListView festList;
    Context context;

    public int [] festImages= {R.drawable.holi,R.drawable.diwali,R.drawable.ramnavmi,R.drawable.navratre,R.drawable.guruparv,R.drawable.christmas,};
    public String [] festNames={"Holi","Diwali","Ram Navami","Navratre","GuruParv","Christmas"};


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_songs);

        context=this;

        festList=findViewById(R.id.festivalSongList);
        festList.setAdapter(new Fest_Song_List_Adapter(this,festImages,festNames));
    }
}
