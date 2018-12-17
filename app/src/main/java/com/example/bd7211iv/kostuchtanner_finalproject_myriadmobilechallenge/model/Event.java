package com.example.bd7211iv.kostuchtanner_finalproject_myriadmobilechallenge.model;

import java.util.List;

public class Event {
    private String id;
    private String title;
    private String image_url;
    private String event_description;
    private String start_date_time;
    private String end_date_time;
    private String location;
    private String featured;
    private List<Speaker> speakers;

    public String getId(){return id;}
    public String getEvent_description(){return event_description;}
    public String getTitle(){return title;}
    public String getImage_url(){return image_url;}
    public String getStart_date_time(){return start_date_time;}
    public String getEnd_date_time(){return end_date_time;}
    public String getLocation(){return location;}
    public String getFeatured(){return featured;}
    public List<Speaker> getSpeakers(){return speakers;}
}
