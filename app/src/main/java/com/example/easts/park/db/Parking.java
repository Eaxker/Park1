package com.example.easts.park.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/11/5.
 */
//停车场类
public class Parking extends DataSupport implements Serializable {
    private String parkingId;
    private String parking_name;
//    private String parking_image1;//图片也不要
//    private String parking_image2;//先不要图片二
    private String parking_location;
    private String residual_lot;//剩余车位如50
    private float freight_basis;
    private float longitude;//经度
    private float latitude;//维度
    private int thumbs_up;//点赞量
//    private float assess_level;//评价级别先不要
    private int assess;//总评论数
    private int reserve_time;//预定剩余时间数每个车位，单位为秒App端有计时功能

    public Parking(){
    }
    public Parking(String parkingId,String parking_name,String parking_location,String residual_lot,
                   float freight_basis,float longitude,float latitude,int thumbs_up,int assess,int reserve_time){
        this.parkingId=parkingId;
        this.parking_name=parking_name;
        this.parking_location=parking_location;
        this.residual_lot=residual_lot;
        this.freight_basis=freight_basis;
        this.longitude=longitude;
        this.latitude=latitude;
        this.thumbs_up=thumbs_up;
        this.assess=assess;
        this.reserve_time=reserve_time;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getParking_name(){
        return parking_name;
    }
    public void setParking_name(String parking_name){
        this.parking_name=parking_name;
    }

//    public String getParking_image1() {
//        return parking_image1;
//    }
//
//    public void setParking_image1(String parking_image1) {
//        this.parking_image1 = parking_image1;
//    }
//
//    public String getParking_image2() {
//        return parking_image2;
//    }
//
//    public void setParking_image2(String parking_image2) {
//        this.parking_image2 = parking_image2;
//    }

    public String getResidual_lot() {
        return residual_lot;
    }
    public void setResidual_lot(String residual_lot) {
        this.residual_lot = residual_lot;
    }
    public String getParking_location() {
        return parking_location;
    }

    public void setParking_location(String parking_location) {
        this.parking_location = parking_location;
    }

    public float getFreight_basis() {
        return freight_basis;
    }

    public void setFreight_basis(float freight_basis) {
        this.freight_basis = freight_basis;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getThumbs_up() {
        return thumbs_up;
    }

    public void setThumbs_up(int thumbs_up) {
        this.thumbs_up = thumbs_up;
    }

//    public float getAssess_level() {
//        return assess_level;
//    }
//
//    public void setAssess_level(float assess_level) {
//        this.assess_level = assess_level;
//    }

    public int getAssess() {
        return assess;
    }
    public void setAssess(int assess) {
        this.assess = assess;
    }

    public int getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(int reserve_time) {
        this.reserve_time = reserve_time;
    }
}
