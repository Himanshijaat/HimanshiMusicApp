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
import android.widget.Toast;

import java.util.List;

/**
 * Created by SANTOSH on 18-Dec-17.
 */

@RequiresApi(api = Build.VERSION_CODES.O)
public class Fest_Song_List_Adapter extends BaseAdapter {

    String[] festName;
    Context context;
    int[] festImage;
    private static LayoutInflater inflater=null;

    public Fest_Song_List_Adapter(Context context, int[] festImage, String[] festName) {
        this.festName = festName;
        this.context = context;
        this.festImage = festImage;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return festName.length;
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    public class Holder {
        TextView festName;
        ImageView festImage;

    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Holder holder=new Holder();
        View rowView=inflater.inflate(R.layout.custom_festive_song_list,null);
        holder.festName=(TextView) rowView.findViewById(R.id.festivalName);
        holder.festImage=(ImageView)rowView.findViewById(R.id.festivalImage);
        holder.festImage.setImageResource(festImage[position]);
        holder.festName.setText(festName[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked "+position, Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}
