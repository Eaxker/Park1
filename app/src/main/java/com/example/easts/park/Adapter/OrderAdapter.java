package com.example.easts.park.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easts.park.ParkingSuccess;
import com.example.easts.park.R;
import com.example.easts.park.db.Order1;

import java.util.List;


/**
 * Created by lenovo on 2017/11/8.
 *查看订单界面 订单适配器
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private final int UNUSED = 0;
    private final int UNPAYED = 1;
    private final int COMPLETED = 2;
    private final int BACK_SERVER_MONEY = 3;//数据库中存的是String
    private List<Order1> mOrderList;

    public OrderAdapter(List<Order1> orderList) {
        this.mOrderList=orderList;
    }

    /***
     * 按要求返回item    
     * @param position
     * @return
     */
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//
//            }
//        }
//    }
    @Override
    public int getItemViewType(int position) {
        if (mOrderList.get(position).getOrderState().equals("待使用")) {
            return UNUSED;
        } else if (mOrderList.get(position).getOrderState().equals("待付款")) {
            return UNPAYED;
        } else if (mOrderList.get(position).getOrderState().equals("退款/售后")) {
            return BACK_SERVER_MONEY;
        } else if (mOrderList.get(position).getOrderState().equals("已完成")) {
            return COMPLETED;
        }
        return 4;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        if (viewType == COMPLETED) {
            Log.i("111","已完成加载。。。");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_completed_unassess_item, null, false);
            final Completed_ViewHolder holder = new Completed_ViewHolder(view);
            holder.to_assess_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "去评价！", Toast.LENGTH_LONG).show();//这遇到问题
                    int position = holder.getAdapterPosition();
                    Order1 order = mOrderList.get(position);
                    //转去评价页面
//                    Intent intent=new Intent(v.getContext(), ParkingdetailClass.class);
//                    intent.putExtra("ParkingId",order.getParkingId());
                    //通过parkingId得到一个parking对象本地实现或者网络请求
//                    v.getContext().startActivity(intent);
                }
            });
            holder.to_order_again_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "再去订！", Toast.LENGTH_LONG).show();
                    int position = holder.getAdapterPosition();
                    Order1 order = mOrderList.get(position);
//                    Intent intent = new Intent(v.getContext(), ParkingdetailClass.class);
                    int parkingId = order.getParkingId();
                    //通过parkingId得到一个parking对象本地实现或者网络请求
//                    intent.putExtra("Parking",p);
//                    v.getContext().startActivity(intent);
                }
            });
            /***
             * 特别重要的代码
             */
