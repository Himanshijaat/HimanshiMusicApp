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

/**
 * Created by HimanshiJaat on 07/01/2018.
 */
public class AllAartiyan_Adapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<SongBean> arraylist;
    private String TAG = MainActivity.class.getSimpleName();
    int pos;
    ViewHolder holder;
    private NotificationManager mNotifyManager;
    NotificationCompat.Builder build;
    int id = 1;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AllAartiyan_Adapter(Context context, ArrayList<SongBean> data) {
        mContext = context;
        this.arraylist = new ArrayList<>(data);
        inflater = LayoutInflater.from(mContext);

    }

    public class ViewHolder {
        TextView name;
        Button download;
        RelativeLayout relativeLayout;
        ProgressBar progressBar;

    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public SongBean getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.aartiyan_itemlayout, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.textview_aartiyan);
            holder.download = (Button) view.findViewById(R.id.download_btn);
            holder.relativeLayout=(RelativeLayout)view.findViewById(R.id.relativelayout);
            holder.progressBar=(ProgressBar)view.findViewById(R.id.progressbar_main);
//            holder.progressBar1 = (ProgressBar) view.findViewById(R.id.progressbar_main);
//            holder.progressBar1.setProgress(100);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(arraylist.get(position).getSongname());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                pos = position;
                Toast.makeText(mContext, "postion " + arraylist.get(position).getTrackId(), Toast.LENGTH_SHORT).show();
                holder.download.setVisibility(View.INVISIBLE);
                mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                build = new NotificationCompat.Builder(mContext);
                build.setContentTitle("Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.ic_launcher);
                new download().execute();
            }
        });
        return view;
    }


    class download extends AsyncTask<Void, Integer, Void> {
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
                    File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/" + name);
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
            holder.download.setBackgroundResource(R.drawable.playm);
            //holder.relativeLayout.setBackgroundColor(R.color.colorPrimaryDark);
        }
    }
}




