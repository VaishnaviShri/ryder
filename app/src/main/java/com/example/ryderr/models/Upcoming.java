package com.example.ryderr.models;

import java.sql.Time;
import java.util.ArrayList;

public class Upcoming {

    String driver_name;
    String vehicle_number;
    String from_location;
    String to_location;
    int progress;
    int fare;
    int driver_img;
    Time departure_time;
    ArrayList<String> riders;

    public int getDriver_img() {
        return driver_img;
    }

    public void setDriver_img(int driver_img) {
        this.driver_img = driver_img;
    }

    public Upcoming(){

    }

    public Upcoming(String driver_name, String vehicle_number, int progress, String from_location, String to_location, Time departure_time, int driver_img) {
        this.driver_name = driver_name;
        this.progress = progress;
        this.from_location = from_location;
        this.to_location = to_location;
        this.departure_time = departure_time;
        this.driver_img = driver_img;
    }

    void computeFare (String from, String to){
        fare = 450;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getFrom_location() {
        return from_location;
    }

    public void setFrom_location(String from_location) {
        this.from_location = from_location;
    }

    public String getTo_location() {
        return to_location;
    }

    public void setTo_location(String to_location) {
        this.to_location = to_location;
    }

    public Time getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Time departure_time) {
        this.departure_time = departure_time;
    }
}
