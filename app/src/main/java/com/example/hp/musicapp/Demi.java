package com.example.hp.musicapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

/**
 * Created by hp on 12/29/2017.
 */

public class Demi extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    Button bDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demi);
        bDownload=findViewById(R.id.button_download);
        bDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new GetContacts().execute();

            }
        });
    }


    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                //pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showDialog(progress_bar_type);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            URL url = null;

            try {
                url = new URL("https://sharmajisantosh.000webhostapp.com/download1.php");

                String data = URLEncoder.encode("song_id", "UTF-8")
                        + "=" + URLEncoder.encode("1", "UTF-8");
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
                Log.d("data", "sb data"+sb);

                jsonStr = sb.toString();

                if (jsonStr != null) {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String name = jsonObj.getString("name");
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

        }

    }

}


