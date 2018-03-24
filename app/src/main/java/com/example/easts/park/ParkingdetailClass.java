package com.example.easts.park;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easts.park.db.Parking;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/11/9.
 */

public class ParkingdetailClass extends AppCompatActivity {
    private Context mContent;
    private static final int to_Order=1;
    private ImageView backtoPaking;
    private ImageView parking_image2;
    private TextView parking_name2;
    private TextView parking_locate;
    private ImageView iphone_IV;
    private TextView freight_basis2;
    private Button toOrder;
    private ImageView tolocate;
    private Parking p;
    private String addressGetParkingLots="http://192.168.1.132:8080/park/parkinglot/searchAllStatus";
    private String strP="";//车位信息
    public  static  final int UPDATE_PARKINGIN=1;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case UPDATE_PARKINGIN:
                        strP=msg.obj.toString();
                        Log.d("strp111",strP);
                        Intent intent=new Intent(ParkingdetailClass.this,SelectParkingSpace.class);
                        intent.putExtra("parkingId",Integer.parseInt(p.getParkingId()));
                        Log.d("In111",strP);
                        intent.putExtra("parkingIn",strP);
                        startActivityForResult(intent,1);
                        break;
                    default:
                        break;

                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_lot_details);
        backtoPaking=(ImageView)findViewById(R.id.backtoPaking) ;
        parking_image2=(ImageView) findViewById(R.id.parking_image2);
        parking_name2=(TextView)findViewById(R.id.parking_name2);
        parking_locate=(TextView)findViewById(R.id.parking_locate_pld);
        iphone_IV=(ImageView)findViewById(R.id.iphone_IV);
        freight_basis2=(TextView)findViewById(R.id.freight_basis2);
        toOrder=(Button)findViewById(R.id.toOrder);
        tolocate=(ImageView)findViewById(R.id.tolocate);
        //获得传来的对象
        p=(Parking)getIntent().getSerializableExtra("Parking");
        Toast.makeText(this,p.getParking_name()+p.getParking_location(),Toast.LENGTH_LONG).show();
////        改变信息##图片加载有问题
////        parking_image2.setImageResource(R.drawable.parking_image3);
//        Glide.with(this).load(R.drawable.parking_image3).into(parking_image2);
//
        parking_name2.setText(p.getParking_name());
        parking_locate.setText(p.getParking_location());
        freight_basis2.setText(String.valueOf(p.getFreight_basis()));
        //返回查询停车场界面


        backtoPaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /***
                 * 重要代码 从一个activity到一个指定的Fragement且带参
                 */
                Intent i=new Intent(v.getContext(),MainActivity.class);
                i.putExtra("toshowF",1);
                v.getContext().startActivity(i);
                //第一个参数为启动时动画效果，第二个参数为退出时动画效果
                overridePendingTransition(R.anim.back,R.anim.back1);

            }
        });
        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle_ParkingLotsInformation();

            }
        });

        tolocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void handle_ParkingLotsInformation() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    OkHttpClient okhttp3=new OkHttpClient();
//                    Log.d("parkingId",p.getParkingId());
//                    RequestBody rB=new FormBody.Builder().add("parking_id",p.getParkingId()).build();
//
//                    Request r=new Request.Builder().url(addressGetParkingLots).post(rB).build();
//                    Response response=okhttp3.newCall(r).execute();

//                    String responseData=response.body().string();
                    String parkingLotIn="[{\"space_location\":\"A2\",\"space_status\":0},{\"space_location\":\"A3\",\"space_status\":0},{\"space_location\":\"A4\",\"space_status\":0},{\"space_location\":\"A6\",\"space_status\":0},{\"space_location\":\"A7\",\"space_status\":0},{\"space_location\":\"A8\",\"space_status\":1},{\"space_location\":\"A10\",\"space_status\":2},{\"space_location\":\"A11\",\"space_status\":0}]";
                    String responseData=parkingLotIn;
                    Message message=new Message();
                    message.what=UPDATE_PARKINGIN;
                    message.obj=responseData;
                    handler.sendMessage(message);
                    Log.d("11111",responseData);//5个1
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case to_Order:
//                if(resultCode==0){
//
//                }
//        }
//    }

}
