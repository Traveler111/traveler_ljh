package com.example.nianchen.normaluniversitytourgroup.page_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nianchen.normaluniversitytourgroup.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * Created by zhangzhixin on 2016/12/11.
 */

public class MyGroup  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupchat);
        EaseChatFragment chatFragment=new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container,chatFragment).commit();
    }
}
