package com.fimiyaad.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.*;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.fimiyaad.mobile.Adapter.GalleryAdapter;
import com.fimiyaad.mobile.Helper.Common;
import com.fimiyaad.mobile.Helper.GalleryDeserializer;
import com.fimiyaad.mobile.Model.GalleryModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class GalleryActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private static HashMap<String, List<GalleryModel>> GalleryMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(getBaseContext()).inflateTransition(R.transition.share_ele_transition));
        }*/

        int mYear = getIntent().getIntExtra(Common.GALLERY_YEAR_TAG, 2018);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mYear + " Gallery");
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, getString(R.string.ADMOB_APP_ID));
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.view_pager);

        if (savedInstanceState == null) {
            GetGalleryListByYear(mYear);
        }
    }

    private void GetGalleryListByYear(final int year)
    {
        @SuppressLint("StaticFieldLeak")
        final AsyncTask<Void, Void, HashMap<String, List<GalleryModel>>> task = new AsyncTask<Void, Void, HashMap<String, List<GalleryModel>>>() {
            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected HashMap<String, List<GalleryModel>> doInBackground(Void... voids) {
                String url = String.format("http://www.fimiyaad.com/services/get_gallery/year/%s", year);

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody b = response.body();
                    GalleryDeserializer deserializer = new GalleryDeserializer((b != null) ? b.string() : "");
                    try {
                        return deserializer.deserializer();
                    }catch(Exception ex) {
                        Log.e(Common.TAG, ex.getMessage());
                        return null;
                    }
                } catch (IOException ex) {
                    Log.e(Common.TAG, ex.getMessage());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(HashMap<String, List<GalleryModel>> s) {
                super.onPostExecute(s);
                if(s!=null) {
                    GalleryMap = s;
                    LoadGallery();
                }
                progressBar.setVisibility(View.GONE);
            }
        };

        task.execute();
    }


    private void LoadGallery() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Common.GALLERY_DATA,  GalleryMap);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        GalleryMap = (HashMap<String, List<GalleryModel>>) savedInstanceState.getSerializable(Common.GALLERY_DATA);
        LoadGallery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Common.about(this);
            return true;
        }
        if (id == R.id.action_jam) {
            mViewPager.setCurrentItem(0);
            return true;
        }
        if (id == R.id.action_usa) {
            mViewPager.setCurrentItem(1);
            return true;
        }
        if (id == R.id.action_uk) {
            mViewPager.setCurrentItem(2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
            //gallery = new Gallery(this);
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
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
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View viewRoot = inflater.inflate(R.layout.recycler_view, container, false);
            RecyclerView recyclerView = viewRoot.findViewById(R.id.recycler_view);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    recyclerView.setAdapter(new GalleryAdapter(getActivity(), GalleryMap.get("jamaica")));
                break;
                case 1:
                    recyclerView.setAdapter(new GalleryAdapter(getActivity(), GalleryMap.get("usa")));
                break;
                case 2:
                    recyclerView.setAdapter(new GalleryAdapter(getActivity(), GalleryMap.get("uk")));
                break;
            }

            int min,max;
            switch(Common.GetDeviceDensity(getActivity())){
                case DisplayMetrics.DENSITY_HIGH :
                    min = 2;
                    max = 4;
                break;
                default:
                    min = 3;
                    max = 6;
                break;
            }

            int spanCount = getResources().getConfiguration().orientation == 1 ? min : max;
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
            return viewRoot;
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
            return 3;
        }
    }
}
