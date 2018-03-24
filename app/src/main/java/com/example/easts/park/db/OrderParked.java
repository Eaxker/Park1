package com.example.easts.park.db;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/11/20.
 */

public class OrderParked implements Serializable{
    private String parking_id;
    private String space_location;
    private double checkmoney;
    private String start_time;
    private String over_time;
    private String phonenumber;

    public void setCheckmoney(double checkmoney) {
        this.checkmoney = checkmoney;
    }

    public double getCheckmoney() {
        return checkmoney;
    }

    public void setOver_time(String over_time) {
        this.over_time = over_time;
    }

    public String getOver_time() {
        return over_time;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getParking_id() {
        return parking_id;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setSpace_location(String space_location) {
        this.space_location = space_location;
    }

    public String getSpace_location() {
        return space_location;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_time() {
        return start_time;
    }
}

