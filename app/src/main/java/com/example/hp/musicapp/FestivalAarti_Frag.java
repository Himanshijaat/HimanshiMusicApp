package com.example.hp.musicapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FestivalAarti_Frag extends Fragment {

    ListView festList;
    Utilities utils;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.festivalaarti_frag, container, false);
        int[] festImages = {R.drawable.holi, R.drawable.diwali, R.drawable.ramnavmi, R.drawable.navratre, R.drawable.guruparv, R.drawable.christmas,};
        String[] festNames = {getString(R.string.fest_diwali), getString(R.string.fest_ram_navami), getString(R.string.fest_navratre)};
        utils = new Utilities();
        final int[] ganeshImage = utils.ganeshImage;
        final int[] laxmiImage = utils.laxmiImage;
        final int[] hanumanImage = utils.hanumanImage;

        final int[] diwaliSongList = {R.raw.c3la, R.raw.d4lc};
        final int[] hanumanSongList = {R.raw.e5ha, R.raw.f6hc};
        final int[] janmastmiSongList = {R.raw.a1ga, R.raw.b2gc};


        festList = (ListView) view.findViewById(R.id.festivalSongList);
        festList.setAdapter(new FestivalAarti_Adapter(getActivity(), festImages, festNames));

        festList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                                if (pos == 0) {
                                                    playFestSong playsongs = new playFestSong();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putIntArray("songList", diwaliSongList);
                                                    bundle.putIntArray("songImage", laxmiImage);
                                                    playsongs.setArguments(bundle);
                                                    FragmentManager frag = ((Activity) getActivity()).getFragmentManager();
                                                    FragmentTransaction ft = frag.beginTransaction();
                                                    ft.replace(R.id.framelayout, playsongs);
                                                    ft.addToBackStack(null);
                                                    ft.commit();


                                                } else if (pos == 1) {
                                                    playFestSong playsongs = new playFestSong();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putIntArray("songList", hanumanSongList);
                                                    bundle.putIntArray("songImage", hanumanImage);
                                                    playsongs.setArguments(bundle);
                                                    FragmentManager frag = ((Activity) getActivity()).getFragmentManager();
                                                    FragmentTransaction ft = frag.beginTransaction();
                                                    ft.replace(R.id.framelayout, playsongs);
                                                    ft.addToBackStack(null);
                                                    ft.commit();

                                                } else if (pos == 2) {
                                                    playFestSong playsongs = new playFestSong();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putIntArray("songList", janmastmiSongList);
                                                    bundle.putIntArray("songImage", ganeshImage);
                                                    playsongs.setArguments(bundle);
                                                    FragmentManager frag = ((Activity) getActivity()).getFragmentManager();
                                                    FragmentTransaction ft = frag.beginTransaction();
                                                    ft.replace(R.id.framelayout, playsongs);
                                                    ft.addToBackStack(null);
                                                    ft.commit();
                                                }
                                            }
                                        }
        );

        return view;
    }
}