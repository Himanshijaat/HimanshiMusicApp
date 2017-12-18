package com.example.hp.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

/**
 * Created by hp on 12/18/2017.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        changeFrag(0);
    }
    public void changeFrag(int position) {
        switch (position) {
            case 0:
                HomeFragment frag0 = new HomeFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, frag0).commit();
                break;

        }
    }
}