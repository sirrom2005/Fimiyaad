package com.fimiyaad.mobile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fimiyaad.mobile.Helper.Common;
import com.fimiyaad.mobile.Model.GalleryModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;
import java.util.Locale;

/**
 * Created by rohan
 * on 10/4/17.
 */

public class LargePhotoActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 2001;
    private static String URL;
    private static List<GalleryModel.Album> IMG_DATA;
    private static String imgUrl;
    private static int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getIntent().getIntExtra("GALLERY_KEY", 0);
        URL = getIntent().getStringExtra("GALLERY_URL");
        IMG_DATA = (List<GalleryModel.Album>) getIntent().getSerializableExtra("GALLERY_DATA");

        setContentView(R.layout.large_photo_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button save  = findViewById(R.id.save);
        Button share = findViewById(R.id.share);
        final TextView galleryText = findViewById(R.id.gallery_text);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePhoto();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = URL.concat(IMG_DATA.get(mPosition).getImage())
                                .replace(" ", "%20")
                                .replace("/thumbs/","/images/")
                                .replace(".jpg",".html");
                String text = getString(R.string.share_photo, Common.getShortUrl(url));
                Common.ShareIntent(getBaseContext(), text, null);
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                galleryText.setText(String.format(Locale.US, "| %d of %d |",(position + 1), IMG_DATA.size()));
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        //Context.getExternalCacheDir()
        galleryText.setText(String.format(Locale.US, "| %d of %d |", mPosition+1, IMG_DATA.size()));

        MobileAds.initialize(this, getString(R.string.ADMOB_APP_ID));
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void SavePhoto() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED )
        {
            imgUrl = URL.concat(IMG_DATA.get(mPosition).getImage()).replace(" ", "%20").replace("/thumbs/","/images/");
            Glide.with(getBaseContext()).load(imgUrl).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable img, Transition<? super Drawable> transition) {
                    com.fimiyaad.mobile.Helper.Common.SavePhoto(getApplicationContext(), img);
                }
            });
        }else{
            //if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mBaseActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)){}
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SavePhoto();
                }
            break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int pos;
        //padding to remove image border
        private int mPadding = 15;

        public PlaceholderFragment() {}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment;
            fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.large_photo_layout, container, false);
            final ImageView imageView = rootView.findViewById(R.id.photo);

            if(getContext()!=null) {
                pos = getArguments().getInt(ARG_SECTION_NUMBER);
                imgUrl = URL.concat(IMG_DATA.get(pos).getImage()).replace(" ", "%20");

                Glide.with(getContext()).load(imgUrl).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable img, Transition<? super Drawable> transition) {
                    Bitmap bitmap = ((BitmapDrawable) img).getBitmap();

                    Bitmap crop = Bitmap.createBitmap(bitmap, mPadding, mPadding, bitmap.getWidth() - mPadding * 2, bitmap.getHeight() - mPadding * 2);

                    FrameLayout.LayoutParams layoutParams;



                    if(getResources().getConfiguration().orientation == 1) {
                        if(bitmap.getHeight() > bitmap.getWidth()) {
                            //w h
                            layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
                        } else {
                            layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                        }
                    } else {
                        layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
                    }

                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setAdjustViewBounds(true);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageBitmap(crop);

                    imgUrl = URL.concat(IMG_DATA.get(pos).getImage()).replace(" ", "%20").replace("/thumbs/", "/images/");
                    Glide.with(getContext()).load(imgUrl).into(
                        new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable d, Transition<? super Drawable> transition) {
                                imageView.setImageDrawable(d);
                            }
                        }
                    );
                    }
                });
            }
            return rootView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return IMG_DATA.size();
        }
    }
}
