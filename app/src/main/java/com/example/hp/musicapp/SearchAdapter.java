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
 * Created by Kshitij on 11/30/2017.
 */
public class SearchAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    public static final String TAG=SearchAdapter.class.getSimpleName();

    private ArrayList<Main_Bean> arraylist; // Original Values
      // Values to be displayed

    int pos;
    private NotificationManager mNotifyManager;
    NotificationCompat.Builder build;
    int id = 1;

    public SearchAdapter(Context context, ArrayList<Main_Bean> data) {
        mContext = context;
        //this.data = data;
        this.arraylist = new ArrayList<>(data);

        inflater = LayoutInflater.from(mContext);

        //this.arraylist.addAll(data);
    }

    public class ViewHolder {
        TextView name;
        Button download;
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
        final ViewHolder holder;
        int type = getItemViewType(position);
        System.out.println("getView " + position + " " + view + " type = " + type);

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.aartiyan_itemlayout, null);
            holder.name = (TextView) view.findViewById(R.id.textview_aartiyan);
            holder.download=(Button)view.findViewById(R.id.download_btn);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(arraylist.get(position).getName());
holder.download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(mContext, "postion"+ pos, Toast.LENGTH_SHORT).show();
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        build = new NotificationCompat.Builder(mContext);
        build.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
        new download().execute();
        holder.download.setBackgroundResource(R.drawable.playm);

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




        }
    }

    }



