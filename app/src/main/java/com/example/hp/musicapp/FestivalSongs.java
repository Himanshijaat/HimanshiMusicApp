package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SANTOSH on 18-Dec-17.
 */

public class FestivalSongs extends Fragment {

    ListView lv;
    FestivalSongs context;
    ArrayList festName;

    public int [] festImages={R.drawable.holi,R.drawable.diwali,R.drawable.ramnavmi,R.drawable.navratre,R.drawable.guruparv,R.drawable.christmas,};
    public String [] festNames={"Holi","Diwali","Ram Navami","Navratre","Guru Parv","Christmas"};
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.festival_songs,container,false);
        context=this;
        lv=(ListView)view.findViewById(R.id.festivalSongList);

        Fest_Song_List_Adapter adapter=new Fest_Song_List_Adapter(this,festNames,festImages);
        lv.setAdapter(adapter);

        return view;
    }

}
