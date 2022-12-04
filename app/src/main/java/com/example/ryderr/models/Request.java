package com.example.ryderr.models;

import java.util.ArrayList;

public class Request {
    String request_id;
    int capacity;
    String vehicle_type;
    String from_location;
    String to_location;
    String time;
    int expected_fare;

    public ArrayList<String> getRiders_names() {
        return riders_names;
    }

    public void setRiders_names(ArrayList<String> riders_names) {
        this.riders_names = riders_names;
    }

    ArrayList<String> riders_names;

    public int getExpected_fare() {
        return expected_fare;
    }

    public void setExpected_fare(int expected_fare) {
        this.expected_fare = expected_fare;
    }

    public String getFare_text() {
        return String.valueOf(getExpected_fare());
    }

    public void setFare_text(String fare_text) {
        this.fare_text = fare_text;
    }

    String fare_text;
    ArrayList<String> riders_ids;
    int count_riders;

    public Request(){

    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getRiders_ids() {
        return riders_ids;
    }

    public void setRiders_ids(ArrayList<String> riders_ids) {
        this.riders_ids = riders_ids;
    }

    public int getCount_riders() {
        return count_riders;
    }

    public void setCount_riders(int count_riders) {
        this.count_riders = count_riders;
    }

    public Request(String request_id, int capacity, String vehicle_type, String from_location,
            String to_location, String time, ArrayList<String> riders_ids) {
        this.request_id = request_id;
        this.capacity = capacity;
        this.vehicle_type = vehicle_type;
        this.from_location = from_location;
        this.to_location = to_location;
        this.time = time;
        this.riders_ids = riders_ids;

        this.count_riders = riders_ids.size();
    }

    public Request(int capacity, String vehicle_type, String from_location,
            String to_location, String time, ArrayList<String> riders_ids) {
        this.capacity = capacity;
        this.vehicle_type = vehicle_type;
        this.from_location = from_location;
        this.to_location = to_location;
        this.time = time;
        this.riders_ids = riders_ids;

        this.count_riders = riders_ids.size();
    }
}
