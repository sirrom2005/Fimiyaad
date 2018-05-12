package com.fimiyaad.mobile.Helper;

import com.fimiyaad.mobile.Interface.EventClient;
import com.fimiyaad.mobile.Interface.IRepositoryEventView;
import com.fimiyaad.mobile.Model.EventModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

/**
 * Created by rohan
 * on 9/28/17.
 */

public class Event {

    private IRepositoryEventView mView;

    public Event(IRepositoryEventView view) {
        mView = view;
    }

    public void FetchEvents() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(Common.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        // Create REST adapter which points the video API endpoint.
        EventClient event = retrofit.create(EventClient.class);

        // Fetch a list of the Video repositories.
        Call<List<EventModel>> call = event.eventRepo();

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                mView.showEventRepositories(response.body());
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                System.out.println("Event Error " + t.getLocalizedMessage() + ">>>" + call.toString());
            }
        });
    }
}
