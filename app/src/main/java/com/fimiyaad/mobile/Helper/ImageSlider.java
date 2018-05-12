package com.fimiyaad.mobile.Helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by rohan on 2/7/2017.
 */

public class ImageSlider {
    private static final int RightToLeft = 1;
    private static final int LeftToRight = 2;
    private static final int DURATION = 5000;

    private final Matrix mMatrix = new Matrix();
    private ImageView mImageView;
    private float mScaleFactor;
    private int mDirection = RightToLeft;
    private RectF mDisplayRect = new RectF();
    private int  loop,
                 step = 0;

    /**
     * @param img - ImageView
     * @param loop - loop count 0 for infinity
     */
    ImageSlider(ImageView img, int loop) {
        this.mImageView = img;
        this.loop = loop;
        init();
    }

    private void init() {
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mScaleFactor = (float)  mImageView.getHeight() / (float) mImageView.getDrawable().getIntrinsicHeight();
                mMatrix.postScale(mScaleFactor, mScaleFactor);
                mImageView.setImageMatrix(mMatrix);

                animate();
            }
        });
    }

    private void animate() {
        updateDisplayRect();
        if(mDirection == RightToLeft) {
            animate(mDisplayRect.left, mDisplayRect.left - (mDisplayRect.right - mImageView.getWidth()));
        } else {
            animate(mDisplayRect.left, 0.0f);
        }
        step+=1;
    }

    private void animate(float from, float to) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();

                mMatrix.reset();
                mMatrix.postScale(mScaleFactor, mScaleFactor);
                mMatrix.postTranslate(value, 0);

                mImageView.setImageMatrix(mMatrix);

            }
        });
        valueAnimator.setDuration(DURATION);
        valueAnimator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                if(mDirection == RightToLeft)
                    mDirection = LeftToRight;
                else
                    mDirection = RightToLeft;

                Log.e(">>", step + " == " + loop);
                if(loop==0 || step < loop){
                    animate();
                }
            }
        });
        valueAnimator.start();
    }

    private void updateDisplayRect() {
        mDisplayRect.set(0, 0, mImageView.getDrawable().getIntrinsicWidth(), mImageView.getDrawable().getIntrinsicHeight());
        mMatrix.mapRect(mDisplayRect);
    }
}

