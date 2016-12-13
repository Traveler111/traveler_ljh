package com.example.jiahanglee.huanxin2;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.exceptions.HyphenateException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;


import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;


public class login extends AppCompatActivity {

    private IUiListener userInfoListener;
    private IUiListener loginListener;
    private String SCOPE = "all";
    private   Tencent mTencent;
    private Button btn1,btn2,qq;
    private EditText edit1,edit2;
    private EditText textView33;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    edit1 = (EditText) findViewById(R.id.Edit1);
    edit2 = (EditText) findViewById(R.id.Edit2);
    textView33 = (EditText) findViewById(R.id.text33);
        btn1 = (Button)findViewById(R.id.Btn1);
        btn2 = (Button)findViewById(R.id.Btn2);
        qq = (Button)findViewById(R.id.QQ);

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
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQqLogin();

                mTencent.login(login.this, SCOPE, loginListener);
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
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                EaseUI.getInstance().getNotifier().onNewMsg(message);
            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }


        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };
    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }
    /** ------------------------QQ第三方登录-------------------- */
    //初始化QQ登录分享的需要的资源
    private void initQqLogin(){
        mTencent =  Tencent.createInstance("1105794897", this);
        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后回调该方法
                Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT).show();
                String openID;
                JSONObject jo = (JSONObject) o;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(login.this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(login.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        };
        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if(o == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) o;
                    Log.e("JO:",jo.toString());
                    int ret = jo.getInt("ret");
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    String url = jo.getString("figureurl_qq_1");
                    Toast.makeText(login.this, "你好，" + nickName,Toast.LENGTH_LONG).show();
                    textView33.setText(nickName+"\n"+gender+"\n"+url);
                } catch (Exception e) {
                }


            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//  官方文档上面的是错误的
//        if(requestCode == Constants.REQUEST_API) {
//            if(resultCode == Constants.RESULT_LOGIN) {
//                mTencent.handleLoginData(data, loginListener);
//            }
//  resultCode 是log出来的，官方文档里给的那个属性是没有的

        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                UserInfo info = new UserInfo(this, mTencent.getQQToken());
                info.getUserInfo(userInfoListener);
            }
        }
    }
            /** -------------------------QQ第三方登录结束-------------------- */
            private int testRandom2(){
                int k=0;
                Random random=new Random();
                for (int i = 0; i <10; i++) {
                    System.out.println("random.nextInt()="+random.nextInt(20));
                    k=k+random.nextInt()*10^i;
                }
                System.out.println("/////以上为testRandom2()的测试///////");
                return k;
            }
}
