package com.example.jiahanglee.huanxin2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class login extends AppCompatActivity {

    private Button btn1,btn2;
    private EditText edit1,edit2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    edit1 = (EditText) findViewById(R.id.Edit1);
    edit2 = (EditText) findViewById(R.id.Edit2);
        btn1 = (Button)findViewById(R.id.Btn1);
        btn2 = (Button)findViewById(R.id.Btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            signin();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            signup();
            }
        });
    }
    public void signup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(edit1.getText().toString().trim(),edit2.getText().toString().trim());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void signin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(edit1.getText().toString().trim(), edit2.getText().toString().trim(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(login.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("login","登录失败");
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        }).start();
    }
}
