package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;


public class dragThali extends Fragment {
    private ImageView img,imgShankh, imgBell, roliImage, imgThali ;
    private ViewGroup rootLayout;
    private GifImageView gifBell;

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
        imgBell=rootLayout.findViewById(R.id.imageGhanti);
        gifBell=rootLayout.findViewById(R.id.gifGhanti);
        roliImage=rootLayout.findViewById(R.id.roli_image);
        imgThali=rootLayout.findViewById(R.id.imageArtiMove);
        gifBell.setVisibility(View.GONE);
        imgThali.setVisibility(View.GONE);

        //img.setOnTouchListener(new ChoiceTouchListener());
        imgBell.setOnClickListener(new bellClick());
        imgShankh.setOnClickListener(new shankhClick());
        roliImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootLayout.setBackgroundResource(R.drawable.ganeshji_withtika);
            }
        });

        img.setOnClickListener(new MoveThali());


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

    private class bellClick implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            imgBell.setVisibility(View.GONE);
            gifBell.setVisibility(View.VISIBLE);
            Animation anim = new CircleAnimation(img, 150);
            anim.setDuration(5000);
            gifBell.startAnimation(anim);

            MediaPlayer mp= MediaPlayer.create(getActivity(),R.raw.temple_bell);
            mp.start();

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.clearAnimation();
                    imgBell.setVisibility(View.VISIBLE);
                    gifBell.setVisibility(View.GONE);
                }
            },5000);


        }

    }

    private class MoveThali implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            img.setVisibility(View.GONE);
            imgThali.setVisibility(View.VISIBLE);
            Animation anim = new CircleAnimation(img, 150);
            anim.setDuration(5000);
            imgThali.startAnimation(anim);

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.clearAnimation();
                    img.setVisibility(View.VISIBLE);
                    imgThali.setVisibility(View.GONE);
                }
            },5000);



        }
    }
}
