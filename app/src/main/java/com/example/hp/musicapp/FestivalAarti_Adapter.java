package com.example.hp.musicapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

/**
 * Created by SANTOSH on 18-Dec-17.
 */

@RequiresApi(api = Build.VERSION_CODES.O)
public class FestivalAarti_Adapter extends BaseAdapter {

    String[] festName;
    Context context;
    int[] festImage;
    private static LayoutInflater inflater=null;

    public FestivalAarti_Adapter(Context context, int[] festImage, String[] festName) {
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
        View rowView=inflater.inflate(R.layout.festivalaarti_item,null);
        holder.festName=(TextView) rowView.findViewById(R.id.festivalName);
        holder.festImage=(ImageView)rowView.findViewById(R.id.festivalImage);
        holder.festImage.setImageResource(festImage[position]);
        holder.festName.setText(festName[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:
                    {
                        Play_Songs playsongs=new Play_Songs();
                        FragmentManager frag=((Activity)context).getFragmentManager();
                        FragmentTransaction ft=frag.beginTransaction();
                        ft.replace(R.id.framelayout,playsongs);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }
            }
        });

        return rowView;
    }
}
