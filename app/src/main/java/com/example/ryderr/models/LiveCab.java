package com.example.ryderr.models;

import java.util.ArrayList;
import java.util.Iterator;

public class LiveCab {


    String live_cab_id;

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    String driver_id;

    String driver_name;
    String vehicle_number;

    public String getUpi_id() {
        return upi_id;
    }

    public void setUpi_id(String upi_id) {
        this.upi_id = upi_id;
    }

    String upi_id;

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    String vehicle_type;
    int capacity;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    boolean live = false;

    String from_location;
    String to_location;
    String departure_time;
    int fare;

    ArrayList<String> riders_ids;

    public ArrayList<String> getRiders_names() {
        return riders_names;
    }

    public void setRiders_names(ArrayList<String> riders_names) {
        this.riders_names = riders_names;
    }

    ArrayList<String> riders_names;
    int count_riders;

    public ArrayList<String> getRiders_ids() {
        return riders_ids;
    }

    public void setRiders_ids(ArrayList<String> riders_ids) {
        this.riders_ids = riders_ids;
    }

    public ArrayList<String> getDisplay() {
     //   return new ArrayList<String>(Collections.singleton("hi"));
        ArrayList<String> rider = getRiders_names();
        double riderFare = fare/count_riders;
        display = new ArrayList<String>();
        Iterator itr = rider.iterator();
        while(itr.hasNext()){
            display.add(itr.next().toString()+"\t \tRs. " + riderFare);
        }
        return display;
    }

    public void setDisplay(ArrayList<String> display) {

        this.display = display;
    }

    ArrayList<String> display;



    public LiveCab(String cab_id, String driver_id, boolean live, String from_location,
            String to_location, String departure_time, int fare,
            ArrayList<String> riders_ids, ArrayList<String> riders_names, String driverName, String vehicleNumber, String vehicle_type,
            int capacity) {
        this.live_cab_id = cab_id;
        this.driver_id = driver_id;
        this.live = live;
        this.from_location = from_location;
        this.to_location = to_location;
        this.departure_time = departure_time;
        this.fare = fare;
        this.riders_ids = riders_ids;
        this.riders_names = riders_names;

//        final Driver[] driver = {new Driver()};
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference docRef = db.collection("drivers").document(driver_id);
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                driver[0] = documentSnapshot.toObject(Driver.class);
//            }
//        });
        this.driver_name = driverName;//driver[0].getName();
        this.vehicle_number = vehicleNumber;//driver[0].getVehicle_number();
        this.vehicle_type = vehicle_type;//driver[0].getVehicle_type();
        this.capacity = capacity;//driver[0].getCapacity();

        this.count_riders = riders_ids.size();

    }

//    public LiveCab(String live_cab_id, String group_chat_id, int capacity, int count_riders,
//            int fare, boolean live,
//            String driver_name, String vehicle_number, String from_location,
//            String to_location, String departure_time) {
//        this.live_cab_id = live_cab_id;
//        this.group_chat_id = group_chat_id;
//        this.capacity = capacity;
//        this.count_riders = count_riders;
//        this.fare = fare;
//        this.live = live;
//        this.driver_name = driver_name;
//        this.vehicle_number = vehicle_number;
//        this.from_location = from_location;
//        this.to_location = to_location;
//        this.departure_time = departure_time;
//        riders_ids = new ArrayList<>();
//    }
    public LiveCab() {

    }



    public String getFareText(){
        int fare = getFare();
        return Integer.toString(fare);
    }
    public String getLive_cab_id() {
        return live_cab_id;
    }

    public void setLive_cab_id(String live_cab_id) {
        this.live_cab_id = live_cab_id;
    }

//    public String getGroup_chat_id() {
//        return group_chat_id;
//    }
//
//    public void setGroup_chat_id(String group_chat_id) {
//        this.group_chat_id = group_chat_id;
//    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCount_riders() {
        return getRiders_names().size();
    }

    public void setCount_riders(int count_riders) {
        this.count_riders = count_riders;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
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

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public ArrayList<String> getRiders() {
        return riders_ids;
    }

    public void setRiders(ArrayList<String> riders_ids) {
        this.riders_ids = riders_ids;
    }
}
