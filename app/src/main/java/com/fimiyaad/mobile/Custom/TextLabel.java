package com.fimiyaad.mobile.Custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by rohan
 * on 9/29/17.
 */

public class TextLabel extends android.support.v7.widget.AppCompatTextView {
    public TextLabel(Context context) {
        super(context);
        Setup();
    }

    public TextLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        Setup();
    }

    public TextLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Setup();
    }

    private void Setup() {
        Typeface face = Typeface.createFromAsset(getResources().getAssets(),"fonts/NotoSerif-Bold.ttf");
        this.setTypeface(face);
    }

    public class Test extends TextLabel {

        public Test(Context context) {
            super(context);
            this.setTextColor(Color.RED);
        }

        public Test(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public Test(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
    }
}
