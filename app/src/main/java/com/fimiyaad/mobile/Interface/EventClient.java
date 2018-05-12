package com.fimiyaad.mobile.Interface;

import com.fimiyaad.mobile.Model.EventModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by rohan
 * on 9/4/17.
 */

public interface EventClient {
    @GET("services/get_events/")
    Call<List<EventModel>> eventRepo();
}