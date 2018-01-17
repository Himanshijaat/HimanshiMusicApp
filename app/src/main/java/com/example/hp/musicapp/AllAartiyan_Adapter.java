package com.example.hp.musicapp;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
<<<<<<< HEAD
=======
import android.graphics.drawable.Drawable;
>>>>>>> origin/master
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
<<<<<<< HEAD
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

=======
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Kshitij on 11/30/2017.
 */
public class AllAartiyan_Adapter extends BaseAdapter{
    private String TAG = MainActivity.class.getSimpleName();
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Main_Bean> arraylist;
    String songID;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder build;
    int id = 1;
    ViewHolder holder;
      // Values to be displayed
    public AllAartiyan_Adapter(Context context, ArrayList<Main_Bean> data) {
        mContext = context;
        //this.data = data;
        this.arraylist = new ArrayList<>(data);

        inflater = LayoutInflater.from(mContext);
        //this.arraylist.addAll(data);
>>>>>>> origin/master
    }

    public class ViewHolder {
        TextView name;
        Button download;
<<<<<<< HEAD
        RelativeLayout relativeLayout;
        ProgressBar progressBar;

=======
        ProgressBar roundProgressBar;
>>>>>>> origin/master
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
<<<<<<< HEAD
    public SongBean getItem(int position) {
=======
    public Main_Bean getItem(int position) {

>>>>>>> origin/master
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
<<<<<<< HEAD
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
=======

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.aartiyan_itemlayout,parent,false);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.textview_aartiyan);
            holder.download=(Button)view.findViewById(R.id.download_btn);
            holder.roundProgressBar=view.findViewById(R.id.circularProgressbar);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(arraylist.get(position).getName());
        //songID=arraylist.get(position).getTrackId();

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources res=mContext.getResources();
                Drawable drawable= res.getDrawable(R.drawable.progressbar_round);
                holder.roundProgressBar.setVisibility(View.VISIBLE);

                holder.download.setVisibility(View.INVISIBLE);
                holder.roundProgressBar.setProgress(0);   // Main Progress
                holder.roundProgressBar.setSecondaryProgress(100); // Secondary Progress
                holder.roundProgressBar.setMax(100); // Maximum Progress
                holder.roundProgressBar.setProgressDrawable(drawable);

                mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                build = new NotificationCompat.Builder(mContext.getApplicationContext());
                build.setContentTitle("Downloading")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.ic_launcher);
                songID=arraylist.get(position).getTrackId();
                Toast.makeText(mContext, "pos"+songID, Toast.LENGTH_SHORT).show();
                new DownloadSong().execute();
>>>>>>> origin/master
            }
        });
        return view;
    }
<<<<<<< HEAD


    class download extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           build.setProgress(100, 0, false);
            mNotifyManager.notify(id, build.build());
        }
=======
    private class DownloadSong extends AsyncTask<Void, Integer, Integer> {
        @SuppressLint("NewApi")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showDialog(progress_bar_type);

            // Displays the progress bar for the first time.
            build.setProgress(100, 0, false);
            mNotifyManager.notify(id, build.build());

        }

>>>>>>> origin/master
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onProgressUpdate(Integer... values) {
            build.setProgress(100, values[0], false);
            mNotifyManager.notify(id, build.build());
<<<<<<< HEAD
=======

            holder.roundProgressBar.setProgress(values[0]);

>>>>>>> origin/master
            super.onProgressUpdate(values);
        }

        @Override
<<<<<<< HEAD
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

=======
        protected Integer doInBackground(Void... arg0) {

            URL url = null;

            try {
                url = new URL("http://arbon.in/songsDownload.php");

                String data = URLEncoder.encode("user_name", "UTF-8")
                        + "=" + URLEncoder.encode("apper", "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode("P@ssw0rd", "UTF-8");
                data += "&" + URLEncoder.encode("song_id", "UTF-8")
                        + "=" + URLEncoder.encode(songID, "UTF-8");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                Log.d("hiiiiiiiii", String.valueOf(url));

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                String jsonStr=null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                Log.d("data", "sb data "+sb);

                int i;
                for (i = 0; i <= 100; i++) {
                    // Sets the progress indicator completion percentage
                    publishProgress(i);
                    // Sleep for 5 milliseconds
                    Thread.sleep(250);
                }

                jsonStr = sb.toString();
                if (jsonStr != null) {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String name = jsonObj.getString("fileName");
                    String data1 = jsonObj.getString("data");
                    String mime = jsonObj.getString("mime");

                    File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/" + name);
                    FileOutputStream os = new FileOutputStream(file, true);
                    os.write(Base64.decode(data1, Base64.NO_WRAP));

                    os.flush();
                    os.close();

                    Log.d(TAG, name);
                    Log.d(TAG, String.valueOf(file));
                    Log.d(TAG, mime);

                }else {
                    Log.d("msg","no data get");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (InterruptedException e){
                e.printStackTrace();
>>>>>>> origin/master
            }
            return null;
        }

<<<<<<< HEAD
        @SuppressLint("ResourceAsColor")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
=======
        @Override
        protected void onPostExecute (Integer result) {
            super.onPostExecute(result);

>>>>>>> origin/master
            build.setContentText("Download complete");
            // Removes the progress bar
            build.setProgress(0, 0, false);
            mNotifyManager.notify(id, build.build());
<<<<<<< HEAD
            holder.download.setBackgroundResource(R.drawable.playm);
            //holder.relativeLayout.setBackgroundColor(R.color.colorPrimaryDark);
        }
    }
}




=======

            holder.roundProgressBar.setProgress(100);
            holder.roundProgressBar.setVisibility(View.GONE);
            holder.download.setBackgroundResource(R.drawable.tick_ok);

        }
    }
}
>>>>>>> origin/master
