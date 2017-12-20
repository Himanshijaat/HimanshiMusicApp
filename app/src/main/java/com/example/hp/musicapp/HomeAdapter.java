package com.example.hp.musicapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Main_Bean> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            textView=(TextView)view.findViewById(R.id.textview);
            view.setOnClickListener(this);
            view.setClickable(true);
            thumbnail.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position=getPosition();
            switch (position) {
                case 0:
                    Panchang_Fragment frag0=new Panchang_Fragment();
                    android.app.FragmentManager fm=((Activity)mContext).getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.replace(R.id.framelayout,frag0);
                    ft.addToBackStack(null);
                    ft.commit();
                    Toast.makeText(mContext, "panchang_frag", Toast.LENGTH_SHORT).show();
           break;
                case 1:
                    All_Aartiyan_frag frag1=new All_Aartiyan_frag();
                    android.app.FragmentManager fm1=((Activity)mContext).getFragmentManager();
                    FragmentTransaction ft1=fm1.beginTransaction();
                    ft1.replace(R.id.framelayout,frag1);
                    ft1.addToBackStack(null);
                    ft1.commit();
                    Toast.makeText(mContext, "all aartiyan", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Pooja_Paath_frag frag2=new Pooja_Paath_frag();
                    android.app.FragmentManager fm2=((Activity)mContext).getFragmentManager();
                    FragmentTransaction ft2=fm2.beginTransaction();
                    ft2.replace(R.id.framelayout,frag2);
                    ft2.addToBackStack(null);
                    ft2.commit();
                    Toast.makeText(mContext, "pooja paath frag", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Horroscope_frag frag3=new Horroscope_frag();
                    android.app.FragmentManager fm3=((Activity)mContext).getFragmentManager();
                    FragmentTransaction ft3=fm3.beginTransaction();
                    ft3.replace(R.id.framelayout,frag3);
                    ft3.addToBackStack(null);
                    ft3.commit();
                    Toast.makeText(mContext, "horroscope frag", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    FestivalAarti_Frag frag4=new FestivalAarti_Frag();
                     android.app.FragmentManager fm4=((Activity)mContext).getFragmentManager();
                    FragmentTransaction ft4=fm4.beginTransaction();
                    ft4.replace(R.id.framelayout,frag4);
                    ft4.addToBackStack(null);
                    ft4.commit();
                    Toast.makeText(mContext, "festival aartiyan frag", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Dainik_Aartiyan_frag frag5=new Dainik_Aartiyan_frag();
                    android.app.FragmentManager fm5=((Activity)mContext).getFragmentManager();
                    FragmentTransaction ft5=fm5.beginTransaction();
                    ft5.replace(R.id.framelayout,frag5);
                    ft5.addToBackStack(null);
                    ft5.commit();
                    Toast.makeText(mContext, "dainik aartiyan frag", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }


    public HomeAdapter(Context mContext, List<Main_Bean> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homelayout_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Main_Bean album = albumList.get(position);
        holder.textView.setText(album.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }
}