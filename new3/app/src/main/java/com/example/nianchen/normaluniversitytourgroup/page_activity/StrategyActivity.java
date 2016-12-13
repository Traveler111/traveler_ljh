package com.example.nianchen.normaluniversitytourgroup.page_activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendFour;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.StrategyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/22.
 */
public class StrategyActivity extends Activity {
    private List<FriendFour> friends = new ArrayList<FriendFour>();
    private StrategyAdapter myadapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiu_strategy);
        getId();
        getData();
        myadapter = new StrategyAdapter(friends,StrategyActivity.this);
        list.setAdapter(myadapter);
    }

    private void getData() {
        friends.add(new FriendFour(R.drawable.a1,"抱犊寨：我省著名旅游景点。。。。"));
    }

    private void getId() {
        list = (ListView) findViewById(R.id.strategys);
    }
    public void onPause(){
        super.onPause();
        friends.clear();
    }
}
