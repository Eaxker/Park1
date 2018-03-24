package com.example.easts.park;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.easts.park.R.layout.revise;

/**
 * Created by lenovo on 2017/10/31.
 */

public class Revise extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String username;
    private String password;
    ImageView conserve;
    ImageView back_me;
    EditText reviseContent;
    TextView reviseTitle;
    String reviseWord;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        username=pref.getString("username","");
        password=pref.getString("password","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revise);
        conserve=(ImageView) findViewById(R.id.conserve_IV);
        back_me=(ImageView)findViewById(R.id.back_me);
        reviseContent=(EditText)findViewById(R.id.revise_ET);
        reviseTitle=(TextView)findViewById(R.id.title_revise);
        Intent i=getIntent();
        final String str=i.getStringExtra("extra_data");
        reviseTitle.setText(str);
        back_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviseContent.setText("");
                Intent back_me=new Intent();
                setResult(RESULT_CANCELED,back_me);
                finish();
            }
        });
        conserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                reviseWord=reviseContent.getText().toString();
                if(str.equals("修改手机号")){
                    //用作手机号码的匹配的正则表达式
                    final String regix_phone = "1(3|5|7|8)[0-9]{9}";
                    if(!reviseWord.matches(regix_phone)){
                        Toast.makeText(Revise.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                        return ;
                    }else{
                        Toast.makeText(Revise.this,"修改手机号成功",Toast.LENGTH_SHORT).show();
                        /**
                         * 进行网络处理后
                         */
                        Intent i_back=new Intent();
                        i_back.putExtra("return_data",reviseWord);
                        Toast.makeText(Revise.this,reviseWord,Toast.LENGTH_LONG);
                        setResult(RESULT_OK,i_back);
                        finish();
                    }
                }else if(str.equals("修改车牌号")){
                    Toast.makeText(Revise.this,"修改车牌号成功",Toast.LENGTH_SHORT).show();
                    /**
                     * 进行网络处理后
                     */
                    Intent i_back=new Intent();
                    i_back.putExtra("return_data",reviseWord);
                    Toast.makeText(Revise.this,reviseWord,Toast.LENGTH_LONG);
                    setResult(RESULT_OK,i_back);
                    finish();
                }else if (str.equals("修改昵称")){
                    Toast.makeText(Revise.this,"修改昵称成功",Toast.LENGTH_SHORT).show();
                    Toast.makeText(view.getContext(),"正在请求服务器.....",Toast.LENGTH_LONG).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                OkHttpClient okHttpClient=new OkHttpClient();
                                RequestBody requestBody=new FormBody.Builder()
                                        .add("phoneNum",username).add("newNickName",reviseContent.getText().toString()).build();
                                Request request=new Request.Builder().url("http").post(requestBody).build();
                                Response response=okHttpClient.newCall(request).execute();
                                String result=response.body().string();
                                if (result.equals(true)){
                                    editor.putString("nickname",reviseContent.getText().toString());
                                    Toast.makeText(view.getContext(), "修改昵称成功！", Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    /**
                     * 进行网络处理后
                     */
                    Intent i_back=new Intent();
                    i_back.putExtra("return_data",reviseWord);
                    Toast.makeText(Revise.this,reviseWord,Toast.LENGTH_LONG);
                    setResult(RESULT_OK,i_back);
                    finish();
                }else if (str.equals("修改密码")){
                    Toast.makeText(Revise.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                    /**
                     * 进行网络处理后
                     */
                    Intent i_back=new Intent();
                    i_back.putExtra("return_data",reviseWord);
                    Toast.makeText(Revise.this,reviseWord,Toast.LENGTH_LONG);
                    setResult(RESULT_OK,i_back);
                    finish();
                }else if(str.equals("修改产车商")){
                    Toast.makeText(Revise.this,"修改产车商成功",Toast.LENGTH_SHORT).show();
                    /**
                     * 进行网络处理后
                     */
                    Intent i_back=new Intent();
                    i_back.putExtra("return_data",reviseWord);
                    Toast.makeText(Revise.this,reviseWord,Toast.LENGTH_LONG);
                    setResult(RESULT_OK,i_back);
                    finish();
            }
        }
    });


}
}

