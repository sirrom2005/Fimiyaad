package com.fimiyaad.mobile.Helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ImageAnimation implements Runnable {
    private final Object lock = new Object();
    private final Drawable[] mLayers = new Drawable[2];
    private Activity mActivity;
    private ImageView view;
    private List<Bitmap> mImage;
    private int mCurrent = 0;
    private int mNext = mCurrent + 1;
    private volatile boolean Running = false;
    private volatile boolean setPause = false;

    public ImageAnimation(Activity activity, ImageView view, List<Bitmap> image) {
        Log.e("TEST", "ANIMATION");
        mActivity  = activity;
        this.view  = view;
        this.mImage = image;
        setIsRunning(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    private void setIsRunning(boolean t){
        Running = t;
    }

    @Override
    public void run() {
        if(mImage.size()==0){
            setIsRunning(false);
            return;
        }

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setImageBitmap(mImage.get(0));
            }
        });

        animate();
    }

    private void animate() {
        if(mImage.size()>1) {
            synchronized (lock) {
                while (Running) {
                    if(setPause) {
                        try {
                            Log.e("PPPP", "PAUSE");
                            lock.wait();
                            setPause = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    pause((long) (3000 + Math.random() * 500));
                    mLayers[0] = new BitmapDrawable(mActivity.getResources(), mImage.get(mCurrent++));
                    mLayers[1] = new BitmapDrawable(mActivity.getResources(), mImage.get(mNext++));

                    final TransitionDrawable transitionDrawable = new TransitionDrawable(mLayers);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageDrawable(transitionDrawable);
                            transitionDrawable.startTransition(1000);
                        }
                    });

                    if (mNext == mImage.size()) {
                        mNext = 0;
                    }
                    if (mCurrent == mImage.size()) {
                        mCurrent = 0;
                    }
                }
            }
        }
    }

    private void pause(long time){
        try {
            System.out.println(time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        setPause = true;
    }

    public void play() {
        if (setPause) {
            synchronized (lock) {
                lock.notify();
            }
        }
    }
}
