package com.example.hp.musicapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Main_Bean> albumList;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextToSpeech.OnInitListener {
        public TextView textView;
        public ImageView thumbnail;
        TextToSpeech textToSpeech;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            textView = (TextView) view.findViewById(R.id.textview);
            textToSpeech = new TextToSpeech(mContext, this);
            view.setOnClickListener(this);
            view.setClickable(true);
            thumbnail.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getPosition();
            switch (position) {
                case 0:
                    FestivalAarti_Frag frag4 = new FestivalAarti_Frag();
                    android.app.FragmentManager fm4 = ((Activity) mContext).getFragmentManager();
                    FragmentTransaction ft4 = fm4.beginTransaction();
                    ft4.replace(R.id.framelayout, frag4);
                    ft4.addToBackStack(null);
                    ft4.commit();
                    int i = 0;
                    if (i == TextToSpeech.SUCCESS) {

                        int result = textToSpeech.setLanguage(new Locale("hi"));


                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {

                            String text = "त्योहारों की आरतियाँ".toString();
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                    break;
                case 1:
                    All_Aartiyan_frag frag1 = new All_Aartiyan_frag();
                    android.app.FragmentManager fm1 = ((Activity) mContext).getFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction();
                    ft1.replace(R.id.framelayout, frag1);
                    ft1.addToBackStack(null);
                    ft1.commit();
                    i = 0;
                    if (i == TextToSpeech.SUCCESS) {

                        int result = textToSpeech.setLanguage(new Locale("hi"));


                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {

                            String text = "सभी आरतियाँ".toString();
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                    // Toast.makeText(mContext, "all aartiyan", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Pooja_Paath_frag frag2 = new Pooja_Paath_frag();
                    android.app.FragmentManager fm2 = ((Activity) mContext).getFragmentManager();
                    FragmentTransaction ft2 = fm2.beginTransaction();
                    ft2.replace(R.id.framelayout, frag2);
                    ft2.addToBackStack(null);
                    ft2.commit();
                    i = 0;
                    if (i == TextToSpeech.SUCCESS) {

                        int result = textToSpeech.setLanguage(new Locale("hi"));


                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {

                            String text = "Pooja paath".toString();
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                    // Toast.makeText(mContext, "pooja paath frag", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                     /*Horroscope_frag frag3=new Horroscope_frag();
                     android.app.FragmentManager fm3=((Activity)mContext).getFragmentManager();
                     FragmentTransaction ft3=fm3.beginTransaction();
                     ft3.replace(R.id.framelayout,frag3);
                     ft3.addToBackStack(null);
                     ft3.commit();*/
                    dragThali frag3 = new dragThali();
                    android.app.FragmentManager fm3 = ((Activity) mContext).getFragmentManager();
                    FragmentTransaction ft3 = fm3.beginTransaction();
                    ft3.replace(R.id.framelayout, frag3);
                    ft3.addToBackStack(null);
                    ft3.commit();

                    i = 0;
                    if (i == TextToSpeech.SUCCESS) {

                        int result = textToSpeech.setLanguage(new Locale("hi"));


                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {

                            String text = "राशि फल".toString();
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                    //Toast.makeText(mContext, "horroscope frag", Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                   Dainik_Aartiyan_frag frag5 = new Dainik_Aartiyan_frag();
                    android.app.FragmentManager fm5 = ((Activity) mContext).getFragmentManager();
                    FragmentTransaction ft5 = fm5.beginTransaction();
                    ft5.replace(R.id.framelayout, frag5);
                    ft5.addToBackStack(null);
                    ft5.commit();
                    i = 0;
                    if (i == TextToSpeech.SUCCESS) {

                        int result = textToSpeech.setLanguage(new Locale("hi"));


                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {

                            String text = "Dainik Aartiyan".toString();
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                    // Toast.makeText(mContext, "dainik aartiyan frag", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        @Override
        public void onInit(int i) {

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