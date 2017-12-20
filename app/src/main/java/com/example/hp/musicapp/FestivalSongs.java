package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FestivalSongs extends Fragment {

    ListView festList;
    Context context;

    public int [] festImages= {R.drawable.holi,R.drawable.diwali,R.drawable.ramnavmi,R.drawable.navratre,R.drawable.guruparv,R.drawable.christmas,};
    public String [] festNames={"Holi","Diwali","Ram Navami","Navratre","GuruParv","Christmas"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_festival_songs,container,false);

        festList=(ListView)view.findViewById(R.id.festivalSongList);
        festList.setAdapter(new Fest_Song_List_Adapter(getActivity(),festImages,festNames));
        return view;
    }

}
