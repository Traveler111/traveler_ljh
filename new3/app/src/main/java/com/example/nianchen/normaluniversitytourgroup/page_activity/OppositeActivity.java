package com.example.nianchen.normaluniversitytourgroup.page_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendThree;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.FriendsAdapter;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/23.
 */
public class OppositeActivity extends AppCompatActivity{//友聊
    private List<FriendThree>friends = new ArrayList<FriendThree>();
    private ListView list;
    private FriendsAdapter myadapter;
    private EaseConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiu_opposote);
       showconConversationList();
       // getData();
        //myadapter = new FriendsAdapter(friends,OppositeActivity.this);
        //list.setAdapter(myadapter);
    }
    private void getData() {
        friends.add(new FriendThree(R.drawable.a1,"李佳航","人是个神马东西",R.drawable.a11,"求男版一名，去抱犊寨爬山\n友聊账号：12345678"));
        friends.add(new FriendThree(R.drawable.a1,"李佳航","人是个神马东西",R.drawable.a11,"求男伴一名，去窦王岭爬山\n友聊号：232332332"));
        friends.add(new FriendThree(R.drawable.a1,"李佳航","人是个神马东西",R.drawable.a10,"本人男，河北师范大学软件学院学生，求女伴一名去天宫寺游玩，友聊账号：888"));
    }


    public void onPause(){
        super.onPause();
        friends.clear();
    }
    public void showconConversationList(){
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(OppositeActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.container,conversationListFragment).commit();
    }
}
