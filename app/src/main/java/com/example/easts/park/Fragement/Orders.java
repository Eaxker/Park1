package com.example.easts.park.Fragement;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easts.park.Adapter.OrderAdapter;
import com.example.easts.park.Httptool.HttpUtil;
import com.example.easts.park.MyApplication;
import com.example.easts.park.R;
import com.example.easts.park.TabLayoutActivity;
import com.example.easts.park.Takesuccess;
import com.example.easts.park.Utility.Utility;
import com.example.easts.park.db.Order1;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by lenovo on 2017/10/31.
 * 订单界面
 */

public class Orders extends Fragment {


    public static final int TabLayoutActivity = 1;
    public static final int UNUSED = 1;
    public static final int UNPAY = 2;
    public static final int UNASSESS = 3;
    public static final int BackServerMoney = 4;
    private ProgressDialog progressDialog;
    private OrderAdapter orderAdapter;
    View view;
    ImageView to_orderdetails;
    ImageView to_make_IV;
    ImageView to_pay_IV;
    ImageView to_assess_IV;
    ImageView to_backservice_IV;
    RecyclerView recentlyOrder_RV;
    ImageButton to_park;
    ImageButton to_take;
    TextView showRecentlyOrder;
    private String addressTakeParkingLot="http://192.168.1.127:8080/park/carspace/takeCarspace";

    private List<Order1> orderList = new ArrayList<>();
    private List<Order1> RecentlyorderList = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initOrders();
        view = inflater.inflate(R.layout.park_layout, container, false);
        to_orderdetails = (ImageView) view.findViewById(R.id.to_orderdetails);
        to_make_IV = (ImageView) view.findViewById(R.id.to_make_IV);
        showRecentlyOrder = (TextView) view.findViewById(R.id.RecentlyOrderTV);
        to_assess_IV = (ImageView) view.findViewById(R.id.to_assess_IV);
        to_pay_IV = (ImageView) view.findViewById(R.id.to_pay_IV);
        to_backservice_IV = (ImageView) view.findViewById(R.id.to_backservice_IV);
        recentlyOrder_RV = (RecyclerView) view.findViewById(R.id.recentlyOrder_RV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recentlyOrder_RV.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderAdapter(RecentlyorderList);
        recentlyOrder_RV.setAdapter(orderAdapter);


        to_park = (ImageButton) view.findViewById(R.id.to_park);
        to_take = (ImageButton) view.findViewById(R.id.to_take);

        to_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            OkHttpClient okHttpClient = new OkHttpClient();
//                            RequestBody requestBody = new FormBody.Builder().add("parking_id", "1")
//                                    .add("space_location", "A4").add("phonenumber", "13456778654").build();
//                            Request request = new Request.Builder().url(addressTakeParkingLot).post(requestBody).build();
//                            Response response = okHttpClient.newCall(request).execute();
//                            String result = response.body().string();
//                            Log.i("1111", result);

//                            Gson gson = new Gson();
//                            final OrderParked orderParked = gson.fromJson(result, OrderParked.class);
//                            Order1 orderParked=new Order1();
//                            orderParked.setParkingLot("万达停车场");
//                            orderParked.setStart_parking_datetime("2017-10-14 23:11:58");
//                            orderParked.setEnd_parking_datetime("2017-11-21 22:11:58");
//                            orderParked.setPay((float) 15.999);
                            String result="true";
                            if (!result.equals("false")) {
                                Intent intent = new Intent(v.getContext(), Takesuccess.class);
//                                intent.putExtra("orderParked", orderParked);
                                startActivity(intent);
//                                Toast.makeText(v.getContext(),"88888",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(v.getContext(), "预定车位错误！请重试！", Toast.LENGTH_SHORT).show();
                            }
                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
        });
        showRecentlyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了！", Toast.LENGTH_LONG).show();
                selectRecentlyOrder();
            }
        });
        to_make_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTo = new Intent(v.getContext(), TabLayoutActivity.class);
                intentTo.putExtra("OrderState", UNUSED);
                startActivity(intentTo);
            }
        });
        to_pay_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTo = new Intent(v.getContext(), TabLayoutActivity.class);
                intentTo.putExtra("OrderState", UNPAY);
                startActivity(intentTo);
            }
        });
        to_assess_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTo = new Intent(v.getContext(), TabLayoutActivity.class);
                intentTo.putExtra("OrderState", UNASSESS);
                startActivity(intentTo);
            }
        });
        to_backservice_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTo = new Intent(v.getContext(), TabLayoutActivity.class);
                intentTo.putExtra("OrderState", BackServerMoney);
                startActivity(intentTo);
            }
        });

        to_orderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TabLayoutActivity.class);
                startActivityForResult(i, TabLayoutActivity);
            }
        });
        return view;
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载....");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    private void initOrders() {
        for (int i = 0; i < 3; i++) {
            Order1 order1 = new Order1(1, "15760939122", 1, "A-21号", "已完成", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 15f);
            RecentlyorderList.add(order1);
            Order1 order2 = new Order1(2, "15760939123", 1, "C-50号", "已完成", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 10f);
            RecentlyorderList.add(order2);
            Order1 order3 = new Order1(3, "15760939122", 1, "B-2号", "退款/售后", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 10f);
            RecentlyorderList.add(order3);
            Order1 order4 = new Order1(4, "15760939122", 1, "A-21号", "待使用", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 20f);
            RecentlyorderList.add(order4);
            Order1 order5 = new Order1(5, "15760939122", 1, "20号", "待付款", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 300f);
            RecentlyorderList.add(order5);
            Order1 order6 = new Order1(6, "15760939122", 1, "20号", "已完成", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 300f);
            RecentlyorderList.add(order6);

        }
        for (Order1 order1 : RecentlyorderList) {
            order1.save();
        }
    }

