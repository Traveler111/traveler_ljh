package com.example.nianchen.normaluniversitytourgroup.page_activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendThree;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/23.
 */
public class OppositeActivity extends Activity{//友聊
    private List<FriendThree>friends = new ArrayList<FriendThree>();
    private ListView list;
    private FriendsAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiu_opposote);
        getId();
        getData();
        myadapter = new FriendsAdapter(friends,OppositeActivity.this);
        list.setAdapter(myadapter);
    }
    private void getData() {
        friends.add(new FriendThree(R.drawable.a1,"李佳航","人是个神马东西",R.drawable.a11,"求男版一名，去抱犊寨爬山\n友聊账号：12345678"));
        friends.add(new FriendThree(R.drawable.a1,"李佳航","人是个神马东西",R.drawable.a11,"求男伴一名，去窦王岭爬山\n友聊号：232332332"));
        friends.add(new FriendThree(R.drawable.a1,"李佳航","人是个神马东西",R.drawable.a10,"本人男，河北师范大学软件学院学生，求女伴一名去天宫寺游玩，友聊账号：888"));
    }

    private void getId() {
        list = (ListView) findViewById(R.id.opposite_sexss);
    }
    public void onPause(){
        super.onPause();
        friends.clear();
    }
}
