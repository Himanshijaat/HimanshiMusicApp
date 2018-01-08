package com.example.hp.musicapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;



/**
 * Created by hp on 12/20/2017.
 */

public class All_Aartiyan_frag extends Fragment {
     ListView listView;
     private String TAG = MainActivity.class.getSimpleName();
     private ProgressDialog pDialog;
     int id = 1;
     AllAartiyan_Adapter adapter;
     private Main_Bean myBean;
     ArrayList<Main_Bean> arraylist;

     @RequiresApi(api = Build.VERSION_CODES.M)
     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_aartiyan_frag,container,false);
        arraylist=new ArrayList<>();

        adapter=new AllAartiyan_Adapter(getActivity(),arraylist);
        listView =(ListView)view.findViewById(R.id.all_aartiyan_listview);

         //new GetSongList().execute();

         return view;
        }



 
 /**
 * Async task class to get json by making HTTP call
 */

 private class GetSongList extends AsyncTask<Void, Void, Void> {

     @SuppressLint("NewApi")
     @Override
     protected void onPreExecute() {
     super.onPreExecute();
     // Showing progress dialog
     pDialog = new ProgressDialog(getActivity());
     pDialog.setMessage("Please wait...");
     pDialog.setCancelable(false);
     pDialog.show();
     }

     @Override
     protected Void doInBackground(Void... arg0) {

     URL url = null;

     try {
     url = new URL("http://arbon.in/songsList.php");

     String data = URLEncoder.encode("user_name", "UTF-8")
     + "=" + URLEncoder.encode("apper", "UTF-8");
     data += "&" + URLEncoder.encode("password", "UTF-8")
     + "=" + URLEncoder.encode("P@ssw0rd", "UTF-8");

     HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
     httpURLConnection.setRequestMethod("POST");
     httpURLConnection.setDoOutput(true);
     httpURLConnection.setDoInput(true);

     Log.d("hiiiiiiiii", String.valueOf(url));

     OutputStream outputStream = httpURLConnection.getOutputStream();
     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
     bufferedWriter.write(data);
     bufferedWriter.flush();
     bufferedWriter.close();

     String jsonStr;
     BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
     StringBuilder sb = new StringBuilder();
     String line = null;
     while ((line = reader.readLine()) != null) {
     // Append server response in string
     sb.append(line + "\n");
     }

         jsonStr = sb.toString();
         Log.d(TAG, jsonStr);
         if (jsonStr != null) {

             JSONArray jsonArray=new JSONArray(jsonStr);
             // looping through All Contacts
                 for (int j = 0; j < jsonArray.length(); j++) {
                     JSONObject c = jsonArray.getJSONObject(j);
                     String trackTitle = c.getString("trackTitle");
                     String trackId=c.getString("trackId");
                     Log.d(TAG, trackTitle);

                     myBean = new Main_Bean();
                     myBean.setName(trackTitle);
                     myBean.setTrackId(trackId);
                     arraylist.add(myBean);

                 }
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
         }
         return null;
         }

         @Override
         protected void onPostExecute (Void result) {
         super.onPostExecute(result);
         // Dismiss the progress dialog
         if (pDialog.isShowing())
         pDialog.dismiss();
         adapter=new AllAartiyan_Adapter(getActivity(),arraylist);

         listView.setAdapter(adapter);

     }
 }
}
