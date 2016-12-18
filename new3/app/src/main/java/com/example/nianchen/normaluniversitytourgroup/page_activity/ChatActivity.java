package com.example.nianchen.normaluniversitytourgroup.page_activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * Created by zhangzhixin on 2016/12/11.
 */

public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        EaseChatFragment chatFragment=new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container,chatFragment).commit();

    }

}
