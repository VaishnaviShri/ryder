package com.example.ryderr.models;

import java.util.ArrayList;

public class Driver {
    String uid;
    String name;
    String vehicle_number;

    public String getCurrent_ride_id() {
        return current_ride_id;
    }

    public void setCurrent_ride_id(String current_ride_id) {
        this.current_ride_id = current_ride_id;
    }

    String vehicle_type;
    int capacity;
    String current_ride_id;
    ArrayList<String> my_rides_ids;

    public ArrayList<String> getMy_rides_ids() {
        return my_rides_ids;
    }

    public void setMy_rides_ids(ArrayList<String> my_rides_ids) {
        this.my_rides_ids = my_rides_ids;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


}
