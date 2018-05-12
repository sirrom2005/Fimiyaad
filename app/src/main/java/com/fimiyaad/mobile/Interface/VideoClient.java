package com.fimiyaad.mobile.Interface;
/*
import com.fimiyaad.Model.VideoModel;
import com.fimiyaad.mobile.Model.VideoModel;
import retrofit2.Call;
import retrofit2.http.GET;*/

import com.fimiyaad.mobile.Model.VideoModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by rohan
 * on 9/4/17.
 */

public interface VideoClient {
    @GET("services/get_videos/")
    Call<List<VideoModel>> videoRepo();
}