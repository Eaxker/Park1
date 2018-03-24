package com.example.easts.park;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/11/20.
 */

public class Takesuccess extends AppCompatActivity {
    private ImageView take_back;
    private TextView parkinglot;
    private TextView pay;
    private TextView start;
    private TextView end;
    private Button to_pay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takesuccess);
//        OrderParked orderParked=(OrderParked)getIntent().getSerializableExtra("orderParked");
        pay=(TextView)findViewById(R.id.pay_t);
        start=(TextView)findViewById(R.id.start_parking_datetime_t);
        end=(TextView)findViewById(R.id.end_parking_datetime_t);
        parkinglot=(TextView)findViewById(R.id.parking_lot_t);
        take_back=(ImageView)findViewById(R.id.back_order_from_take);
        to_pay=(Button)findViewById(R.id.to_pay_t) ;

        take_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        pay.setText(String.valueOf(orderParked.getCheckmoney()));
//        start.setText(orderParked.getStart_time());
//        end.setText(orderParked.getOver_time());
//        parkinglot.setText(orderParked.getSpace_location());

        to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Takesuccess.this,ParkingSuccess.class);
                intent.putExtra("Information","付款成功！欢迎下次再来~");
                startActivity(intent);
            }
        });
    }
}
