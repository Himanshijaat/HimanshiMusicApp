package com.example.hp.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by hp on 12/18/2017.
 */

public class MainActivity extends AppCompatActivity {
    FilesUtils filesUtils;
    int[] songList = {R.raw.a1ga, R.raw.b2gc, R.raw.d4lc, R.raw.f6hc};
    String[] songName = {"Jai Dev Jai MangalMurti Aarti", "Jai Ganesh Jai Ganesh Deva Aarti", "Lakshmi Chalisa", "Shree Hanuman Chalisa"};
    String[] songId={"SG01","SG02","SG03","SG04",};
    ArrayList<Main_Bean> arraylist;
    final int[] defaultImages = {R.drawable.ganeshgif1, R.drawable.laxmi1, R.drawable.hanuman1};
    String[] imageName={"ganeshji","laxmiji","hanumanji"};

    DatabaseHelperForSongList db;
    android.support.v7.widget.Toolbar toolbar;
    Button btn_refresh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        btn_refresh=(Button)toolbar.findViewById(R.id.btn_refresh);
        btn_refresh.setVisibility(View.INVISIBLE);
        filesUtils = new FilesUtils(MainActivity.this);
        //db=new DatabaseHelperForSongList(MainActivity.this);
        arraylist = new ArrayList<>();

        if (filesUtils.checkFolder()) {
            // Toast.makeText(MainActivity.this, "folder found", Toast.LENGTH_SHORT).show();
        } else {
            //creates folder for app
            filesUtils.CreateDir();
            //copy songs for first time
            String path = Environment.getExternalStorageDirectory() + "/BhaktiMusic";
            File dir = new File(path);
            for (int i = 0; i < songList.length; i++) {
                try {

                    if (dir.exists()) {
                        String str_song_name = songName[i] + ".mp3";
                        CopyRAWtoSDCard(songList[i], path + File.separator + str_song_name);
                        Log.d("data", "all songs save");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
 /*myBean=new Main_Bean();
 for (int j = 0; j < songName.length; j++) {

 myBean.setName(songName[j]);
 myBean.setTrackId(songId[j]);

 arraylist.add(myBean);
 db.addSong(arraylist);
 }*/
        }

        if (filesUtils.checkImageFolder()) {
            // Toast.makeText(MainActivity.this, "folder found", Toast.LENGTH_SHORT).show();

        } else {
            //creates folder for app
            filesUtils.CreateImageDir();

            //copy images for first time
            String path = Environment.getExternalStorageDirectory() + "/BhaktiMusic/Images";
            File dir = new File(path);
            for (int i = 0; i < defaultImages.length; i++) {
                try {

                    if (dir.exists()) {
                        String str_image_name = imageName[i] + ".gif";
                        CopyRAWtoSDCard(defaultImages[i], path + File.separator + str_image_name);
                        Log.d("data", "all images save");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }

        changeFrag(0);
    }

    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }



    public void changeFrag(int position) {
        switch (position) {
            case 0:
                HomeFragment frag0 = new HomeFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, frag0).commit();
                break;
        }
    }
}
