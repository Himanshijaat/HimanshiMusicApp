package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class dragThali extends Fragment {
    private ImageView img,imgShankh;
    private ViewGroup rootLayout;

    Rect parentRect;
    // original down point
    final PointF offsetPoint = new PointF();

    private MediaPlayer mp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_drag_thali,container,false);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // Display Rect Boundaries
        //parentRect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        rootLayout = (ViewGroup) view.findViewById(R.id.view_root);
        img = (ImageView) rootLayout.findViewById(R.id.imageArti);
        imgShankh= (ImageView) rootLayout.findViewById(R.id.imageShankh);

        img.setOnTouchListener(new ChoiceTouchListener());
        imgShankh.setOnClickListener(new shankhClick());

        return view;
    }
    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int action = motionEvent.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    offsetPoint.x = motionEvent.getX();
                    offsetPoint.y = motionEvent.getY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();

                    img.offsetLeftAndRight((int) (x - offsetPoint.x));
                    img.offsetTopAndBottom((int) (y - offsetPoint.y));

                    // check boundaries
                    if (img.getRight() > rootLayout.getRight()) {
                        img.offsetLeftAndRight(-(img.getRight() - rootLayout.getRight()));
                    } else if (img.getLeft() < rootLayout.getLeft()) {
                        img.offsetLeftAndRight((rootLayout.getLeft() - img.getLeft()));
                    }

                    if (img.getBottom() > rootLayout.getBottom()) {
                        img.offsetTopAndBottom(-(img.getBottom() - rootLayout.getBottom()));
                    } else if (img.getTop() < rootLayout.getTop()) {
                        img.offsetTopAndBottom((rootLayout.getTop() - img.getTop()));
                    }

                    break;
            }
            return true;
        }
    }

    private class shankhClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            mp= MediaPlayer.create(getActivity(),R.raw.shankh_sound);
            mp.start();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mp!=null){
            mp.stop();
        }
    }
}
