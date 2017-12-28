package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by hp on 12/23/2017.
 */

public class Pdfview_Fragment extends Fragment implements TextToSpeech.OnInitListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    PDFView pdfView;
    private static final String FILENAME = "sample.pdf";
Button speck;
TextToSpeech textToSpeech;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pdfview,container,false);
        pdfView= (PDFView)view.findViewById(R.id.pdfView);
speck=(Button)view.findViewById(R.id.speck);
        textToSpeech=new TextToSpeech(getActivity(),this);
        pdfView.fromAsset(FILENAME).load();
        speck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status=0;
                if (status == TextToSpeech.SUCCESS) {

                    int result = textToSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        speck.setEnabled(true);
                        try {
                            speakOut();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {



    }

    private void speakOut() throws FileNotFoundException {

AssetManager am=getActivity().getAssets();
        InputStream inputStream ;
        try {
            inputStream = am.open("sample.pdf");
            String inputStreamToString = inputStream.toString();
           // byte[] byteArray = inputStreamToString.getBytes();
       //     String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
           // String text = "this is a pooja app and this is a pooja paath module";
            textToSpeech.speak(inputStreamToString, TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getActivity(), ""  +inputStreamToString, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

           }


}
