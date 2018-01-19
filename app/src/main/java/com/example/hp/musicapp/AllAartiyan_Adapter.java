package com.example.hp.musicapp;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    String path = Environment.getExternalStorageDirectory()+"/BhaktiMusic";
    File directory = new File(path);
    File[] folderSongList=directory.listFiles();
    String[] fileName;
    String[] arrayFileName;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AllAartiyan_Adapter(Context context, ArrayList<Main_Bean> data) {
        mContext = context;
        this.arraylist = data;
        inflater = LayoutInflater.from(mContext);
        boxState = new int[arraylist.size()];
        for (int i = 0; i < arraylist.size(); i++) {
            boxState[i] = 0;
        }
        fileName=new String[folderSongList.length];
        arrayFileName=new String[arraylist.size()];
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
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout);
        //view.setTag(holder);
        name.setText(arraylist.get(position).getName());

        download.setTag(position);

        if (boxState[position] == 0) {
            download.setBackgroundResource(R.drawable.download_icon);
        } else {
            download.setBackgroundResource(R.drawable.playm);
        }

        getFolderSongList();

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

                new download(download).execute();
                //download.setBackgroundResource(R.drawable.playm);

                Log.d(TAG, "inside download clicked");
            }
        });

        return view;
    }

    private void getFolderSongList() {
        for(int i=0;i<arrayFileName.length;i++){
            arrayFileName[i]=arraylist.get(i).getName().toString();
        }

        for (int i=0;i<fileName.length;i++) {
            fileName[i] = folderSongList[i].getName().substring(0, folderSongList[i].getName().lastIndexOf("."));
        }

        for(int i=0;i<fileName.length;i++){

            for(int j=0;j<arraylist.size();j++) {

                if( fileName[i].equalsIgnoreCase(arrayFileName[j])){

                    Log.d("matched",""+fileName[i]);
                    boxState[j] = 1;

                }else{
                    //nothing
                }
            }
        }

    }



    class download extends AsyncTask<Void, Integer, Void> {
        Button download;
        download(Button download){
            this.download=download;
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
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.d(TAG, jsonStr);
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

            if (jsonStr != null) {

                try {
                    JSONObject object = new JSONObject(jsonStr);
                    String name = object.getString("fileName");
                    String data = object.getString("data");
                    Log.d(TAG, name);
                    File file = new File(Environment.getExternalStorageDirectory() + "/BhaktiMusic/" + name);
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
            download.setBackgroundResource(R.drawable.playm);
            boxState[pos] = 1;

        }


    }

}




