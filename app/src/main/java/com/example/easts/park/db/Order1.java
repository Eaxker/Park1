package com.example.easts.park.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/11/16.
 */

public class Order1 extends DataSupport implements Serializable {
    private int orderId;
    private int parkingId;
    private String iphoneNumber;
    private String parkingLot;
    private String orderState;
    private String start_parking_datetime;
    private String end_parking_datetime;
    private float pay;

    public Order1(){

    }
    public Order1(int orderId,String iphoneNumber,int parkingId,String parkingLot,
                  String orderState,String start_parking_datetime,String  end_parking_datetime,float pay){
        this.orderId=orderId;
        this.iphoneNumber=iphoneNumber;
        this.parkingId=parkingId;
        this.parkingLot=parkingLot;
        this.orderState=orderState;
        this.start_parking_datetime=start_parking_datetime;
        this.end_parking_datetime=end_parking_datetime;
        this.pay=pay;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public int getParkingId() {
        return parkingId;
    }

    public void setEnd_parking_datetime(String end_parking_datetime) {
        this.end_parking_datetime = end_parking_datetime;
    }

    public String getEnd_parking_datetime() {
        return end_parking_datetime;
    }

    public void setIphoneNumber(String iphoneNumber) {
        this.iphoneNumber = iphoneNumber;
    }

    public String getIphoneNumber() {
        return iphoneNumber;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setPay(float pay) {
        this.pay = pay;
    }

    public float getPay() {
        return pay;
    }

    public void setStart_parking_datetime(String start_parking_datetime) {
        this.start_parking_datetime = start_parking_datetime;
    }

    public String getStart_parking_datetime() {
        return start_parking_datetime;
    }
}
