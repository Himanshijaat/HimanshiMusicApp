package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setClickable(true);
            thumbnail.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int pos=getPosition();
            switch (pos){
                case 0:
                    Toast.makeText(mContext, "1 pressed", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(mContext, "2 pressed", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    FestivalSongs frag=new FestivalSongs();
                case 3:
                    Toast.makeText(mContext, "4 pressed", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(mContext, "5 pressed", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(mContext, "6 pressed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartitem_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);



        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }
}