package com.example.easts.park;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by lenovo on 2017/11/7.
 */

public class SelectParkingSpace extends AppCompatActivity implements View.OnClickListener{
//    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ImageView back_Parkingdetail;
    private GridView paking_space_GV;
    private TextView parking_name;
    private LinearLayout invisible_LL;
    private GridLayout selected_parking_show;
    private HashMap<Integer,TextView> textViewList1;
//    private List<TextView> textViewList;
    private TextView pay;
    private float payMoney;
    private Button orderButton;
    private int i=0;
    private HashMap<Integer,Integer> lotstateMap;
    private int parkingId;
    private String parekingIn;
    private String parkinglotIds="";
    private String parkinglotId="";
    private String str;
    private String username;
    private String password;
    private String jsonData;
    private String addressOrderParkingLot="http://192.168.1.127:8080/park/carspace/bookCarspace";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        username=pref.getString("username","");
        password=pref.getString("password","");
//        Toast.makeText(this,"username:||"+username+"password:"+password,Toast.LENGTH_LONG).show();
        setContentView(R.layout.select_parking_lot);
        lotstateMap=new HashMap<Integer,Integer>();
//        textViewList=new ArrayList<TextView>();
        textViewList1=new HashMap<Integer,TextView>();
//        parkingId=getIntent().getIntExtra("parkingId");

        Intent intent=getIntent();
        parkingId=intent.getIntExtra("parkingId",-1);
        parekingIn=intent.getStringExtra("parkingIn");
        Log.i("parkingIn1",parekingIn);

        back_Parkingdetail = (ImageView) findViewById(R.id.back_parking_lot_details);
        paking_space_GV = (GridView) findViewById(R.id.paking_space_GV);
        selected_parking_show = (GridLayout) findViewById(R.id.selected_parking_show);
        invisible_LL = (LinearLayout) findViewById(R.id.invisible_view);
        pay = (TextView) findViewById(R.id.pay);
        orderButton=(Button)findViewById(R.id.to_order_selected);
        orderButton.setOnClickListener(this);
        back_Parkingdetail.setOnClickListener(this);


