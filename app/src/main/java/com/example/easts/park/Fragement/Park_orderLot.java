package com.example.easts.park.Fragement;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easts.park.Adapter.ParkingAdapter;
import com.example.easts.park.Httptool.HttpUtil;
import com.example.easts.park.MyApplication;
import com.example.easts.park.R;
import com.example.easts.park.Utility.Utility;
import com.example.easts.park.db.Order1;
import com.example.easts.park.db.Parking;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/10/31.
 * 查找合适的停车场，生产订单界面
 */

public class Park_orderLot extends Fragment {
//    private String[] mStrs = {"aaa", "bbb", "ccc", "airsaid"};
//    private ListView mListView;
    View view;
    Spinner spinnerCity;
    Spinner spinnerArea;
    Spinner spinnerTerm;
    SearchView searchView;
    String addressParking="http://192.168.1.132:8080/park/parkinglot/getParkinglotInfo";
    private LinearLayout changeView;
    private ParkingAdapter parkingAdapter;
    private ImageView locate;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private List<Parking> parkingList =new ArrayList<>();

    private Parking[] parks={
            new Parking("1","万达停车场","西北政法大学","80",3.0f,1.0f,1.0f,200,100,900),
            new Parking("2","快捷停车场","西安邮电大学","80",3.0f,1.0f,1.0f,200,200,900),
            new Parking("3","万达停车场","西北政法大学","80",3.0f,1.0f,1.0f,200,300,900),
            new Parking("4","佳美家停车场","西安邮电大学","80",3.0f,1.0f,1.0f,200,400,900),
            new Parking("5","万达停车场","西北政法大学","80",3.0f,1.0f,1.0f,200,500,900),
            new Parking("6","快捷停车场","北京大学","80",3.0f,1.0f,1.0f,200,600,900),
            new Parking("7","高空停车场","西安邮电大学","80",3.0f,1.0f,1.0f,200,700,900),
            new Parking("8","快捷停车场","清华大学","80",3.0f,1.0f,1.0f,200,800,900),
            new Parking("9","万达停车场","西安邮电大学","80",3.0f,1.0f,1.0f,200,900,900),
            new Parking("10","快捷停车场","西安交通大学","80",3.0f,1.0f,1.0f,200,1000,900),
            new Parking("11","佳美家停车场","西安邮电大学","80",3.0f,1.0f,1.0f,200,1100,900),
            new Parking("12","万达停车场","复旦大学","80",3.0f,1.0f,1.0f,200,1200,900),
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.orderlot,container,false);
        spinnerCity = (Spinner)view.findViewById(R.id.spinnerCity);
        spinnerArea=(Spinner) view.findViewById(R.id.spinnerArea);
        spinnerTerm=(Spinner)view.findViewById(R.id.spinnerTerm);
        locate=(ImageView) view.findViewById(R.id.locate);
//        changeView=(LinearLayout)view.findViewById(R.id.changeView) ;
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_parking);
        recyclerView=(RecyclerView)view.findViewById(R.id.parks_recyclerView);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        parkingList=DataSupport.limit(10).find(Parking.class);
        parkingAdapter=new ParkingAdapter(parkingList);
        recyclerView.setAdapter(parkingAdapter);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);

        /**
         * 通常可以将SearchView和ListView结合，实现数据的搜索和过滤。
         1.监听SearchView，SearchView.setOnQueryTextListener(OnQueryTextListener listener)；
         2.开启ListView的过滤功能，listView.setTextFilterEnabled(true)。必须开启，否则不会过滤；
         3..当SearchView接收到输入事件后，调用ListView.setFilterText(filterText)方法，该方法会通过Adapter得到Filter，然后调用Filter.filter(filterText)：
         */
