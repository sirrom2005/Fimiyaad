package com.fimiyaad.mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fimiyaad.mobile.Model.EventModel;
import com.fimiyaad.mobile.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by rohan
 * on 9/28/17.
 */

public class EventAdapter {/*extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<EventModel> data;
    private Context mContext;
    private Activity mActivity;
    private int flag;

    public void setData(List<EventModel> repo) {
        this.data = repo;
    }

    public EventAdapter(Activity activity) {
        mActivity = activity;
        mContext = activity.getBaseContext();
        if(inflater == null){inflater = LayoutInflater.from(mContext);}
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder;
        view = inflater.inflate(R.layout.event_tpl, parent, false);

        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.vTitle.setText(data.get(position).getTitle());
        holder.vDate.setText(Common.getFormattedDate(data.get(position).getEventDate()));

        switch(data.get(position).getCountry()){
            case "jamaica":
                flag = R.drawable.ic_ja;
            break;
            case "usa":
                flag = R.drawable.ic_usa;
            break;
            case "england":
                flag = R.drawable.ic_uk;
            break;
        }

        holder.vFlag.setImageDrawable(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
                                        mContext.getResources().getDrawable(flag, mContext.getApplicationContext().getTheme()) :
                                        mContext.getResources().getDrawable(flag));

        Glide.with(mContext)
                .load(mContext.getResources().getString(R.string.event_banner_url, data.get(position).getImageName()))
                .into(new SimpleTarget<Drawable>()
        {
            @Override
            public void onResourceReady(Drawable d, Transition<? super Drawable> transition) {
                final Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                Bitmap crop = Bitmap.createBitmap(bitmap,0,0,
                                                  bitmap.getWidth(),
                                                  (bitmap.getHeight()>=600)? 600 : bitmap.getHeight());
                holder.vImage.setImageBitmap(crop);

                holder.vShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        class CopyCache extends AsyncTask<Object, Object, Uri> {
                            @Override
                            protected Uri doInBackground(Object... params) {
                                try {
                                    File cachePath = new File(mContext.getExternalCacheDir(), "images");
                                    boolean i = cachePath.mkdirs(); // don't forget to make the directory
                                    FileOutputStream stream = new FileOutputStream(cachePath + "/image.jpg"); //overwrites this image every time
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    stream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                File imagePath  = new File(mContext.getExternalCacheDir(), "images");
                                File newFile    = new File(imagePath, "image.jpg");
                                return FileProvider.getUriForFile(mContext, mContext.getResources().getString(R.string.file_provider), newFile);
                            }

                            @Override
                            protected void onPostExecute(Uri uri) {
                                super.onPostExecute(uri);
                                String url = getShortUrl(mContext.getString(R.string.event_url,data.get(position).getImageName()));
                                ShareIntent( mContext,
                                        String.format( "%s%s",
                                                mContext.getString(R.string.share_event),
                                                url
                                        ),
                                        uri);
                            }
                        }
                        new CopyCache().execute();
                    }
                });
            }
        });

        holder.vImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, holder.vImage, "photo_transition");
                Intent intent = new Intent(mContext, EventPhotoActivity.class);
                intent.putExtra("POSTER_URL", mContext.getResources().getString(R.string.event_banner_url, data.get(position).getImageName()));
                mActivity.startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView vTitle, vDate;
        ImageView vFlag, vImage;
        Button vShare;

        private ViewHolder(View v) {
            super(v);
            vTitle = v.findViewById(R.id.title);
            vDate  = v.findViewById(R.id.date);
            vFlag  = v.findViewById(R.id.country_flag);
            vImage = v.findViewById(R.id.banner);
            vShare = v.findViewById(R.id.share);
        }
    }*/
}
