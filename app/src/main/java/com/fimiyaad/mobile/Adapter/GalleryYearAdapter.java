package com.fimiyaad.mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fimiyaad.mobile.GalleryActivity;
import com.fimiyaad.mobile.Helper.Common;
import com.fimiyaad.mobile.Model.GalleryCoverModel;
import com.fimiyaad.mobile.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rohan
 * @date 9/4/17.
 */

public class GalleryYearAdapter extends RecyclerView.Adapter<GalleryYearAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<GalleryCoverModel> data;
    private Activity mActivity;
    private Context mContext;

    public GalleryYearAdapter(Activity activity) {
        mActivity = activity;
        mContext = mActivity.getBaseContext();
        if(inflater == null){inflater = LayoutInflater.from(mContext);}
        data = new GalleryList().GetGalleryLsit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(  inflater.inflate((viewType==0)?
                                R.layout.home_banner_ad :
                                R.layout.layout_gallery_year, parent, false),
                                viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final int pos = position;
        if(pos==0){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            AdView adView = holder.vAdView;
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            return;
        }
        int mYear   = data.get(pos).getYear();
        int mPhoto  = data.get(pos).getImage();
        holder.vYear.setText(String.valueOf(mYear));

        holder.vImage.setImageDrawable(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
                mContext.getResources().getDrawable(mPhoto, mContext.getApplicationContext().getTheme()) :
                mContext.getResources().getDrawable(mPhoto));

        holder.vImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.isInternetAvailable()) {
                    Intent intent = new Intent(mContext, GalleryActivity.class);
                    intent.putExtra(Common.GALLERY_YEAR_TAG, data.get(pos).getYear());
                    mActivity.startActivity(intent);
                }else{
                    Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position==0)? 0 : 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AdView vAdView;
        private TextView vYear;
        private ImageView vImage;

        private ViewHolder(View v, int viewType){
            super(v);
            if(viewType==0){
                vAdView = v.findViewById(R.id.adView);
            }else {
                vYear  = v.findViewById(R.id.year);
                vImage = v.findViewById(R.id.image);
            }
        }
    }

    private class GalleryList {
        private List<GalleryCoverModel> data;

        private List<GalleryCoverModel> GetGalleryLsit(){
            if(data==null) {
                data = new ArrayList<>();
                data.add(null);
                data.add(new GalleryCoverModel(2018, R.drawable.img_2018));
                data.add(new GalleryCoverModel(2017, R.drawable.img_2017));
                data.add(new GalleryCoverModel(2016, R.drawable.img_2016));
                data.add(new GalleryCoverModel(2015, R.drawable.img_2015));
                data.add(new GalleryCoverModel(2014, R.drawable.img_2014));
            }
            return data;
        }
    }
}
