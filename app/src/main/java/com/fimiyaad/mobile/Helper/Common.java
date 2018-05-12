package com.fimiyaad.mobile.Helper;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.urlshortener.Urlshortener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;
import com.fimiyaad.mobile.R;
import com.google.api.services.urlshortener.model.Url;

import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Rohan.
 * on 9/5/17.
 */

public class Common {
    public static final String TAG = "FIMIYAAD_LOG";
    public static final String SLIDE_SHOW = "com.fimiyaad.mobile.slide_show";
    public static final String GALLERY_DATA = "com.fimiyaad.mobile.gallery_data";
    public static final String API_BASE_URL = "http://www.fimiyaad.com/";
    public static final String GALLERY_YEAR_TAG = "GALLERY_YEAR";


    public static int GetDeviceDensity(Activity activity){
        return activity.getResources().getDisplayMetrics().densityDpi;
    }

    public static String getFormattedDate(String date){
        Date d=null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US).format(d);
    }

    @SuppressLint("StaticFieldLeak")
    public static String getShortUrl(final String longUrl){

        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Urlshortener.Builder builder = new Urlshortener.Builder(AndroidHttp.newCompatibleTransport(),
                        AndroidJsonFactory.getDefaultInstance(), null);
                Urlshortener urlshortener = builder.build();

                Url url = new Url();
                url.setLongUrl(longUrl);
                try {
                    Urlshortener.Url.Insert insert = urlshortener.url().insert(url);
                    insert.setKey("AIzaSyAekWdrXbQv3S2ecvBf8dYtbntbZ2p-Ohg");
                    url = insert.execute();
                    return url.getId();
                } catch (IOException e) {
                    return longUrl;
                }
            }
        };

        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void ShareIntent(Context c, String bodyText, Uri uriToImage) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.putExtra(Intent.EXTRA_TEXT, bodyText);
        shareIntent.setType("image/jpeg");
        c.startActivity(Intent.createChooser(shareIntent, c.getResources().getText(R.string.send_to)));
    }

    @SuppressLint("StaticFieldLeak")
    public static void SavePhoto(final Context c, final Drawable photo){
        new AsyncTask<Void,Void,String>()  {
            @Override
            protected String doInBackground(Void[] params) {
                if(photo!=null) {
                    Bitmap bitmap = ((BitmapDrawable) photo).getBitmap();
                    Date d = new Date();
                    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyMMddHHmms", Locale.getDefault());

                    return MediaStore.Images.Media.insertImage(c.getContentResolver(),
                            bitmap,
                            String.format("%s.jpg", df.format(d)),
                            c.getString(R.string.app_name));
                }
                return null;
            }

            @Override
            protected void onPostExecute(String o) {
                super.onPostExecute(o);
                if(o!=null) {
                    Toast.makeText(c, c.getString(R.string.download_photo), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public static void about(Activity c) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(c);
        final AlertDialog alert = dialog.create();
        View v = View.inflate(c, R.layout.about_layout, null);

        alert.setIcon(  Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
                c.getResources().getDrawable(R.mipmap.ic_launcher, c.getApplicationContext().getTheme()) :
                c.getResources().getDrawable(R.mipmap.ic_launcher));

        Button cancel   = v.findViewById(R.id.cancel);

        Drawable d = new ColorDrawable(Color.TRANSPARENT);
        alert.setView(v);
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(d);
        alert.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public static boolean isInternetAvailable() {
        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    InetAddress ipAddr = InetAddress.getByName("www.fimiyaad.com");
                    return !ipAddr.equals("");
                } catch (Exception e) {
                    return false;
                }
            }
        };

        try {
            return asyncTask.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }
}