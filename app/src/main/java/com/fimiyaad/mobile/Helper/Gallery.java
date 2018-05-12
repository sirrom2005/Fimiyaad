package com.fimiyaad.mobile.Helper;

import android.util.Log;
import com.fimiyaad.mobile.Interface.GalleryClient;
import com.fimiyaad.mobile.Interface.IRepositoryGalleryView;
import com.fimiyaad.mobile.Model.GalleryModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rohan
 * on 9/4/17.
 */

public class Gallery {

    private IRepositoryGalleryView mView;

    public Gallery(IRepositoryGalleryView view) {
        mView = view;
    }

    public void FetchGalleryById(int id) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(Common.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        // Create REST adapter which points the gallery API endpoint.
        GalleryClient client = retrofit.create(GalleryClient.class);

        // Fetch a list of the Gallery repositories.
        Call<List<GalleryModel.Album>> call = client.galleryRepo(id);

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GalleryModel.Album>>() {
            @Override
            public void onResponse(Call<List<GalleryModel.Album>> call, Response<List<GalleryModel.Album>> response) {
                mView.showRepositories(response.body());
            }

            @Override
            public void onFailure(Call<List<GalleryModel.Album>> call, Throwable t) {
                System.out.println("GalleryModel Error " + t.getLocalizedMessage() + ">>>" + call.toString());
            }
        });
    }
}
