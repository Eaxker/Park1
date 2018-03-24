package com.example.easts.park;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //获取用户账号相关参数
    private EditText input_username;
    private String username;
    //获取用户密码相关参数
    private EditText input_password;
    private String password;
    //用户登录按钮
    private Button login;
    //用户注册按钮
    private Button regist;
    //记住密码
    private CheckBox remember_password;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.login);
        regist = (Button) findViewById(R.id.regist);
        remember_password = (CheckBox) findViewById(R.id.rember_password);

        login.setOnClickListener(this);
        regist.setOnClickListener(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);

        if(isRemember){
            String username = pref.getString("username","");
            String password = pref.getString("password","");
            input_username.setText(username);
            input_password.setText(password);
            remember_password.setChecked(true);
        }

    }

    @Override
    public void onClick(View view) {
//        Throwable throwable = new Throwable();
//        Log.i("LoginActivity", Log.getStackTraceString(throwable));
        switch(view.getId()){
            case R.id.login:
                LoginBySample();
//                toLoginByNet();
                break;
            case R.id.regist:
                Intent intent2 = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.back,R.anim.fade);
                finish();
                break;
            default:
                break;
        }
    }

    /***
     * 用网络登录
     */
    private void toLoginByNet() {
//                android4.0后所有耗时操作都放在新线程里面执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取用户输入信息
                username = input_username.getText().toString();
                password = input_password.getText().toString();
                try {
                    //发起网络请求
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phonenumber", username)
                            .add("password", password)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.133:8080/park/user/login")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //responseData = responseData.substring(1,responseData.length()-1);
                    Log.i("4444", responseData);
                    if (responseData.equals("success")) {
                        editor = pref.edit();
                        if (remember_password.isChecked()) {
                            editor.putBoolean("remember_password", true);
                            editor.putString("username", username);
                            editor.putString("password", password);
                        } else {
                            editor.clear();
                        }
                        editor.apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent1);
                                finish();
                                //淡出效果
                                overridePendingTransition(R.anim.fade, R.anim.fade0  );
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /***
     * 不用网络登录
     */
    public void LoginBySample(){
//                注销
        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent1);
        //淡出效果
        overridePendingTransition(R.anim.fade, R.anim.fade0 );
        finish();
    }
}
