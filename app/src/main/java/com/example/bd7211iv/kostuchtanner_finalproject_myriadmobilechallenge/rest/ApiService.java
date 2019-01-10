package com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.rest;

import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Event;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Speaker;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Token;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private Retrofit retrofit= new Retrofit.Builder()
            .baseUrl("https://challenge.myriadapps.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private EventService eventService;

    public ApiService() {eventService= retrofit.create(EventService.class);}

    public void login(Callback<Token> callback, String username, String password){
         eventService.login(username,password).enqueue(callback);
    }

    public void getEvents(Callback<List<Event>> callback, String token){
        eventService.getSecret(token).enqueue(callback);
    }

    public void getSpecificEvent(Callback<Event> callback, String token, String id){
        eventService.getSpecificEvent(token,id).enqueue(callback);
    }

    public void getSpeaker(Callback<Speaker> callback, String token, String id){
        eventService.getSpeaker(token,id).enqueue(callback);
    }
}
