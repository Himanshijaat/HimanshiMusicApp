package com.example.hp.musicapp;

import android.app.Fragment;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FestivalAarti_Frag extends Fragment {

    ListView festList;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.festivalaarti_frag,container,false);
        int [] festImages= {R.drawable.holi,R.drawable.diwali,R.drawable.ramnavmi,R.drawable.navratre,R.drawable.guruparv,R.drawable.christmas,};
        String [] festNames={getString(R.string.fest_holi),getString(R.string.fest_diwali),
                getString(R.string.fest_ram_navami),getString(R.string.fest_navratre),
                getString(R.string.fest_guruparv),getString(R.string.fest_christmas)};

        festList=(ListView) view.findViewById(R.id.festivalSongList);
        festList.setAdapter(new FestivalAarti_Adapter(getActivity(),festImages,festNames));
        return view;
    }
}
