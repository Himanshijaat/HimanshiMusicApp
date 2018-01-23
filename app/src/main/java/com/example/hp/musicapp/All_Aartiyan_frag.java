package com.example.hp.musicapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.List;


/**
 * Created by hp on 12/20/2017.
 */

public class All_Aartiyan_frag extends Fragment implements View.OnClickListener {
    ListView listView;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    int id = 1;
    AllAartiyan_Adapter adapter;
    private Main_Bean myBean;
    ArrayList<Main_Bean> arraylist;

    DatabaseHelperForSongList db;
    private ProgressDialog progress;
    android.support.v7.widget.Toolbar toolbar;
    Button btn_refresh;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_aartiyan_frag, container, false);
        arraylist = new ArrayList<>();
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        btn_refresh = (Button) toolbar.findViewById(R.id.btn_refresh);
        btn_refresh.setVisibility(View.VISIBLE);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetSongList().execute();

            }
        });
        adapter = new AllAartiyan_Adapter(getActivity(), arraylist);
        listView = (ListView) view.findViewById(R.id.all_aartiyan_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "postion" + i, Toast.LENGTH_SHORT).show();
            }
        });

        db = new DatabaseHelperForSongList(getActivity());

        if (isNetworkAvaliable(getContext())) {
            //if internet is available
//            GetAllData();
//            if (arraylist.isEmpty()){
//                new GetSongList().execute();
//            }else {
//              //  Toast.makeText(getActivity(), "please refresh it", Toast.LENGTH_SHORT).show();
            new GetSongList().execute();


        } else {
            //if internet is not available
            Toast.makeText(getActivity(), "Internet not found.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "total row= " + db.getSongsCount(), Toast.LENGTH_SHORT).show();

            //Reading values from database
            GetAllData();

        }

        return view;
    }

    public void download(View view) {
        progress = new ProgressDialog(getActivity());

        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Please Wait");

        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    private void GetAllData() {
        ArrayList<Main_Bean> songDBList = db.getAllSongList();
        for (int j = 0; j < songDBList.size(); j++) {
            String trackId = songDBList.get(j).getTrackId();
            String trackTitle = songDBList.get(j).getName();
            String genre = songDBList.get(j).getGenre();
            Log.d(TAG, trackTitle);
            myBean = new Main_Bean();
            myBean.setTrackId(trackId);
            myBean.setName(trackTitle);
            myBean.setGenre(genre);
            arraylist.add(myBean);
        }
        adapter = new AllAartiyan_Adapter(getActivity(), arraylist);
        listView.setAdapter(adapter);
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {

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

                    JSONArray jsonArray = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject c = jsonArray.getJSONObject(j);
                        String trackTitle = c.getString("trackTitle");
                        String trackId = c.getString("trackId");
                        String genre = c.getString("genre");
                        String deity = c.getString("deity");
                        String imagename = c.getString("imagesnames");


                        Log.d(TAG, trackTitle);

                        myBean = new Main_Bean();
                        myBean.setName(trackTitle);
                        myBean.setTrackId(trackId);
                        myBean.setDeity(deity);
                        myBean.setGenre(genre);
                        myBean.setImageName(imagename);
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            adapter = new AllAartiyan_Adapter(getActivity(), arraylist);
            ;

            listView.setAdapter(adapter);

            db.addSong(arraylist);

        }
    }
}