//        mListView = (ListView) view.findViewById(R.id.listView);
//        mListView.setAdapter(new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_list_item_1, mStrs));
//        mListView.setTextFilterEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshParkings();
                Toast.makeText(view.getContext(),"刷新成功！",Toast.LENGTH_LONG).show();
            }
        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            // 当点击搜索按钮时触发该方法
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                changeView.setVisibility(View.VISIBLE);
////                mListView.setVisibility(View.GONE);
//                Toast.makeText(MyApplication.getContext(),"获取网络数据成功",Toast.LENGTH_LONG).show();
//                refreshParkingsBy(query);
//                return false;
//            }
//
//            // 当搜索内容改变时触发该方法
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                changeView.setVisibility(View.GONE);
////                mListView.setVisibility(View.VISIBLE);
//                if (!TextUtils.isEmpty(newText)){
//                    mListView.setFilterText(newText);
//                }else{
//                    mListView.clearTextFilter();
//                }
//                return false;
//            }
//        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.City);
                Toast.makeText(getActivity(), "你点击的是:"+languages[pos], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.Area);
                Toast.makeText(getActivity(), "你点击的是:"+languages[pos], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinnerTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.Term);
                Toast.makeText(getActivity(), "你点击的是:"+languages[pos], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击了定位",Toast.LENGTH_SHORT).show();
                  nativel();
//                  getParkingByNet();
//                    testNet();
//                parkingList=DataSupport.findAll(Parking.class);
                showParks();

                Toast.makeText(getActivity(),"刷新成功！",Toast.LENGTH_SHORT).show();
            }

            private void testNet() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            OkHttpClient okhttp3=new OkHttpClient();
                            RequestBody rB=new FormBody.Builder().add("test","123").build();
                            Request r=new Request.Builder().url(addressParking).post(rB).build();
                            Response response=okhttp3.newCall(r).execute();
                            String responseData=response.body().string();
                            Log.d("111",responseData);
                            Toast.makeText(getActivity(),responseData,Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        return view;
    }
    //本地
    private void nativel(){
//        creatdbtest();
        dbtest();
        showdbtest();
    }
    //通过网络获取方法
    private void getParkingByNet(){
//                creatdbtest();
//                DataSupport.deleteAll(Parking.class);
                queryFromServer(addressParking,"全部");
//                queryParking();
    }
    /**
     *
     */
    private void testdb(){
        creatdbtest();
        dbtest();
        showdbtest();
    }
    private void creatdbtest(){
        LitePal.getDatabase();
    }
    private void dbtest(){
        Parking parking=new Parking();
        parking.setParkingId("1");
        parking.setParking_name("万达停车场");
        parking.setParking_location("西安邮电大学");
        parking.setResidual_lot("80");
        parking.setFreight_basis(2.5f);
        parking.setLongitude(1.0f);
        parking.setLatitude(1.0f);
        parking.setThumbs_up(200);
        parking.setAssess(1000);
        parking.setReserve_time(20);
        parking.save();
    }
    private void showParks(){
        Toast.makeText(view.getContext(),"size="+parkingList.size(),Toast.LENGTH_LONG).show();

    }
    private void showdbtest() {
//        parkingList=DataSupport.limit(10).offset(10).find(Parking.class);
        parkingList=DataSupport.findAll(Parking.class);
        parkingAdapter.setData(parkingList);
        parkingAdapter.notifyDataSetChanged();
        for (Parking p:parkingList){
            Log.d("111","parking id is "+p.getParkingId());
            Log.d("111","parking name is "+p.getParking_name());
            Log.d("111","parking location is "+p.getParking_location());        }
        Toast.makeText(view.getContext(),"parlingList size ="+parkingList.size(),Toast.LENGTH_SHORT).show();
//        List<Parking> parkings = DataSupport.findAll(Parking.class);
//        for (Parking p:parkings){
//            parkingList.add(p);

//            Log.d("111","parking id is "+p.getParkingId());
//            Log.d("111","parking name is "+p.getParking_name());
//            Log.d("111","parking location is "+p.getParking_location());

        }



    /**
     * 本地测试方法
     */
//    private void initParkings(){
//        for(int i=0;i<50;i++){
//            Random random=new Random();
//            int index=random.nextInt(parks.length);
//            parkingList.add(parks[index]);
//            parkingAdapter.notifyDataSetChanged();
//        }
//    }

    /**
     * 刷新停车场商家说数据
     * 1、去网络上请求最新数据，然后将这些数据展示出来
     */
    public void refreshParkings(){
        parkingList=DataSupport.limit(5).offset(5).find(Parking.class);
        Toast.makeText(getActivity(),"parkingList size="+parkingList.size()+"刷新成功！",Toast.LENGTH_LONG).show();
        parkingAdapter.setData(parkingList);
        parkingAdapter.notifyDataSetChanged();//通知数据发生了变化
        swipeRefreshLayout.setRefreshing(false);//表示刷新事件结束，并隐藏进度条
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
                        Toast.makeText(getActivity(),"加载失败",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
//                Toast.makeText(getActivity(),responseText,Toast.LENGTH_LONG).show();
                Log.d("111",responseText);
                boolean result=false;
                result= Utility.handleParkingResponse(responseText);
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            queryParking();
                        }


                    });
                }
            }
    });
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
    private void queryParking(){
        parkingList=DataSupport.findAll(Parking.class);

        if (parkingList.size()>0){
            parkingAdapter.setData(parkingList);
            parkingAdapter.notifyDataSetChanged();
        }else {
            queryFromServer(addressParking,"全部");
        }
    }


}