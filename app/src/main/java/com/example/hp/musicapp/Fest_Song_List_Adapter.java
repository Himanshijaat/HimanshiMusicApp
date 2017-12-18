package com.example.hp.musicapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SANTOSH on 18-Dec-17.
 */

@RequiresApi(api = Build.VERSION_CODES.O)
public class Fest_Song_List_Adapter extends BaseAdapter {

    String[] fest_Names;
    int[] fest_Images;
    FestivalSongs context;

    public Fest_Song_List_Adapter(FestivalSongs context,String[] fest_Names, int[] fest_Images) {
        this.fest_Names = fest_Names;
        this.fest_Images = fest_Images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fest_Names.length;
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        LayoutInflater inflater=context.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.custom_festive_song_list,null,true);
        TextView fest_Name=(TextView) view.findViewById(R.id.festivalName);

        ImageView fest_Image=(ImageView)view.findViewById(R.id.festivalImage);
        fest_Name.setText(fest_Names[position]);
        fest_Image.setClipToOutline(true);
        fest_Image.setImageResource(fest_Images[position]);

        return rowView;
    }
}
