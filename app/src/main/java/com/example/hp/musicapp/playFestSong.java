package com.example.hp.musicapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pl.droidsonroids.gif.GifImageView;


public class playFestSong extends Fragment implements View.OnClickListener, MediaPlayer.OnCompletionListener,SeekBar.OnSeekBarChangeListener {

   int[] songList;
   Context context;
   MediaPlayer mp;
   GifImageView fullImage;
   private ImageView btnPlay;
   private ImageView btnNext;
   private ImageView btnStop;
   private SeekBar songProgressBar;
   private TextView songCurrentDuration;
   private TextView songTotalDuration;
   private int currentSongIndex = 0;
   private int[] pauseArray;
   Utilities utils;
   int dura;
   final Handler handler = new Handler();
   Runnable runnable;
   private final Handler mHandler = new Handler();

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_play_fest_song, container,false);
      context=getActivity();

      btnPlay = (ImageView) view.findViewById(R.id.play);
      btnStop = (ImageView) view.findViewById(R.id.stop);
      btnNext = (ImageView) view.findViewById(R.id.next);
      fullImage = (GifImageView) view.findViewById(R.id.fullImage);
      songProgressBar=(SeekBar)view.findViewById(R.id.seekBar);
      btnStop=(ImageView) view.findViewById(R.id.stop);
      songCurrentDuration=(TextView) view.findViewById(R.id.startTime);
      songTotalDuration=(TextView) view.findViewById(R.id.totalTime);
      utils=new Utilities();

      songList=getArguments().getIntArray("songList");
      pauseArray=getArguments().getIntArray("songImage");

      playSong(0);

      btnPlay.setOnClickListener(this);
      btnNext.setOnClickListener(this);
      btnStop.setOnClickListener(this);

      return view;
   }

   public void changeImage(final int[] imageArray){
      dura=mp.getDuration();
      handler.removeCallbacks(runnable);
      dura=dura/25;
      runnable = new Runnable() {
         int i = 0;

         @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
         public void run() {
            fullImage.setImageResource(imageArray[i]);
            i++;
            if (i >= 3) {
               i = 0;
            }
            handler.postDelayed(this, dura);  //changes after 3 sec
         }
      };
      handler.postDelayed(runnable, 0);
   }


   public void playSong(int songIndex) {
      // Play song
      try {
         mp= MediaPlayer.create(context,songList[songIndex]);
         mp.start();
         btnPlay.setImageResource(R.drawable.pause);
         changeImage(pauseArray);

         songProgressBar.setOnSeekBarChangeListener(this); // Important
         mp.setOnCompletionListener(this); // Important
         // Changing Button Image to pause image
         btnPlay.setImageResource(R.drawable.pause);
         // Updating progress bar
         updateProgressBar();

      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalStateException e) {
         e.printStackTrace();
      }
   }
   @Override
   public void onClick(View v){

      switch ((v.getId())){
         case R.id.play:
            playSong();
            break;
         case R.id.next:
            nextSong();
            break;
         case R.id.stop:
            stopSong();
            break;
      }

   }
   private void playSong() {
      // check for already playing
      if (mp.isPlaying()) {
         if (mp != null) {
            mp.pause();
            handler.removeCallbacks(runnable);
            // Changing button image to play button
            btnPlay.setImageResource(R.drawable.playm);
         }
      } else {
         // Resume song
         if (mp != null) {
            mp.start();
            handler.removeCallbacks(runnable);
            changeImage(pauseArray);
            // Changing button image to pause button
            btnPlay.setImageResource(R.drawable.pause);
            updateProgressBar();
         }
      }
   }
   private void nextSong() {
      Toast.makeText(context, "next song", Toast.LENGTH_SHORT).show();
      mp.reset();
      // check if next song is there or not
      if (currentSongIndex < (songList.length - 1)) {
         playSong(currentSongIndex + 1);
         currentSongIndex = currentSongIndex + 1;

      } else {
         // play first song
         playSong(0);
         currentSongIndex = 0;
      }

   }
   private void stopSong() {
      mp.stop();
      try {
         mp.prepare();
      } catch (IOException e) {
         e.printStackTrace();
      }
      songTotalDuration.setText("0:00");
      // Displaying time completed playing
      songCurrentDuration.setText("0:00");
      handler.removeCallbacks(runnable);
      // Updating progress bar
      songProgressBar.setProgress(0);
      mHandler.removeCallbacks(mUpdateTimeTask);
      btnPlay.setImageResource(R.drawable.playm);


   }
   public void updateProgressBar() {
      mHandler.postDelayed(mUpdateTimeTask, 100);
   }
   private Runnable mUpdateTimeTask = new Runnable() {
      public void run() {
         long totalDuration = mp.getDuration();
         long currentDuration = mp.getCurrentPosition();

         // Displaying Total Duration time
         songTotalDuration.setText("" + utils.milliSecondsToTimer(totalDuration));
         // Displaying time completed playing
         songCurrentDuration.setText("" + utils.milliSecondsToTimer(currentDuration));

         // Updating progress bar
         int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
         //Log.d("Progress", ""+progress);
         songProgressBar.setProgress(progress);

         // Running this thread after 100 milliseconds
         mHandler.postDelayed(this, 100);
      }
   };

   @Override
   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

   }

   /**
    * When user starts moving the progress handler
    */
   @Override
   public void onStartTrackingTouch(SeekBar seekBar) {
      // remove message Handler from updating progress bar
      mHandler.removeCallbacks(mUpdateTimeTask);
   }

   /**
    * When user stops moving the progress hanlder
    */
   @Override
   public void onStopTrackingTouch(SeekBar seekBar) {
      mHandler.removeCallbacks(mUpdateTimeTask);
      int totalDuration = mp.getDuration();
      int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

      // forward or backward to certain seconds
      mp.seekTo(currentPosition);

      // update timer progress again
      updateProgressBar();
   }


   @Override
   public void onCompletion(MediaPlayer mediaPlayer) {
      if (currentSongIndex < (songList.length - 1)) {
         playSong(currentSongIndex + 1);
         currentSongIndex = currentSongIndex + 1;
      } else {
         // play first song
         playSong(0);
         currentSongIndex = 0;
      }
   }

   @Override
   public void onPause() {
      super.onPause();
      btnPlay.setImageResource(R.drawable.playm);
      mp.pause();
      handler.removeCallbacks(runnable);
   }
   @Override
   public void onResume() {
      super.onResume();
      if (mp != null) {
         mp.start();
         handler.postDelayed(runnable,dura);
         // Changing button image to pause button
         btnPlay.setImageResource(R.drawable.pause);
         updateProgressBar();
      }
   }
}