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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{
    private List<Event> events;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView imageView;
        public TextView time;
        public String formatedTime;
        public MyViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            imageView = view.findViewById(R.id.eventImage);
            time = view.findViewById(R.id.time);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Event event= events.get(position);
        holder.title.setText(event.getTitle());
        // create the time and date of the event
        holder.formatedTime = "";
        String[] dateTime=event.getStart_date_time().split("T");
        String[] brokenTime= dateTime[0].split("-");
        holder.formatedTime+=brokenTime[1]+"/"+brokenTime[2]+"/"+brokenTime[0];
        String[] brokenDate= dateTime[1].split(":");
        holder.formatedTime+="  "+goodTimeFactory(brokenDate[0],brokenDate[1]);
        dateTime=event.getEnd_date_time().split("T");
        brokenDate=dateTime[1].split(":");
        holder.formatedTime+=" - "+ goodTimeFactory(brokenDate[0],brokenDate[1]);

        holder.time.setText(holder.formatedTime);
        Picasso.get().load(event.getImage_url()).into(holder.imageView);
        final String passFormatedDate= holder.formatedTime;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(event.getId());
                Intent eventActivity= new Intent(v.getContext(), EventActivity.class);
                eventActivity.putExtra("id", event.getId());
                eventActivity.putExtra("date", passFormatedDate);
                eventActivity.putExtra("title", event.getTitle());
                v.getContext().startActivity(eventActivity);
            }
        });
    }
    public EventAdapter(List<Event> events) {this.events=events;}

    @Override
    public int getItemCount(){
        return events.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    public String goodTimeFactory(String hour, String minute){
        Integer intTime= Integer.parseInt(hour);
        String temp="";
        if(intTime>12) {
            temp += Integer.toString(intTime - 12);
            if(Integer.parseInt(minute)>0) {
                temp+=":"+minute;
            }
            return temp+"pm";
        }
        else{
            temp+=hour.replaceFirst("^0+(?!$)","");
            if(Integer.parseInt(minute)>0){
                temp+=":"+minute;
            }
            return temp+"am";
        }
    }
}
