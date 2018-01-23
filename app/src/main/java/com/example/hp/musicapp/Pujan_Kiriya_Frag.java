package com.example.hp.musicapp;

import android.app.Fragment;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import pl.droidsonroids.gif.GifImageView;


public class Pujan_Kiriya_Frag extends Fragment {
    private ImageView thaliimage, sankimage, bell_image, roliImage, thaliimage_move;
   RelativeLayout rootLayout;
    GifImageView bell_gif;
    final PointF offsetPoint = new PointF();
    private MediaPlayer mp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pujan_kiriya_frag, container, false);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        rootLayout = (RelativeLayout)view.findViewById(R.id.pujan_view);
        thaliimage = (ImageView) view.findViewById(R.id.pujan_thaliimage);
        sankimage = (ImageView) view.findViewById(R.id.pujan_sankimage);
        bell_image = view.findViewById(R.id.pujan_bellimage);
        bell_gif = view.findViewById(R.id.pujan_bellgif);
        roliImage = view.findViewById(R.id.pujan_roliimage);
        thaliimage_move = view.findViewById(R.id.pujan_thaliimage_move);
        bell_gif.setVisibility(View.GONE);
        thaliimage_move.setVisibility(View.GONE);

        bell_image.setOnClickListener(new bellClick());
        sankimage.setOnClickListener(new shankhClick());
        roliImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootLayout.setBackgroundResource(R.drawable.ganeshji_withtika);
            }
        });

        thaliimage.setOnClickListener(new MoveThali());
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

                    thaliimage.offsetLeftAndRight((int) (x - offsetPoint.x));
                    thaliimage.offsetTopAndBottom((int) (y - offsetPoint.y));

                    // check boundaries
                    if (thaliimage.getRight() > rootLayout.getRight()) {
                        thaliimage.offsetLeftAndRight(-(thaliimage.getRight() - rootLayout.getRight()));
                    } else if (thaliimage.getLeft() < rootLayout.getLeft()) {
                        thaliimage.offsetLeftAndRight((rootLayout.getLeft() - thaliimage.getLeft()));
                    }

                    if (thaliimage.getBottom() > rootLayout.getBottom()) {
                        thaliimage.offsetTopAndBottom(-(thaliimage.getBottom() - rootLayout.getBottom()));
                    } else if (thaliimage.getTop() < rootLayout.getTop()) {
                        thaliimage.offsetTopAndBottom((rootLayout.getTop() - thaliimage.getTop()));
                    }

                    break;
            }
            return true;
        }
    }

    private class shankhClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            mp = MediaPlayer.create(getActivity(), R.raw.shankh_sound);
            mp.start();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mp != null) {
            mp.stop();
        }
    }

    private class bellClick implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            bell_image.setVisibility(View.GONE);
            bell_gif.setVisibility(View.VISIBLE);
            Animation anim = new CircleAnimation(thaliimage, 150);
            anim.setDuration(5000);
            bell_gif.startAnimation(anim);

            MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.temple_bell);
            mp.start();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.clearAnimation();
                    bell_image.setVisibility(View.VISIBLE);
                    bell_gif.setVisibility(View.GONE);
                }
            }, 5000);


        }

    }

    private class MoveThali implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            thaliimage.setVisibility(View.GONE);
            thaliimage_move.setVisibility(View.VISIBLE);
            Animation anim = new CircleAnimation(thaliimage, 150);
            anim.setDuration(5000);
            thaliimage_move.startAnimation(anim);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.clearAnimation();
                    thaliimage.setVisibility(View.VISIBLE);
                    thaliimage_move.setVisibility(View.GONE);
                }
            }, 5000);


        }
    }
}