//        Order1 order=new Order1();
//        order.setOrderId(6);
//        order.setIphoneNumber("15760939122");
//        order.setParkingId(3);
//        order.setParkingLot("A-2号");
//        order.setOrderState("待使用");
//        order.setStart_parking_datetime("2017-10-14 23:11:58");
//        order.setEnd_parking_datetime("2017-10-15 6:00:00");
//        order.setPay(1.5f);
//        order.save();
//        Order1 order1=new Order1();
//        order1.setOrderId(8);
//        order1.setIphoneNumber("15760939122");
//        order1.setParkingId(1);
//        order1.setParkingLot("B-3号");
//        order1.setOrderState("已完成");
//        order1.setStart_parking_datetime("2017-10-14 23:11:58");
//        order1.setEnd_parking_datetime("2017-10-15 6:00:00");
//        order1.setPay(1.2f);
//        order1.save();
//        RecentlyorderList=DataSupport.findAll(Order1.class);

//        RecentlyorderList=DataSupport.limit(5).find(Order1.class);
//        for (Order1 o:RecentlyorderList){
//            Log.i("OrderState:",o.getParkingLot());
//            Log.i("OrderState:",o.getOrderState());
//        }
//        Toast.makeText(getActivity(),"size="+RecentlyorderList.size(),Toast.LENGTH_LONG).show();
//        orderAdapter.setData(RecentlyorderList);
//        orderAdapter.notifyDataSetChanged();
//        String AllOrderUrl="http://192.168.1.133:8080/park/check/getLaestCheck?phonenumber=" ;
//        String username="18209183861";
//        String AllOrderAddress=AllOrderUrl+username;
//       queryOrder_recently(AllOrderAddress);
    private void selectRecentlyOrder(){
        RecentlyorderList=DataSupport.limit(6).find(Order1.class);
        orderAdapter.setData(RecentlyorderList);
        orderAdapter.notifyDataSetChanged();

    }
    private void queryFromServer(final String address, final String type1){
        DataSupport.deleteAll(Order1.class);
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeProgressDialog();
                Toast.makeText(MyApplication.getContext(),"加载失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                boolean result=false;
                if (type1.equals("全部")) {
                    result = Utility.handleOrdersResponse(responseText);
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (type1.equals("全部")){
                                queryOrder_recently(address);
                            }
                        }
                    });
                }
            }
        });
    }


    private void queryOrder_recently(String addressOrder) {
//        DataSupport.deleteAll(Order1.class);
        RecentlyorderList= DataSupport.findAll(Order1.class);
        if (RecentlyorderList.size() > 0) {
            orderAdapter.setData(RecentlyorderList);
            orderAdapter.notifyDataSetChanged();
        } else {
            queryFromServer(addressOrder,"全部");
        }
    }
}