//            holder..setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = holder.getAdapterPosition();
//                    Order order = mOrderList.get(position);
//                    Toast.makeText(v.getContext(), "去做！", Toast.LENGTH_LONG).show();
////                Intent intent=new Intent(v.getContext(), ParkingdetailClass.class);
////                v.getContext().startActivity(intent);
//
//                }
//            });
            return holder;
        } else if (viewType == UNUSED) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_unused_item, parent, false);
            final Unused_ViewHolder holder = new Unused_ViewHolder(view);

            holder.cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final int position = holder.getAdapterPosition();
                    final Order1 order = mOrderList.get(position);
                    Toast.makeText(v.getContext(),"取消订单！车位号为："+order.getParkingLot(),Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

//                                OkHttpClient okHttpClient = new OkHttpClient();
//                                RequestBody requestBody = new FormBody.Builder().add("orderId", String.valueOf(order.getOrderId()))
//                                        .add("oderState", order.getOrderState()).
//                                                add("username", order.getIphoneNumber()).add("todo", "cancel").build();
//                                Request request = new Request.Builder().url("http://").post(requestBody).build();
//                                Response response = okHttpClient.newCall(request).execute();
//                                String responseData = response.body().string();

                                String responseData="true";
                                if (responseData.equals("true")){
                                    //刷新界面
//                                    mOrderList=DataSupport.where("orderState= ?","待使用").find(Order1.class);
                                   //传送数据给服务器
                                    Log.i("888","线程内");
                                    Intent intent=new Intent(mContext, ParkingSuccess.class);
                                    intent.putExtra("Information","取消订单成功！");
                                    v.getContext().startActivity(intent);
                                }else {
                                    Toast.makeText(mContext,"服务器错误请重试!",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Log.i("888","线程外");

                }
            });
            holder.to_use.setOnClickListener(new View.OnClickListener() {
                @Override
                //停车场id、用户手机号、车位id  A1
                public void onClick(final View v) {

                    int position =holder.getAdapterPosition();
                    final  Order1 order=mOrderList.get(position);
                    Toast.makeText(v.getContext(),"去停车！车位号为："+order.getParkingLot(),Toast.LENGTH_SHORT).show();
//
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                OkHttpClient okHttpClient = new OkHttpClient();
//                                RequestBody requestBody = new FormBody.Builder().add("parking_id","1" )
//                                        .add("space_location","A11" ).
//                                                add("phonenumber","18829076351").build();
//                                Request request = new Request.Builder().url("http://192.168.1.127:8080/park/carspace/parkCarspace").post(requestBody).build();
//                                Response response = okHttpClient.newCall(request).execute();
//                                String responseData = response.body().string();
//                                Log.i("99999","线程内");
                                String responseData="success";
                                Log.i("99999",String.valueOf(responseData.equals("success")));
                                if (responseData.equals("success")){
                                    //传送数据给服务器
                                    Log.i("99999",responseData);
                                    Intent intent=new Intent(v.getContext(), ParkingSuccess.class);
                                    intent.putExtra("Information","地锁下降成功!可以停车.......");
                                    v.getContext().startActivity(intent);
                                }else {
                                    Toast.makeText(v.getContext(),"服务器错误请重试!",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Log.i("999","线程外");

                    //给数据库中返回停车成功信息

                }
            });
            return holder;
        } else if (viewType == UNPAYED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_unpayed_item, parent, false);
            final Unpayed_ViewHolder holder = new Unpayed_ViewHolder(view);

            holder.to_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Toast.makeText(mContext, "去付款.....", Toast.LENGTH_LONG).show();
                    int position = holder.getAdapterPosition();
                    Order1 order = mOrderList.get(position);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                OkHttpClient okHttpClient = new OkHttpClient();
//                                RequestBody requestBody = new FormBody.Builder().add("parkingId","" )
//                                        .add("spacelocation","").
//                                                add("username", "").build();
//                                Request request = new Request.Builder().url("http://").post(requestBody).build();
//                                Response response = okHttpClient.newCall(request).execute();
//                                String responseData = response.body().string();

                                  String responseData="success";

                                if (responseData.equals("success")){
                                    //传送数据给服务器
                                    Log.i("999","线程内");
                                    Intent intent=new Intent(v.getContext(), ParkingSuccess.class);
                                    intent.putExtra("Information","付款成功！欢迎下次再来~");
                                    v.getContext().startActivity(intent);
                                }else {
                                    Toast.makeText(v.getContext(),"服务器错误请重试!",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
            });
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_back_server_money, parent,false );
            final Back_server_money_ViewHolder holder = new Back_server_money_ViewHolder(view);
            holder.look_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "查看详情", Toast.LENGTH_LONG).show();
                    int position = holder.getAdapterPosition();
                    Order1 order = mOrderList.get(position);
                }
            });
            return holder;
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Order1 order = mOrderList.get(position);
        if (viewHolder instanceof Completed_ViewHolder) {

            /***
             * load()参数可以是urL地址or本地路径or资源id
             */
            Glide.with(mContext).load(R.drawable.parking_image).into(((Completed_ViewHolder) viewHolder).parking_image);//写死
            ((Completed_ViewHolder) viewHolder).parking_lot.setText(order.getParkingLot());
            ((Completed_ViewHolder) viewHolder).parking_name.setText("万达停车场");//写死
            ((Completed_ViewHolder) viewHolder).orderState.setText(order.getOrderState());
            ((Completed_ViewHolder) viewHolder).start_parking_datatime.setText(order.getStart_parking_datetime());
            ((Completed_ViewHolder) viewHolder).end_parking_datetime.setText(order.getEnd_parking_datetime());
            ((Completed_ViewHolder) viewHolder).pay.setText(String.valueOf(order.getPay()));
        } else if (viewHolder instanceof Unused_ViewHolder) {
//

            Glide.with(mContext).load(R.drawable.parking_image).into(((Unused_ViewHolder) viewHolder).parking_image);
            ((Unused_ViewHolder) viewHolder).parking_lot.setText(order.getParkingLot());
            ((Unused_ViewHolder) viewHolder).parking_name.setText(order.getParkingLot());
            ((Unused_ViewHolder) viewHolder).parking_name.setText("万达停车场");
            ((Unused_ViewHolder) viewHolder).orderState.setText(order.getOrderState());
            ((Unused_ViewHolder) viewHolder).start_parking_datatime.setText(order.getStart_parking_datetime());
            ((Unused_ViewHolder) viewHolder).pay.setText(String.valueOf(order.getPay()));
        } else if (viewHolder instanceof Unpayed_ViewHolder) {
//
            Glide.with(mContext).load(R.drawable.parking_image).into(((Unpayed_ViewHolder) viewHolder).parking_image);
            ((Unpayed_ViewHolder) viewHolder).parking_lot.setText(order.getParkingLot());
//            ((Unpayed_ViewHolder) viewHolder).parking_name.setText(order.getParking_name());
            ((Unpayed_ViewHolder) viewHolder).parking_name.setText("万达停车场");
            ((Unpayed_ViewHolder) viewHolder).orderState.setText(order.getOrderState());

            ((Unpayed_ViewHolder) viewHolder).start_parking_datatime.setText(order.getStart_parking_datetime());
            ((Unpayed_ViewHolder) viewHolder).pay.setText(String.valueOf(order.getPay()));
        } else if (viewHolder instanceof Back_server_money_ViewHolder){
//
            Glide.with(mContext).load(R.drawable.parking_image).into(((Back_server_money_ViewHolder) viewHolder).parking_image);
            ((Back_server_money_ViewHolder) viewHolder).parking_lot.setText(order.getParkingLot());
//            ((Back_server_money_ViewHolder) viewHolder).parking_name.setText(order.getParking_name());
            ((Back_server_money_ViewHolder) viewHolder).parking_name.setText("万达停车场");
            ((Back_server_money_ViewHolder) viewHolder).orderState.setText(order.getOrderState());
            ((Back_server_money_ViewHolder) viewHolder).start_parking_datatime.setText(order.getStart_parking_datetime());
            ((Back_server_money_ViewHolder) viewHolder).pay.setText(String.valueOf(order.getPay()));
        }
    }

        @Override
        public int getItemCount () {
            return mOrderList.size();
        }

        public class Completed_ViewHolder extends RecyclerView.ViewHolder {
            View orderView;
            ImageView parking_image;
            TextView parking_lot;
            TextView parking_name;
            TextView orderState;
            TextView start_parking_datatime;
            TextView end_parking_datetime;
            TextView pay;
            Button to_assess_BT;
            public Button to_order_again_BT;

            public Completed_ViewHolder(View view) {
                super(view);
                orderView = view;
                parking_image = (ImageView) view.findViewById(R.id.parking_image1_order);
                parking_lot = (TextView) view.findViewById(R.id.parking_lot);
                parking_name = (TextView) view.findViewById(R.id.parking_name);
                orderState = (TextView) view.findViewById(R.id.order_state);
                start_parking_datatime = (TextView) view.findViewById(R.id.start_parking_datetime);
                end_parking_datetime = (TextView) view.findViewById(R.id.end_parking_datetime);
                pay = (TextView) view.findViewById(R.id.pay);
                to_assess_BT = (Button) view.findViewById(R.id.to_assess_BT);
                to_order_again_BT = (Button) view.findViewById(R.id.to_order_again_BT);
            }
        }

        public class Unused_ViewHolder extends RecyclerView.ViewHolder {
            View orderView;
            ImageView parking_image;
            TextView parking_lot;
            TextView parking_name;
            TextView orderState;
            TextView start_parking_datatime;
            TextView pay;
            Button cancel_order;
            Button to_use;


            public Unused_ViewHolder(View view) {
                super(view);
                orderView = view;
                parking_image = (ImageView) view.findViewById(R.id.parking_image1_order);
                parking_lot = (TextView) view.findViewById(R.id.parking_lot);
                parking_name = (TextView) view.findViewById(R.id.parking_name);
                orderState = (TextView) view.findViewById(R.id.order_state);
                start_parking_datatime = (TextView) view.findViewById(R.id.start_parking_datetime);
                pay = (TextView) view.findViewById(R.id.pay);
                cancel_order = (Button) view.findViewById(R.id.cancel_order);
                to_use = (Button) view.findViewById(R.id.to_use);
            }
        }

        public class Unpayed_ViewHolder extends RecyclerView.ViewHolder {
            View orderView;
            ImageView parking_image;
            TextView parking_lot;
            TextView parking_name;
            TextView orderState;
            TextView start_parking_datatime;
            TextView end_parking_datetime;
            TextView pay;
            Button to_pay;

            public Unpayed_ViewHolder(View view) {
                super(view);
                orderView = view;
                parking_image = (ImageView) view.findViewById(R.id.parking_image1_order);
                parking_lot = (TextView) view.findViewById(R.id.parking_lot);
                parking_name = (TextView) view.findViewById(R.id.parking_name);
                orderState = (TextView) view.findViewById(R.id.order_state);
                start_parking_datatime = (TextView) view.findViewById(R.id.start_parking_datetime);
                end_parking_datetime = (TextView) view.findViewById(R.id.end_parking_datetime);
                pay = (TextView) view.findViewById(R.id.pay);
                to_pay = (Button) view.findViewById(R.id.to_pay);

            }

        }
        public class Back_server_money_ViewHolder extends RecyclerView.ViewHolder {
            View orderView;
            ImageView parking_image;
            TextView parking_lot;
            TextView parking_name;
            TextView orderState;
            TextView start_parking_datatime;

            TextView pay;
            Button look_details;

            public Back_server_money_ViewHolder(View view) {
                super(view);
                orderView = view;
                parking_image = (ImageView) view.findViewById(R.id.parking_image1_order);
                parking_lot = (TextView) view.findViewById(R.id.parking_lot);
                parking_name = (TextView) view.findViewById(R.id.parking_name);
                orderState = (TextView) view.findViewById(R.id.order_state);
                start_parking_datatime = (TextView) view.findViewById(R.id.start_parking_datetime);
                pay = (TextView) view.findViewById(R.id.pay);
                look_details =(Button)view.findViewById(R.id.to_look_details_order);

            }
        }
        public void setData(List<Order1> newOrder1List){
            this.mOrderList=newOrder1List;
        }
    }

