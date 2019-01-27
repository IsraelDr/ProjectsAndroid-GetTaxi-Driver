package com.example.srulispc.projectsandroid_driver.controller.model.backend;

import com.example.srulispc.projectsandroid_driver.controller.model.entities.CustomLocation;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.util.ArrayList;

public interface Ibackend {

    public interface Action<T>{
        void onDataChange(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double percent);
    }

    void listenToRideList(Action<ArrayList<Ride>> action);
    void stopListenToRideList();

    void addDriver(Driver newDriver);

    ArrayList<Driver> getAllDrivers();

    ArrayList<Ride> getallopenrides();

    ArrayList<Ride> getallclosedrides();

    ArrayList<Ride> getspecificdriverrides();

    ArrayList<Ride> getopenridestospecificdestination();

    ArrayList<Ride> geopenridesinspecificdistance();

    ArrayList<Ride> getridesbydate();

    ArrayList<Ride> getridesbyamount();

    void setstatus(String id, Ride.Status s);

    void setDriverID(String id, int driveID);
    void setmyLocation(String Driverid,CustomLocation mylocation);


}
