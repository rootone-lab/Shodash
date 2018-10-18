package com.rootonelabs.vicky.shodash;

/**
 * Created by Vicky on 13-Oct-18.
 */

public class listDetails {
    private String speakerName, topic, yt_url, date;
    private int day;

    public listDetails() {
    }

    public listDetails(String date, int day, String speakerName, String topic, String yt_url) {
        this.speakerName = speakerName;
        this.topic = topic;
        this.yt_url = yt_url;
        this.date = date;
        this.day = day;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getYt_url() {
        return yt_url;
    }

    public void setYt_url(String yt_url) {
        this.yt_url = yt_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
