package com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.R;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Event;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.EventAdapter;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.rest.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");


        prefs=getSharedPreferences("PREFS",Context.MODE_PRIVATE);
        String token= prefs.getString("Token","");
        //System.out.println(token);
        ApiService apiService= new ApiService();
        apiService.getEvents(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response <List<Event>> response) {
                    List<Event> events= response.body();
                    RecyclerView recyclerView= findViewById(R.id.recycler_view);
                    EventAdapter eAdapter= new EventAdapter(events);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eAdapter);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        },token);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                // Do whatever you want to do on logout click.
                prefs=getSharedPreferences("PREFS",Context.MODE_PRIVATE);
                prefs.edit().putString("Token","").apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
