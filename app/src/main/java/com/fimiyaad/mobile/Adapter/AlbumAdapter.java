package com.fimiyaad.mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.bumptech.glide.Glide;
import com.fimiyaad.mobile.Helper.Common;
import com.fimiyaad.mobile.LargePhotoActivity;
import com.fimiyaad.mobile.Model.GalleryModel;
import com.fimiyaad.mobile.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by
 * Date rohan on 9/4/17...
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<GalleryModel.Album> data;
    private Context mContext;
    private Activity mActivity;
    private String galleryUrl;
    private String img;

    public void setData(List<GalleryModel.Album> repo) {
        this.data = repo;
    }

    public AlbumAdapter(Activity activity, String Url) {
        mActivity = activity;
        mContext = activity.getBaseContext();
        this.galleryUrl = Url;
        if(inflater == null){inflater = LayoutInflater.from(mContext);}
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder;
        view = inflater.inflate(R.layout.image, parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        img = galleryUrl + data.get(position).getImage();
        Glide.with(mContext).load(img).into(new SimpleTarget<Drawable>()
        {
            @Override
            public void onResourceReady(Drawable img, Transition<? super Drawable> transition) {
                Bitmap bitmap = ((BitmapDrawable)img).getBitmap();
                int pad = 15;
                Bitmap crop = Bitmap.createBitmap(bitmap,pad,pad,bitmap.getWidth()-pad*2,bitmap.getHeight()-pad*2);
                holder.vImage.setImageBitmap(crop);
            }
        });

        holder.vImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, holder.vImage, "photo_transition");
                Intent intent = new Intent(mContext, LargePhotoActivity.class);
                intent.putExtra("GALLERY_KEY", position);
                intent.putExtra("GALLERY_URL", galleryUrl);
                intent.putExtra("GALLERY_DATA", (Serializable) data);

                if(Common.isInternetAvailable()){
                    mActivity.startActivity(intent);
                }else{
                    Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView vImage;

        private ViewHolder(View v){
            super(v);
            vImage = v.findViewById(R.id.thumb);
        }
    }
}
