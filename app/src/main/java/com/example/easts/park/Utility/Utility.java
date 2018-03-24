package com.example.easts.park.Utility;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.easts.park.db.Order1;
import com.example.easts.park.db.Parking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/11/14.
 */

public class Utility {
    //解析和处理服务器返回的停车场数据
    public static boolean handleParkingResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allParkings = new JSONArray(response);
                for (int i = 0; i < allParkings.length(); i++) {
                    JSONObject parkingObject = allParkings.getJSONObject(i);
                    Parking p = new Parking();
                    Log.i("1111","parking_id"+parkingObject.getString("parking_id")+"\n");
                    Log.i("1111","parking_name"+parkingObject.getString("parking_name")+"\n");
                    Log.i("1111","parking_location"+parkingObject.getString("parking_location"));
                    p.setParkingId(parkingObject.getString("parking_id"));
                    p.setParking_name(parkingObject.getString("parking_name"));
//                    p.setParking_image1(parkingObject.getString("parking_image"));
                    p.setParking_location(parkingObject.getString("parking_location"));
                    p.setFreight_basis(Float.valueOf(parkingObject.getString("freight_basis")));//浮点数
                    p.setResidual_lot(parkingObject.getString("car_number"));
                    p.setAssess(parkingObject.getInt("assess_time"));//评价次数
//                    p.setAssess_level(parkingObject.getInt("assess_level"));先没有评价等级
                    p.setLatitude(Float.valueOf(parkingObject.getString("latitude")));
                    p.setLongitude(Float.valueOf(parkingObject.getString("longitude")));
                    p.setThumbs_up(parkingObject.getInt("thumbs_up"));
                    /**
                     * s剩余时间
                     */
                    p.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //解析和处理服务器返回的订单数据

    /**
     * private int parkingId;
     * private int parkingImageId;
     * private String parking_lot;
     * private String parking_name;
     * private String orderState;//订单状态
     * private String start_parking_datetime;
     * private String end_parking_datetime;
     * private float pay;
     *
     * @param response
     * @return
     */
    public static boolean handleOrdersResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allOrders = new JSONArray(response);
                for (int i = 0; i < allOrders.length(); i++) {
                    JSONObject orderObject = new JSONObject();
                    Order1 order = new Order1();
                    order.setOrderId(orderObject.getInt("check_id"));
                    order.setIphoneNumber(orderObject.getString("phonenumber"));
                    order.setParkingId(orderObject.getInt("parking_id"));
//                    order.setParkingImageId(orderObject.getInt(""));写死
                    order.setParkingLot(orderObject.getString("space_location"));
//                    order.setParking_name(orderObject.getString("parking_name"));//写死
                    /**
                     * 0待使用
                     * 1待付款
                     * 2待评价
                     * 3退款/售后
                     */
                    order.setOrderState(orderObject.getString("checkstatus"));
                    order.setStart_parking_datetime(orderObject.getString("check_start_time"));
                    order.setEnd_parking_datetime(orderObject.getString("check_over_time"));
                    order.setPay(Float.valueOf(orderObject.getString("check_money")));
                    order.save();
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
