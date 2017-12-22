package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;

/**
 * Created by hp on 12/20/2017.
 */

public class Panchang_Fragment extends Fragment{
    private CalendarView mCalendarView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.panchang_frag,container,false);
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);

        return view;
    }
}
