package com.fimiyaad.mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//import com.bumptech.glide.Glide;
import com.fimiyaad.mobile.Model.VideoModel;
import com.fimiyaad.mobile.R;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.List;

/**
 * Created by rohan on 9/4/17.
 */

public class VideoAdapter{  /*extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private String YOUTUBE_DEV_KEK = "AIzaSyDEOY5SMZIQBnEjDSK9WV9tz7-uzCNdbaQ";
    private LayoutInflater inflater;
    private List<VideoModel> data;
    private Context mContext;
    private Activity mActivity;

    public void setData(List<VideoModel> repo) {
        this.data = repo;
    }

    public VideoAdapter(Activity activity) {
        mActivity = activity;
        mContext = activity.getBaseContext();
        if(inflater == null){inflater = LayoutInflater.from(mContext);}
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder;
        view = inflater.inflate(R.layout.video_tpl, parent, false);

        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String videoId = data.get(position).getVideoId();
        holder.vTitle.setText(data.get(position).getTitle());

        final String img = "https://i.ytimg.com/vi/" + videoId + "/mqdefault.jpg";
        Glide.with(mContext).load(img).into(holder.vThumb);

        holder.vThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideoActivity(videoId);
            }
        });
    }

    private void startVideoActivity(String videoId){
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(mActivity,
                YOUTUBE_DEV_KEK, videoId, 0, true, true);
        if(canResolveIntent(intent)){
            mActivity.startActivityForResult(intent, 1);
        }else{
            // Could not resolve the intent - must need to install or update the YouTube API service.
            YouTubeInitializationResult.SERVICE_MISSING.getErrorDialog(mActivity, 1).show();
        }
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = mActivity.getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vTitle;
        ImageView vThumb;

        private ViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.video_title);
            vThumb = (ImageView) v.findViewById(R.id.video_thumb);
            //vShare = (ImageView) v.findViewById(R.id.movie_share);
        }
    }
    */
}
