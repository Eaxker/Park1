package com.example.easts.park;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/11/19.
 */

public class ParkingSuccess extends Activity {
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkingsuccess);
        imageView=(ImageView)findViewById(R.id.back_order);
        textView=(TextView) findViewById(R.id.information);
        String information=getIntent().getStringExtra("Information");

        textView.setText(information);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }
}
