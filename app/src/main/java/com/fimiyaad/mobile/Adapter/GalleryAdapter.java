package com.fimiyaad.mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fimiyaad.mobile.AlbumActivity;
import com.fimiyaad.mobile.Helper.Common;
import com.fimiyaad.mobile.Model.GalleryModel;
import com.fimiyaad.mobile.R;

import java.util.List;

/**
 * Created by rohan
 * date 9/4/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<GalleryModel> data;
    private Context mContext;
    private Activity mActivity;
    private String img;

    public GalleryAdapter(Activity activity, List<GalleryModel> repo) {
        mActivity = activity;
        mContext = activity.getBaseContext();
        if(inflater == null){inflater = LayoutInflater.from(mContext);}
        this.data = repo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder;
        view = inflater.inflate(R.layout.layout_gallery, parent, false);
        //view.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, mContext.getResources().getDisplayMetrics());

        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        img = String.format("http://www.fimiyaad.com/%s", data.get(position).getImage()).replace(" ", "%20");
        holder.vTitle.setText(data.get(position).getTitle());
        Glide.with(mContext).load(img).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable img, Transition<? super Drawable> transition) {
                holder.vImage.setImageDrawable(img);
            }
        });

        holder.vView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, holder.vImage, "photo_transition");
                Intent intent = new Intent(mContext, AlbumActivity.class);
                intent.putExtra("GALLERY_KEY", data.get(position).getId());
                intent.putExtra("GALLERY_URL", data.get(position).getUrl());
                intent.putExtra("GALLERY_TITLE", data.get(position).getTitle());
                intent.putExtra("GALLERY_PIC", ((BitmapDrawable)  holder.vImage.getDrawable()).getBitmap());

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

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView vImage;
        private TextView vTitle;
        private RelativeLayout vView;

        private ViewHolder(View v){
            super(v);
            vImage = v.findViewById(R.id.image);
            vTitle = v.findViewById(R.id.title);
            vView =  v.findViewById(R.id.view);
        }
    }
}
