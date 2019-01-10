package com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.activity;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.R;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Event;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.Speaker;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model.SpeakerAdapter;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.rest.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    TextView title, date, description, location;
    ImageView eventImage;
    List<Speaker> speakers;
    Boolean done=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEvent);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.eventTitle);
        date = findViewById(R.id.eventDate);
        description= findViewById(R.id.eventDescription);
        location = findViewById(R.id.eventLocation);
        eventImage = findViewById(R.id.eventImage);
        //Get the token
        SharedPreferences prefs=getSharedPreferences("PREFS",Context.MODE_PRIVATE);
        final String token= prefs.getString("Token","");
        //Get the data
        Intent mainIntent=getIntent();
        String recyclerId=mainIntent.getStringExtra("id");
        String recyclerDate=mainIntent.getStringExtra("date");
        String recyclerTitle=mainIntent.getStringExtra("title");

        getSupportActionBar().setTitle(recyclerTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        speakers= new ArrayList<>();
        final ApiService apiService= new ApiService();
        apiService.getSpecificEvent(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event event= response.body();
                title.setText(event.getTitle());
                description.setText(event.getEvent_description());
                location.setText(event.getLocation());
                Picasso.get().load(event.getImage_url()).into(eventImage);

                // loops another response inside in order to get the speakers.
                final int size=event.getSpeakers().size();
                for(int i=0; i<size; i++) {
                    //System.out.println(i);
                    if(i==size-1) {
                        done= true;
                    }
                    apiService.getSpeaker(new Callback<Speaker>() {
                        @Override
                        public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                            Speaker speaker = response.body();
                            speakers.add(speaker);
                            // It had to be... done hahahaha get it?
                            //No seriously though, this is needed as the task runs in the background so the list will be empty unless you have some fast wifi.
                            if(done) {
                                //System.out.println(speakers.size());
                                RecyclerView recyclerView = findViewById(R.id.event_recycler_view);
                                SpeakerAdapter sAdapter = new SpeakerAdapter(speakers);

                                recyclerView.setLayoutManager( new LinearLayoutManager(EventActivity.this));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(sAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<Speaker> call, Throwable t) {
                            System.out.println("FAILED");
                        }
                    }, token, event.getSpeakers().get(i).getId());
                }

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        },token,recyclerId);

                //title.setText();

        date.setText(recyclerDate);
        //eventImage.setImageResource(img);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                // Do whatever you want to do on logout click.
                SharedPreferences prefs=getSharedPreferences("PREFS",Context.MODE_PRIVATE);
                prefs.edit().putString("Token","").apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
