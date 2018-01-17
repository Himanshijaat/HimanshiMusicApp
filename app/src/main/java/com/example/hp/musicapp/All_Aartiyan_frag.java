package com.example.hp.musicapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

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
    private SongBean myBean;
    ArrayList<SongBean> arraylist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_aartiyan_frag,container,false);
        arraylist=new ArrayList<>();
        adapter=new AllAartiyan_Adapter(getActivity(),arraylist);
        listView =(ListView)view.findViewById(R.id.all_aartiyan_listview);
        new GetContacts().execute();

        return view;
    }

    /**
     * Async task class to get json by making HTTP call
     */

    private class GetContacts extends AsyncTask<Void, Void, Void> {

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

            HttpHandler sh = new HttpHandler();
            String url = "http://arbon.in/songsList.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.d(TAG, jsonStr);
            if (jsonStr != null) {

                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject c = jsonArray.getJSONObject(j);
                        String trackTitle = c.getString("trackTitle");
                        String trackId = c.getString("trackId");
                        Log.d(TAG, trackTitle);
                        myBean = new SongBean();
                        myBean.setSongname(trackTitle);
                        myBean.setTrackId(trackId);
                        arraylist.add(myBean);

                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

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
