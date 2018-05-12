package com.fimiyaad.mobile.Interface;

import com.fimiyaad.mobile.Model.GalleryModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rohan
 * on 9/4/17.
 */

public interface GalleryClient {
    @GET("services/get_gallery_images/id/{id}")
    Call<List<GalleryModel.Album>> galleryRepo(@Path("id") int id);

    @GET("services/get_gallery/year/{year}")
    Call<List<GalleryModel>> galleryListRepo(@Path("year") int year);
}