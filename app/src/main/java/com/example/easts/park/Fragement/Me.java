package com.example.easts.park.Fragement;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easts.park.LoginActivity;

import com.example.easts.park.R;
import com.example.easts.park.Revise;
import com.example.easts.park.collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lenovo on 2017/10/31.
 */

public class Me extends Fragment {


    public static final int HEAD_SCULPTURE_RESERVE=1;
    public static final int NAME_RESERVE=2;
    public static final int IPHONENUM_REVISE=3;
    public static final int CARlICENCE_REVISE=4;
    public static final int PASSWORD_REVISE=5;
    public static final int CARPRODUCTION_REVISE=6;

    private String title;
    private  View view;
    private Uri imageUri;

    ImageView collection_IV;
    ImageView head_sculpture_IV;
    TextView useName;
    TextView iphoneNum;
    TextView carLicence;
    TextView password;
    TextView carProduction;


    ImageView right_IV;
    ImageView iphoneNum_revise_IV;
    ImageView carLicence_revise_IV;
    ImageView password_revise_IV;
    ImageView carProduction_revise_IV;
    ImageButton exit_IB;
    Button exit_button;
    LinearLayout exit_LL;
    Intent i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i=new Intent(getActivity(),Revise.class);
        view=inflater.inflate(R.layout.me_layout,container,false);
        collection_IV=(ImageView) view.findViewById(R.id.collection);
        head_sculpture_IV=(ImageView)view.findViewById(R.id.head_sculpture);
        useName=(TextView)view.findViewById(R.id.usename);
        iphoneNum=(TextView)view.findViewById(R.id.iphone);
        carLicence=(TextView)view.findViewById(R.id.carlicence);
        password=(TextView)view.findViewById(R.id.password);
        carProduction=(TextView)view.findViewById(R.id.car_brand);

        right_IV=(ImageView) view.findViewById(R.id.right);
        iphoneNum_revise_IV=(ImageView)view.findViewById(R.id.iphoneNum_revise_IV);
        carLicence_revise_IV=(ImageView)view.findViewById(R.id.carLicence_revise);
        password_revise_IV=(ImageView)view.findViewById(R.id.password_revise);
        carProduction_revise_IV=(ImageView)view.findViewById(R.id.carProduction_revise);
        exit_LL=(LinearLayout)view.findViewById(R.id.exit_LL);
        exit_IB=(ImageButton)view.findViewById(R.id.exit_IB);
        exit_button=(Button)view.findViewById(R.id.exit_Button);
        Toast.makeText(getActivity(),"欢迎来到主上界面",Toast.LENGTH_LONG).show();
        //开启收藏界面
        collection_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             Intent  collection_Intent=new Intent(getActivity(),collection.class);
//                startActivity(collection_Intent);
                Toast.makeText(view.getContext(),"功能开发中...",Toast.LENGTH_SHORT).show();

            }
        });
        //修改头像
        head_sculpture_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPicture();
            }
        });
        //修改姓名
        right_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str="修改昵称";
                i.putExtra("extra_data",str);
                startActivityForResult(i,2);

            }
        });
        //修改手机号
        iphoneNum_revise_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title="修改手机号";
                i.putExtra("extra_data",title);
                startActivityForResult(i,3);
            }
        });
        //修改车牌号
        carLicence_revise_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title="修改车牌号";
                i.putExtra("extra_data",title);
                startActivityForResult(i,4);
            }
        });
        //修改密码
        password_revise_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title="修改密码";
                i.putExtra("extra_data",title);
                startActivityForResult(i,5);
            }
        });
        //修改产车商
        carProduction_revise_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title="修改产车商";
                i.putExtra("extra_data",title);
                startActivityForResult(i,6);
            }
        });
        exit_LL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"点击了退出选项布局",Toast.LENGTH_SHORT).show();
                Intent i2=new Intent(getActivity(), LoginActivity.class);
                startActivity(i2);
                getActivity().finish();
            }
        });
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"点击了退出选项Button",Toast.LENGTH_SHORT).show();
                Intent i2=new Intent(getActivity(), LoginActivity.class);
                startActivity(i2);
                getActivity().finish();
            }
        });
        exit_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 清空个人信息
                 */
                Toast.makeText(getActivity(),"点击了退出选项ImageButton",Toast.LENGTH_SHORT).show();
                Intent i2=new Intent(getActivity(), LoginActivity.class);
                startActivity(i2);
                getActivity().finish();
            }
        });


        return  view;
    }

    /**
     * 从拍张或从相册中选择一张图片
     */
    public void pickPicture(){

        File outputImage=new File(getActivity().getExternalCacheDir(),"output_image.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(getActivity(),"com.example.cameraalbumtest.fileprovider",outputImage);
        }else{
            imageUri=Uri.fromFile(outputImage);
        }
        Intent collection=new Intent("android.media.action.IMAGE_CAPTURE");
        collection.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(collection,1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    try{
                        //讲拍摄照片显示出来
                        Bitmap bitmap= BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        head_sculpture_IV.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    String returnData=data.getStringExtra("return_data");
                    Toast.makeText(getActivity(),returnData,Toast.LENGTH_LONG);
                    useName.setText(returnData);
                }
                break;
            case 3:
                if(resultCode==RESULT_OK){
                    String returnData=data.getStringExtra("return_data");
                    Toast.makeText(getActivity(),returnData,Toast.LENGTH_LONG);
                    iphoneNum.setText(returnData);
                }
                break;
            case 4:
                if(resultCode==RESULT_OK){
                    String returnData=data.getStringExtra("return_data");
                    Toast.makeText(getActivity(),returnData,Toast.LENGTH_LONG);
                    carLicence.setText(returnData);
                }
                break;
            case 5:
                if(resultCode==RESULT_OK){
                    String returnData=data.getStringExtra("return_data");
                    Toast.makeText(getActivity(),returnData,Toast.LENGTH_LONG);
//                    password.setText(returnData);
                    password.setText("********");
                }
                break;
            case 6:
                if(resultCode==RESULT_OK){
                    String returnData=data.getStringExtra("return_data");
                    Toast.makeText(getActivity(),returnData,Toast.LENGTH_LONG);
                    carProduction.setText(returnData);
                }
                break;
            default:
                break;
        }
    }




}