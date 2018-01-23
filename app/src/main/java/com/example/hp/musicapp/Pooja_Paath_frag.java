package com.example.hp.musicapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by hp on 12/20/2017.
 */

public class Pooja_Paath_frag extends Fragment implements TextToSpeech.OnInitListener {
    private ListView lv;
    ArrayAdapter<String> adapter;
    TextToSpeech textToSpeech;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pooja_paath_frag, container, false);
        textToSpeech = new TextToSpeech(getActivity(), this);
        // Listview Data
        final String products[] = {"गणपति पूजा", "महा लक्ष्मी पूजा", "सरस्वती पूजा",
                "दुर्गा पूजा",
                "नव ग्रह पूजा",
                "नवरात्रे पूजा"};

        lv = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.pooja_paath_item, R.id.poojaname, products);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                int i = 0;
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(new Locale("hi"));
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        String text = products[pos].toString();
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
                Pdfview_Fragment fragment = new Pdfview_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(pos));
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).addToBackStack(null).commit();
            }
        });


        return view;
    }

    @Override
    public void onInit(int i) {


    }


}



