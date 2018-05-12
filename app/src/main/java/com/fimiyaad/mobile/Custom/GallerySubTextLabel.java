package com.fimiyaad.mobile.Custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by rohan
 * on 9/29/17.
 */

public class GallerySubTextLabel extends android.support.v7.widget.AppCompatTextView {
    public GallerySubTextLabel(Context context) {
        super(context);
        Setup();
    }

    public GallerySubTextLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        Setup();
    }

    public GallerySubTextLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Setup();
    }

    private void Setup() {
        Typeface face = Typeface.createFromAsset(getResources().getAssets(),"fonts/Roboto-Medium.ttf");
        this.setTypeface(face);
    }
}
