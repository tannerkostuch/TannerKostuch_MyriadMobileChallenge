package com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.R;
import com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.activity.EventActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.MyViewHolder>{
    private List<Speaker> speakers;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView bio;
        public MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.speakerName);
            bio = view.findViewById(R.id.speakerBio);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Speaker speaker= speakers.get(position);
        holder.name.setText(speaker.getFirst_name()+" "+speaker.getLast_name());
        holder.bio.setText(speaker.getBio());
    }
    public SpeakerAdapter(List<Speaker> speakers) {this.speakers=speakers;}

    @Override
    public int getItemCount(){
        return speakers.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

}
