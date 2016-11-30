package com.example.jiahanglee.huanxin2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jiahanglee.huanxin2.runtimepermissions.PermissionsManager;
import com.example.jiahanglee.huanxin2.runtimepermissions.PermissionsResultAction;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

public class MainActivity extends AppCompatActivity {
    private Button btn3,btn4;
    private EditText edit3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit3 = (EditText) findViewById(R.id.Edit3);

        btn3 = (Button)findViewById(R.id.Btn3);
        btn4 = (Button)findViewById(R.id.Btn4);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startchar();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signout();
            }
        });
    }
    public void startchar(){

        Intent intent  = new Intent(MainActivity.this,chat.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,edit3.getText().toString().trim());
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
        startActivity(intent);
    }
    public void signout(){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(MainActivity.this,login.class));
                finish();
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {
                Log.e("didi","退出登录失败");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(MainActivity.this,login.class));
                finish();
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {
                Log.e("didi","退出登录失败");
            }
        });
    }
    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
