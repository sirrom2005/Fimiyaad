package com.fimiyaad.mobile.Custom;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

public class ThumbImageView extends android.support.v7.widget.AppCompatImageView
{

    public ThumbImageView(Context context )
    {
        super( context );
    }

    public ThumbImageView(Context context, AttributeSet attrs )
    {
        super( context, attrs );
    }

    public ThumbImageView(Context context, AttributeSet attrs, int defStyle )
    {
        super( context, attrs, defStyle );
    }

    @Override
    protected void onDraw( @NonNull Canvas canvas )
    {
        Drawable drawable = getDrawable();

        if(drawable == null) { return; }

        if(getWidth() == 0 || getHeight() == 0) { return; }

        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth()/*, h = getHeight( )*/;
        //setScaleType(ScaleType.FIT_XY);
        //Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
        //canvas.drawBitmap(roundBitmap, 0, 0, null);
        //Bitmap img = getCroppedThumbBitmap(bitmap, 100);
        canvas.drawBitmap(bitmap, -15, -15, null);
    }

    private static Bitmap getCroppedThumbBitmap(@NonNull Bitmap bmp, int trim )
    {
        return null;
    }

    private static Bitmap getCroppedBitmap(@NonNull Bitmap bmp, int radius )
    {
        Bitmap bitmap;

        if(bmp.getWidth( ) != radius || bmp.getHeight( ) != radius)
        {
            float smallest = Math.min( bmp.getWidth( ), bmp.getHeight( ) );
            float factor = smallest / radius;
            bitmap = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() / factor), (int) (bmp.getHeight() / factor), false);
        }
        else
        {
            bitmap = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(radius/2 + 0.7f, radius / 2 + 0.7f, radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}