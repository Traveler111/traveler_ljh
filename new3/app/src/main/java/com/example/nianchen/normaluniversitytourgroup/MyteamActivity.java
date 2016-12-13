package com.example.nianchen.normaluniversitytourgroup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.Group;
import com.example.nianchen.normaluniversitytourgroup.adapter.Groupmyadapter;
import com.example.nianchen.normaluniversitytourgroup.page_activity.MyGroup;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class MyteamActivity extends AppCompatActivity {


    private ListView lv;
    private List<EMGroup> groupsList=new ArrayList<>();
    private int pageSize=20;
    private ArrayList<Group>mygroup=new ArrayList<>();
    private Groupmyadapter myadapter;
    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for(int i=0;i<groupsList.size();i++){
                Group group=new Group(groupsList.get(i).getGroupName(),groupsList.get(i).getGroupId()
                ,R.drawable.loginh);


                mygroup.add(group);
            }
            myadapter=new Groupmyadapter(MyteamActivity.this,mygroup);
            lv.setAdapter(myadapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myteam);
        findview();
        getgrouplist();
    }
    public void  findview(){
        lv=(ListView)findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ina=new Intent(MyteamActivity.this, MyGroup.class);
                ina.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                ina.putExtra(EaseConstant.EXTRA_USER_ID,mygroup.get(position).getDesc().toString().trim());
                startActivity(ina);
            }
        });
    }
    public void getgrouplist(){
         Thread th=new Thread(){
             @Override
             public void run() {
                 super.run();
                 EMCursorResult<EMGroupInfo> result = null;//需异步处理
                 try {
                     //result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(pageSize, "1");
                    // List<EMGroupInfo> returnGroups = result.getData();
                     List<EMGroup> returnGroups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                     groupsList =returnGroups;
                     Message msg=new Message();
                     myhandler.sendMessage(msg);
                 } catch (HyphenateException e) {
                     e.printStackTrace();
                 }
             }
         };
         th.start();
    }
}
