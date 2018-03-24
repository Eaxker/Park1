package com.example.easts.park.Fragement;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easts.park.Adapter.OrderAdapter;
import com.example.easts.park.Httptool.HttpUtil;
import com.example.easts.park.MyApplication;
import com.example.easts.park.R;
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
 * Created by lenovo on 2017/11/7.
 */

public class OrderConcrete extends Fragment {
    private Context mcontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private String mPage;
    private ProgressDialog progressDialog;
    private OrderAdapter orderAdapter;
    private List<Order1> ordersList = new ArrayList<>();//全部订单
    private List<Order1> order_unusedList = new ArrayList<>();//待使用订单
    private List<Order1> order_unpayedList = new ArrayList<>();//待付款订单
    private List<Order1> order_unassessedList = new ArrayList<>();//已完成
    private List<Order1> order_back_server_money = new ArrayList<>();//退款售后
    RecyclerView recyclerView_orderConcrete;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getString(ARG_PAGE);

    }

    /***
     * 用缓存保存订单还是用数据库
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.orders, container, false);
        recyclerView_orderConcrete = (RecyclerView) view.findViewById(R.id.recyclerView_orders);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_orderConcrete.setLayoutManager(linearLayoutManager);
        initOrders();
        switch (mPage) {
            case "全部":
                orderAdapter = new OrderAdapter(ordersList);
                break;
            case "待使用":
                orderAdapter = new OrderAdapter(order_unusedList);
                break;
            case "待付款":
                orderAdapter = new OrderAdapter(order_unpayedList);
                break;
            case "退款/售后":
                orderAdapter = new OrderAdapter(order_back_server_money);
                break;
            case "待评价":
                orderAdapter = new OrderAdapter(order_unassessedList);
                break;
            default:
                break;
        }
        recyclerView_orderConcrete.setAdapter(orderAdapter);


        return view;

    }

    public static OrderConcrete newInstance(String page) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, page);
        OrderConcrete fragment = new OrderConcrete();
        fragment.setArguments(args);
        return fragment;
    }

    private void initOrders() {
//        for (int i = 0; i < 5; i++) {
//            Order order1 = new Order(1,"15760939122",1,"A-21号", "已完成", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 15f);
//            ordersList.add(order1);
//            Order order2 = new Order(2,"15760939122",1,"C-50号", "已完成", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 10f);
//            ordersList.add(order2);
//            Order order3 = new Order(3, "15760939122",1,"B-2号",  "已完成", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 10f);
//            ordersList.add(order3);
//            Order order4 = new Order(4,"15760939122",1,"A-21号",  "待使用", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 20f);
//            ordersList.add(order4);
//            Order order5 = new Order(5, "15760939122",1,"20号", "待付款", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 300f);
//            ordersList.add(order5);
//            Order order6 = new Order(6, "15760939122",1, "20号",  "退款/售后", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 300f);
//            ordersList.add(order6);
//            Order order7 = new Order(7, "15760939122",1, "20号",  "退款/售后", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 300f);
//            ordersList.add(order7);
//            Order order8 = new Order(8, "15760939122",1, "20号",  "待付款", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 300f);
//            ordersList.add(order8);
//            Order order9 = new Order(9, "15760939122",1, "20号",  "已完成", "2017-10-14 23:11:58", "2017-10-15 6:00:00", 300f);
//            ordersList.add(order9);
//
//        }
//        ordersList.clear();
//        queryOrder_All();
        ordersList= DataSupport.findAll(Order1.class);
        for (int i=0;i<ordersList.size();i++){
                Order1 order=ordersList.get(i);
                String os=order.getOrderState();
            if (os.equals("已完成")){
                order_unassessedList.add(order);
            }else if (os.equals("待使用")){
                order_unusedList.add(order);
            }else if (os.equals("待付款")){
                order_unpayedList.add(order);
            }else if ((os.equals("退款/售后"))){
                order_back_server_money.add(order);
            }
        }
    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载....");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询订单数据
     *
     */
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(MyApplication.getContext(),"加载失败",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                boolean result=false;
                if (type.equals("全部")){
                    result= Utility.handleOrdersResponse(responseText);
                }else if (type.equals("待使用")){
                    result=Utility.handleOrdersResponse(responseText);
                }else if (type.equals("待付款")){
                    result=Utility.handleOrdersResponse(responseText);
                }else if (type.equals("待评价")){
                    result=Utility.handleOrdersResponse(responseText);
                }else if(type.equals("退款/售后")){
                    result=Utility.handleOrdersResponse(responseText);
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (type.equals("全部")){
                                queryOrder_All();
                            }else if (type.equals("待使用")){
                                queryOrder_Unuse();
                            }else if (type.equals("待付款")){
                                queryOrder_Unpay();
                            }else if (type.equals("待评价")){
                                queryOrder_Unassess();
                            }else if (type.equals("退款/售后")){
                                querOrder_BackMoneyServer();
                            }
                        }


                    });
                }
            }


        });
    }
    private void queryOrder_All() {
        ordersList= DataSupport.findAll(Order1.class);
        if (ordersList.size()>0){
            orderAdapter.setData(ordersList);
            orderAdapter.notifyDataSetChanged();
        }else {
            String addressAll="http";
            queryFromServer(addressAll,"全部");
        }

    }
    private void queryOrder_Unuse(){
        order_unusedList=DataSupport.where("orderState=?","待使用").find(Order1.class);
        if (order_unusedList.size()>0){
            orderAdapter.setData(order_unassessedList);
            orderAdapter.notifyDataSetChanged();
        }else {
            String addressUnuse="http";
            queryFromServer(addressUnuse,"待使用");
        }
    }
    private void queryOrder_Unpay(){
        order_unusedList=DataSupport.where("orderState=?","待付款").find(Order1.class);
        if (order_unpayedList.size()>0){
            orderAdapter.setData(order_unpayedList);
            orderAdapter.notifyDataSetChanged();
        }else {
            String addressUnpay = "http";
            queryFromServer(addressUnpay, "待付款");
        }
    }
    private void queryOrder_Unassess(){
        order_unusedList=DataSupport.where("orderState=?","已完成").find(Order1.class);
        if (order_unassessedList.size()>0){
            orderAdapter.setData(order_unassessedList);
            orderAdapter.notifyDataSetChanged();
        }else {
            String addressUnassess = "http:";
            queryFromServer(addressUnassess, "待评价");
        }
    }
    private void querOrder_BackMoneyServer(){
        order_back_server_money=DataSupport.where("orderState=?","退款/售后").find(Order1.class);
        if(order_back_server_money.size()>0){
            orderAdapter.setData(order_back_server_money);
            orderAdapter.notifyDataSetChanged();
        }else {
            String addressBackmoneyServer = "http";
            queryFromServer(addressBackmoneyServer, "退款/售后");
        }
    }
}

