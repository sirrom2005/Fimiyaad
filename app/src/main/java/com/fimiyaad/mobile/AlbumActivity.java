package com.fimiyaad.mobile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ProgressBar;
import com.fimiyaad.mobile.Adapter.AlbumAdapter;
import com.fimiyaad.mobile.Helper.Common;
import com.fimiyaad.mobile.Helper.Gallery;
import com.fimiyaad.mobile.Interface.IRepositoryGalleryView;
import com.fimiyaad.mobile.Model.GalleryModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private static ProgressBar progressBar;
    private static int    GALLERY_KEY;
    private static String GALLERY_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        progressBar = findViewById(R.id.progressBar);

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(getBaseContext()).inflateTransition(R.transition.share_ele_transition));
        }*/

        GALLERY_KEY = getIntent().getIntExtra("GALLERY_KEY", 0);
        GALLERY_URL = getIntent().getStringExtra("GALLERY_URL").replace("index.html","thumbs/").replace(" ", "%20");
        String GALLERY_TITLE = getIntent().getStringExtra("GALLERY_TITLE");
        Bitmap GALLERY_PIC   = getIntent().getParcelableExtra("GALLERY_PIC");

        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle(GALLERY_TITLE);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ImageView photo = findViewById(R.id.gallery_photo);
        //photo.setImageBitmap(GALLERY_PIC);

        MobileAds.initialize(this, getString(R.string.ADMOB_APP_ID));
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, AppFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public static class AppFragment extends Fragment implements IRepositoryGalleryView {
        private AlbumAdapter albumAdapter;

        public static Fragment newInstance() {
            return new AppFragment();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View viewRoot = inflater.inflate(R.layout.recycler_view, container, false);
            RecyclerView recyclerView = viewRoot.findViewById(R.id.recycler_view);

            albumAdapter = new AlbumAdapter(super.getActivity(), GALLERY_URL);

            int min,max;
            switch(Common.GetDeviceDensity(getActivity())){
                case DisplayMetrics.DENSITY_HIGH :
                    min = 3;
                    max = 5;
                break;
                default:
                    min = 4;
                    max = 6;
                break;
            }

            progressBar.setVisibility(View.VISIBLE);

            int spanCount = getResources().getConfiguration().orientation == 1 ? min : max;
            recyclerView.setAdapter(albumAdapter);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));

            final Gallery gallery = new Gallery(this);
            gallery.FetchGalleryById(GALLERY_KEY);

            return viewRoot;
        }

        @Override
        public void showRepositories(List<GalleryModel.Album> repo) {
            albumAdapter.setData(repo);
            albumAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }
}
