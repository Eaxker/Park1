package com.example.easts.park;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.easts.park.Fragement.OrderConcrete;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/11/7.
 * 详细订单布局
 */

public class TabLayoutActivity extends FragmentActivity {

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private ImageView back_park;
    private int selected_OrderState;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paking_tablayout);
        selected_OrderState=getIntent().getIntExtra("OrderState",-1);


        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);
        back_park=(ImageView)findViewById(R.id.back_parking_lot_details);
        back_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                setResult(0,i);
                finish();
                //第一个参数为启动时动画效果，第二个参数为退出时动画效果
                overridePendingTransition(R.anim.back,R.anim.back1);
            }
        });
        initContent();
        initTab();
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.gray), ContextCompat.getColor(this, R.color.white));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        ViewCompat.setElevation(mTabTl,10);
//        TabLayout.getTabAt(position).select();
        mTabTl.setupWithViewPager(mContentVp);
        if (selected_OrderState != -1) {
            mTabTl.getTabAt(selected_OrderState).select();
        }
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();
        tabIndicators.add("全部");
        tabIndicators.add("待使用");
        tabIndicators.add("待付款");
        tabIndicators.add("待评价");
        tabIndicators.add("退款/售后");
        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(OrderConcrete.newInstance(s));
        }

        /**
         * 首先Fragment是3.0之后的东西，3.0之后获取FragmentManager使用的方法是getFragmentManager() ，3.0之前压根没有Fragment。但是google提供了3.0之前也能用Fragment的功能，那就是使用android-support-v4.jar兼容包，同时
         * 这时候继承就不能直接继承Activity，而要继承FragmentActivity
         */
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }

         class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }


}

