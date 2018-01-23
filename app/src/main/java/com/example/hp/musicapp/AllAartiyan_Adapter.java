package com.example.hp.musicapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HimanshiJaat on 07/01/2018.
 */
public class AllAartiyan_Adapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Main_Bean> arraylist;
    private String TAG = MainActivity.class.getSimpleName();
    int pos;
    private NotificationManager mNotifyManager;
    NotificationCompat.Builder build;
    int id = 1;
    int boxState[];
    TextView name;
    RelativeLayout relativeLayout;
    String path = Environment.getExternalStorageDirectory() + "/BhaktiMusic";
    File directory = new File(path);
    File[] folderSongList = directory.listFiles();
    String[] strFileName;
    String[] arrayFileName;
    TextView textview_genre;
    DatabaseHelperForSongList db;
    String path1 = Environment.getExternalStorageDirectory() + "/BhaktiMusic/Images/";
    File directory1 = new File(path1);
    File[] folderImageList = directory1.listFiles();
    FilesUtils filesUtils;
    int currentValue=0;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AllAartiyan_Adapter(Context context, ArrayList<Main_Bean> data) {
        mContext = context;
        this.arraylist = data;
        inflater = LayoutInflater.from(mContext);
        boxState = new int[arraylist.size()];
        for (int i = 0; i < arraylist.size(); i++) {
            boxState[i] = 0;
        }
        arrayFileName = new String[arraylist.size()];
        filesUtils = new FilesUtils();

    }

    @Override
    public int getCount() {

        return arraylist.size();
    }

    @Override
    public Main_Bean getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.aartiyan_itemlayout, parent, false);
        final Button download;
        name = (TextView) view.findViewById(R.id.textview_aartiyan);
        download = (Button) view.findViewById(R.id.download_btn);
        final Button play_btn = (Button) view.findViewById(R.id.play_btn);
        textview_genre = (TextView) view.findViewById(R.id.textview_genre);
        final CircularProgressBar progressBar1 = (CircularProgressBar) view.findViewById(R.id.progressBar1);
        name.setText(arraylist.get(position).getName());
        textview_genre.setText(arraylist.get(position).getGenre());
        download.setTag(position);

        getFolderSongList();

        if (boxState[position] == 0) {
            download.setVisibility(View.VISIBLE);
            play_btn.setVisibility(View.INVISIBLE);
        } else if(boxState[position]==1) {
            download.setVisibility(View.INVISIBLE);
            play_btn.setVisibility(View.VISIBLE);
        }else {
            download.setVisibility(View.INVISIBLE);
            play_btn.setVisibility(View.INVISIBLE);
            progressBar1.setVisibility(View.VISIBLE);
            progressBar1.setProgress(currentValue);
        }


        download.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                download.setTag(position);

                pos = (Integer) view.getTag();

                mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                build = new NotificationCompat.Builder(mContext);
                build.setContentTitle("Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.ic_launcher);
                boxState[pos]=2;
                download.setVisibility(View.INVISIBLE);
                progressBar1.setVisibility(View.VISIBLE);

                new download(download, progressBar1, play_btn).execute();
                List<String> finalImageList = getImageFilesToDownload();
                for (int i = 0; i < finalImageList.size(); i++) {
                    Log.d(TAG, String.valueOf(finalImageList));
                    new Imagedownload().execute(finalImageList.get(i).toString());

                }

                Log.d(TAG, "inside download clicked");
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play_Songs frag = new Play_Songs();
                Bundle bundle = new Bundle();
                frag.setArguments(bundle);
                FragmentManager fm = ((Activity) mContext).getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.framelayout, frag).addToBackStack(null).commit();
                Toast.makeText(mContext, "name " + strFileName[position].toString()+" pos "+position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private List<String> getImageFilesToDownload() {
        db = new DatabaseHelperForSongList(mContext);
        String songId = arraylist.get(pos).getTrackId();
        Log.d(TAG,"getImagefiletodownload     "+    songId);
        String imageArray = db.getImageList(songId);
        Log.d(TAG,"getImagefiletodownload     "+    imageArray);
        String[] folderimagelist = new String[folderImageList.length];

        Log.d(TAG, "" + imageArray);
        String[] imagelist = imageArray.split("\\s*,\\s*");
        Log.d(TAG, "" + imagelist[0]);
        Log.d(TAG, "" + imagelist[1]);
        Log.d(TAG, "" + imagelist[2]);
        for (int i = 0; i < folderimagelist.length; i++) {
            folderimagelist[i] = folderImageList[i].getName().toString();
        }
        List<String> mylist = new ArrayList<>(Arrays.asList(imagelist));
        List<String> myfolderlist = new ArrayList<>(Arrays.asList(folderimagelist));
        List<String> imageName = new ArrayList<>(mylist);
        imageName.removeAll(myfolderlist);

        return imageName;
    }

    private void getFolderSongList() {

        for (int i = 0; i < arrayFileName.length; i++) {
            arrayFileName[i] = arraylist.get(i).getName().toString();
        }
        List<String> fileName=new ArrayList<>();
        for (int i = 0; i < folderSongList.length; i++) {
            //Log.d("folderSongList size", "" + folderSongList[i].getName().substring(0, folderSongList[i].getName().lastIndexOf(".")));
            if(folderSongList[i].isFile()){
                fileName.add(folderSongList[i].getName().toString());

            }
        }
        for (int i = 0; i < fileName.size(); i++) {
            Log.d("isFile", "" + fileName.get(i).toString());
        }

        strFileName=new String[fileName.size()];
        for (int i = 0; i < fileName.size(); i++) {
            strFileName[i] = fileName.get(i).toString().substring(0, fileName.get(i).toString().lastIndexOf("."));
            //fileName[i] = folderSongList[i].getName().toString();

        }

        for (int i = 0; i < strFileName.length; i++) {

            for (int j = 0; j < arraylist.size(); j++) {

                if (strFileName[i].toString().equalsIgnoreCase(arrayFileName[j])) {

                    Log.d("matched", "" + strFileName[i].toString());
                    boxState[j] = 1;

                } else {
                    //nothing
                }
            }
        }

    }


    class download extends AsyncTask<Void, Integer, Void> {
        Button download;
        CircularProgressBar progressBar;
        Button play_btn;

        download(Button download, CircularProgressBar progressBar, Button play_btn) {
            this.download = download;
            this.progressBar = progressBar;
            this.play_btn = play_btn;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            build.setProgress(100, 0, false);
            mNotifyManager.notify(id, build.build());
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onProgressUpdate(Integer... values) {
            build.setProgress(100, values[0], false);
            mNotifyManager.notify(id, build.build());
            progressBar.setProgress(values[0]);
            currentValue=values[0];
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = "http://arbon.in/songsDownload.php?song_id=";
            String song = arraylist.get(pos).getTrackId();
            Log.d(TAG, song);
            url = url + song;
            Log.d(TAG, url);
            int i;
            for (i = 0; i <= 100; i += 5) {
                // Sets the progress indicator completion percentage
                publishProgress(Math.min(i, 100));

                // Sleep for 5 seconds
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
 Log.d(TAG, jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject object = new JSONObject(jsonStr);
                    String name = object.getString("title");
                    String data = object.getString("data");
                    Log.d(TAG, name);
                    File file = new File(Environment.getExternalStorageDirectory() + "/BhaktiMusic/" + name+".mp3");
                    FileOutputStream os = new FileOutputStream(file, true);
                    os.write(Base64.decode(data, Base64.NO_WRAP));
                    os.flush();
                    os.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            build.setContentText("Download complete");
            // Removes the progress bar
            build.setProgress(0, 0, false);
            mNotifyManager.notify(id, build.build());
            progressBar.setVisibility(View.INVISIBLE);
            download.setVisibility(View.INVISIBLE);
            // download.setBackgroundResource(R.drawable.playm);
            play_btn.setVisibility(View.VISIBLE);
            boxState[pos] = 1;

        }


    }

    class Imagedownload extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String url = "http://arbon.in/imagesDownload.php?imageName=";
            String imagename = params[0];
            url = url + imagename;
            Log.d(TAG, "imagename image download"+    imagename);
            Log.d("url ",""+url);
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            //Log.d(TAG, jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject object = new JSONObject(jsonStr);
                    String name = object.getString("fileName");
                    String data = object.getString("data");
                    Log.d(TAG, name);

                    File file = new File(Environment.getExternalStorageDirectory() + "/BhaktiMusic/Images/" + name);
                    FileOutputStream os = new FileOutputStream(file, true);
                    os.write(Base64.decode(data, Base64.NO_WRAP));
                    os.flush();
                    os.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

    }
}
