package com.fimiyaad.mobile.Helper;

import com.fimiyaad.mobile.Interface.IRepositoryVideoView;
import com.fimiyaad.mobile.Interface.VideoClient;
import com.fimiyaad.mobile.Model.VideoModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

/**
 * Created by rohan
 * on 9/4/17.
 */

public class Video {

    private IRepositoryVideoView mView;

    public Video(IRepositoryVideoView view) {
        mView = view;
    }

    public void FetchVideos() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(Common.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        // Create REST adapter which points the video API endpoint.
        VideoClient client = retrofit.create(VideoClient.class);

        // Fetch a list of the Video repositories.
        Call<List<VideoModel>> call = client.videoRepo();

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<VideoModel>>() {
            @Override
            public void onResponse(Call<List<VideoModel>> call, Response<List<VideoModel>> response) {
                mView.showVideoRepositories(response.body());
            }

            @Override
            public void onFailure(Call<List<VideoModel>> call, Throwable t) {
                System.out.println("Video Error " + t.getLocalizedMessage() + ">>>" + call.toString());
            }
        });
    }
}
