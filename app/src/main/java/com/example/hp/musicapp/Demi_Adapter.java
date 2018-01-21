package com.example.hp.musicapp;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hp on 1/18/2018.
 */

public class Demi_Adapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Main_Bean> arraylist;
    private String TAG = MainActivity.class.getSimpleName();
    LayoutInflater inflter;
    private NotificationManager mNotifyManager;
    NotificationCompat.Builder build;
    int id = 1;
    int pos;

    public Demi_Adapter(Context mContext, ArrayList<Main_Bean> arraylist) {
        this.mContext = mContext;
        this.arraylist=new ArrayList<>(arraylist);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View  view, ViewGroup viewGroup) {
            LayoutInflater lInflater = (LayoutInflater)mContext.getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.aartiyan_itemlayout, null);//set layout for displaying items
        TextView name = (TextView) view.findViewById(R.id.textview_aartiyan);
        Button download = (Button) view.findViewById(R.id.download_btn);
   final   Button   play=(Button)view.findViewById(R.id.play_btn);
        name.setText(arraylist.get(position).getName());
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                build = new NotificationCompat.Builder(mContext);
                build.setContentTitle("Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.ic_launcher);
                play.setVisibility(View.VISIBLE);
               // new Download().execute();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "pos"+ position, Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
    class Download extends AsyncTask<Void, Integer, Void> {


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

            if (jsonStr != null) {

                try {
                    JSONObject object = new JSONObject(jsonStr);
                    String name = object.getString("fileName");
                    String data = object.getString("data");
                    Log.d(TAG, name);
                    Log.d(TAG, data);
                    File file = new File(Environment.getExternalStorageDirectory() + "/BhaktiMusic/" + name);
                    FileOutputStream os = new FileOutputStream(file, true);
                    os.write(Base64.decode(data, Base64.NO_WRAP));
                    os.flush();
                    os.close();
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

         //   download.setBackgroundResource(R.drawable.playm);
            //holder.relativeLayout.setBackgroundColor(R.color.colorPrimaryDark);

           // play.setVisibility(View.VISIBLE);
        }
    }
}
