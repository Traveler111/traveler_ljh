package com.example.nianchen.normaluniversitytourgroup.page_activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendTwo;
import com.example.nianchen.normaluniversitytourgroup.BaseClass.MyApp;
import com.example.nianchen.normaluniversitytourgroup.ChooseActivity;
import com.example.nianchen.normaluniversitytourgroup.MainActivityFabu;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.GroupAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/22.
 */
public class GroupActivity extends Activity {
    private List<FriendTwo> friends = new ArrayList<FriendTwo>();
    private ListView list;
    private GroupAdapter myadpter;
    private ImageView jia;
    private TextView tv1;
    private TextView tv2;
    String s1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiu_group);
        final String url="http://123.207.228.232/blog/Xianshifabu";
        final AsyncHttpClient client=new AsyncHttpClient();
        client.get(getApplicationContext(),url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject json=response.getJSONObject(i);
                        String scontent=json.getString("Content");
                        String stitle=json.getString("Title");

//                        String surl=json.getString("url");
//                        AsyncHttpClient client11=new AsyncHttpClient();
//                        RequestParams rp1=new RequestParams();

//                        rp1.put("url",surl);
//                        System.out.println("Title:"+stitle);
//                        System.out.println("Content:"+scontent);
                        friends.add(new FriendTwo("http://123.207.228.232/blog/downFile",stitle,scontent,R.drawable.b1));
                        myadpter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getid();
        setlistener();
        myadpter = new GroupAdapter(this,friends,list);
        list.setAdapter(myadpter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v1=getLayoutInflater().inflate(R.layout.layout_item,null);
                tv1=(TextView)v1.findViewById(R.id.tv1);
                tv2=(TextView)v1.findViewById(R.id.tv2);
                tv1.setText(friends.get(position).getTop());
                tv2.setText(friends.get(position).getBottom());
                s1=friends.get(position).getTop();
                AlertDialog.Builder adb=new AlertDialog.Builder(GroupActivity.this);
                adb.setView(v1);
                adb.setTitle("队伍信息");
                adb.setPositiveButton("我要加入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences spf= MyApp.getAppContext().getSharedPreferences("User",MODE_PRIVATE);
                        String s=spf.getString("uname","");
                        System.out.println(s);
                        String url1="http://123.207.228.232/blog/Jiarucontent";
                        AsyncHttpClient client1=new AsyncHttpClient();
                        RequestParams rp1=new RequestParams();
                        rp1.put("Title",s1);
                        rp1.put("Uname",s);
                        client.get(url1, rp1, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                Toast.makeText(GroupActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                    }
                });
                adb.setNegativeButton("取消",null);
                adb.create().show();
            }
        });
    }

    private void setlistener() {
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(GroupActivity.this, MainActivityFabu.class);
                startActivity(i);
            }
        });
    }

    private void getid() {
        list = (ListView)findViewById(R.id.group_list);
        jia =(ImageView)findViewById(R.id.img_jia);
    }

    //    private void getdata() {
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//        friends.add(new FriendTwo(R.drawable.a1,"抱犊寨","详情......",R.drawable.b1));
//    }
    public void onPause(){
        super.onPause();
        friends.clear();
    }
}