      final ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        try {
            jsonData=parekingIn;
            Log.i("parekingIn2",parekingIn);
            Log.i("jsonData2",jsonData);
            JSONArray jsonArray=new JSONArray(jsonData);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String id=jsonObject.getString("space_location");
                    int state=jsonObject.getInt("space_status");
                    //
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    if (state==0){
                        map.put("ItemImage", R.drawable.car_can_select);//
                        map.put("ItemText", "" + id);//
                        lstImageItem.add(map);
                        lotstateMap.put(i,0);
                    }else {
                        map.put("ItemImage", R.drawable.parking_ordered);//
                        map.put("ItemText", "" + id);//
                        lstImageItem.add(map);
                        lotstateMap.put(i,-1);
                    }
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < 15; i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("ItemImage", R.drawable.parking_right);//���ͼ����Դ��ID
//            map.put("ItemText", "NO." + String.valueOf(i));//�������ItemText
//            lstImageItem.add(map);
//            lotstateMap.put(i,false);
//        }
        //
        final SimpleAdapter saImageItems = new SimpleAdapter(this, //ûʲô����
                lstImageItem,//
                R.layout.parkingspace_item,//

                //
                new String[]{"ItemImage", "ItemText"},

                //
                new int[]{R.id.ItemImage, R.id.ItemText});
        paking_space_GV.setAdapter(saImageItems);
        paking_space_GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parkinglotIds=" ";
                if(lotstateMap.get(position).equals(0)) {
//                    textViewList.add(textView);
                    if (textViewList1.size()>0){
                        Toast.makeText(view.getContext(),"每个用户只能选一个车位!",Toast.LENGTH_SHORT).show();
                    }else {
                        //û��Ūչʾ����ӵ�С����
                        ImageView imageView = (ImageView) view.findViewById(R.id.ItemImage);
                        imageView.setImageResource(R.drawable.parking_right);
                        lotstateMap.put(position,1);
                        invisible_LL.setVisibility(View.VISIBLE);
                        selected_parking_show.setVisibility(View.VISIBLE);
                        pay.setVisibility(View.VISIBLE);
                        TextView textView = new TextView(view.getContext());
                        textView.setWidth(80);
                        textView.setHeight(50);
                        textView.setText(lstImageItem.get(position).get("ItemText").toString());
//                        textView.setText("No."+position);
                        Log.i("textView",""+lstImageItem.get(position).get("ItemText").toString());
                        textViewList1.put(position, textView);
                    }
                }else if(lotstateMap.get(position).equals(1)){
                    ImageView imageView = (ImageView) view.findViewById(R.id.ItemImage);
                    imageView.setImageResource(R.drawable.car_can_select);
                    lotstateMap.put(position,0);
                    textViewList1.remove(position);
                }
                selected_parking_show.removeAllViews();


                for (Integer key : textViewList1.keySet()) {
                    if(lotstateMap.get(key).equals(1)){
                        parkinglotIds=parkinglotIds+key+"车位id";
                        parkinglotId=textViewList1.get(key).getText().toString();
                    }
                    selected_parking_show.addView(textViewList1.get(key));
                    Log.i("textView-show",textViewList1.get(key).getText().toString());
                }
                payMoney = textViewList1.size() * 5;
                pay.setText("?" + String.valueOf(payMoney));
                selected_parking_show.requestLayout();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.to_order_selected:
                if (textViewList1.size()>1){
                    Toast.makeText(v.getContext(),"只能选一个车位!",Toast.LENGTH_LONG).show();
                    break;
                }else if (textViewList1.size()==1) {
                    Toast.makeText(v.getContext(), "", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SelectParkingSpace.this, MainActivity.class);
                    intent.putExtra("toshowF","2");
                    startActivity(intent);
                    finish();
                    Log.i("999",textViewList1.size()+"");
                    pref = PreferenceManager.getDefaultSharedPreferences(this);
//                    str = "ͣ����Id��" + parkingId + "��λ��:" + parkinglotIds + getTime() + "�Ƿ�Ԥ����";
                    str = "" + parkingId + "停车位:" + parkinglotId + getTime() + "时间";
//                    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                    dialog.setTitle("ȷ��Ԥ����Ϣ���£�");
//                    dialog.setMessage(str);
//                    dialog.setCancelable(false);
//                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                           Toast.makeText(v.getContext(),"�������������.....",Toast.LENGTH_LONG).show();
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try{

//                                        OkHttpClient okHttpClient=new OkHttpClient();
//                                        RequestBody requestBody=new FormBody.Builder().add("parking_id","1")
//                                                .add("space_location","A2").add("phonenumber","13456778654").build();
//                                        Request request=new Request.Builder().url(addressOrderParkingLot).post(requestBody).build();
//                                        Response response=okHttpClient.newCall(request).execute();
//                                        String result=response.body().string();
//                                        Log.i("777r",result);
//                                            String result="true";
//                                        if (result.equals("true"))
//                    });
//                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(v.getContext(), "��ȡ��Ԥ����", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    dialog.show();
                    //����̨����һ���ַ���������parkingId ��parkingId,ѡ��ĳ�λparingLotIdsList{0,1,2,3}ʱ�䣩
                    break;
                }else {
                    Toast.makeText(v.getContext(),"请选择您要预定的车位！",Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.back_parking_lot_details:
                Intent i=new Intent(this,ParkingdetailClass.class);
                Toast.makeText(this ,"返回停车场详情界面",Toast.LENGTH_LONG).show();
//                startActivity(i);
                setResult(0,i);
                finish();
                //��һ������Ϊ����ʱ����Ч�����ڶ�������Ϊ�˳�ʱ����Ч��
                overridePendingTransition(R.anim.back,R.anim.back1);
                break;
            default:
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getNowdatetime() {
        Calendar cal;String year,month,day,hour,minute,second,my_time_1,my_time_2;
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        year = String.valueOf(cal.get(Calendar.YEAR));
        month = String.valueOf(cal.get(Calendar.MONTH)+1);
        day = String.valueOf(cal.get(Calendar.DATE));
        if (cal.get(Calendar.AM_PM) == 0)
            hour = String.valueOf(cal.get(Calendar.HOUR));
        else {
            hour = String.valueOf(cal.get(Calendar.HOUR) + 11);
        }
        minute = String.valueOf(cal.get(Calendar.MINUTE));
        second = String.valueOf(cal.get(Calendar.SECOND));
        my_time_1 = year + "-" + month + "-" + day;
        my_time_2 = hour + "-" + minute + "-" + second;
        return my_time_1+my_time_2;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getTime(){
        //true
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = dff.format(new Date());
        return ee;
    }
}
