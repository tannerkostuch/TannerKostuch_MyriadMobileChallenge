package com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.rest;

import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Event;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Speaker;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Token;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventService {
    @POST("login")
    Call<Token> login(@Query("Username") String username,
                       @Query("Password") String password);

    @GET("events")
    Call<List<Event>> getSecret(@Header("Authorization") String token);

    @GET("events/{id}")
    Call<Event> getSpecificEvent(@Header("Authorization") String token,
                               @Path("id") String id);
    @GET("speakers/{id}")
    Call<Speaker> getSpeaker(@Header("Authorization") String token,
                             @Path("id") String id);
}
