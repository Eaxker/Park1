package com.example.easts.park;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easts.park.Fragement.Me;
import com.example.easts.park.Fragement.Orders;
import com.example.easts.park.Fragement.Park_orderLot;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

private Orders park;
private Park_orderLot orderLot;
private Me me;

private View messageLayout;
private View contactLayout;
private View settingLayout;

private ImageView messageImage;
private ImageView contactImage;
private ImageView settingImage;

private TextView messageText;
private TextView contactText;
private TextView settingText;
private String  f;

private android.app.FragmentManager fragmentManager;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        fragmentManager=getFragmentManager();
        setTabSelection(1);
        if (getIntent().getStringExtra("toshowF")!=null){
                if (getIntent().getStringExtra("toshowF").equals("1")){
                        setTabSelection(1);
                }
                else if (getIntent().getStringExtra("toshowF").equals("2")){
                        setTabSelection(2);
                }else if (getIntent().getStringExtra("toshowF").equals("3")){
                        setTabSelection(3);
                }
        }
        }

private void initViews(){
        messageLayout = findViewById(R.id.message_layout);
        contactLayout = findViewById(R.id.contact_layout);
        settingLayout = findViewById(R.id.zhushang_layout);

        messageImage = (ImageView)findViewById(R.id.message_image);
        contactImage = (ImageView)findViewById(R.id.contact_image);
        settingImage = (ImageView)findViewById(R.id.zhushang_image);

        messageText = (TextView)findViewById(R.id.message_text);
        contactText = (TextView)findViewById(R.id.contact_text);
        settingText = (TextView)findViewById(R.id.zhushang_text);

        messageLayout.setOnClickListener(this);
        contactLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        }

@Override
public void onClick(View view) {
        switch (view.getId()){
        case R.id.message_layout:
        setTabSelection(1);
        break;
        case R.id.contact_layout:
        setTabSelection(2);
        break;
        case R.id.zhushang_layout:
        setTabSelection(3);
        break;
default:
        break;
        }

        }

private void setTabSelection(int index){
        //选中之前先清楚掉之前的状态
        clearSelection();

        //开启一个Fragment事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);

        switch(index){
        case 1:
        messageImage.setImageResource(R.drawable.order32);
        messageText.setTextColor(getResources().getColor(R.color.blue1));
        if(orderLot == null){
        orderLot = new Park_orderLot();
        fragmentTransaction.add(R.id.content, orderLot);
        }else{
        fragmentTransaction.show(orderLot);
        }
        fragmentTransaction.commit();
        break;
        case 2:
        contactImage.setImageResource(R.drawable.park1);
        contactText.setTextColor(getResources().getColor(R.color.blue1));
        if(park == null){
        park = new Orders();
        fragmentTransaction.add(R.id.content, park);
        }else{
        fragmentTransaction.show(park);
        }
        fragmentTransaction.commit();
        break;

        case 3:
        settingImage.setImageResource(R.drawable.me1);
        settingText.setTextColor(getResources().getColor(R.color.blue1));
        if(me== null){
        me = new Me();
        fragmentTransaction.add(R.id.content,me);
        }else{
        fragmentTransaction.show(me);
        }
        fragmentTransaction.commit();
        break;
default:
        break;
        }
        }

private void clearSelection(){
        messageImage.setImageResource(R.drawable.order0);
        messageText.setTextColor(Color.BLACK);
        contactImage.setImageResource(R.drawable.park0);
        contactText.setTextColor(Color.BLACK);
        settingImage.setImageResource(R.drawable.me0);
        settingText.setTextColor(Color.BLACK);
        }

private void hideFragments(FragmentTransaction fragmentTransaction){
        if(park != null){
        fragmentTransaction.hide(park);
        }
        if(orderLot != null){
        fragmentTransaction.hide(orderLot);
        }

        if(me != null){
        fragmentTransaction.hide(me);
        }
        }
        }