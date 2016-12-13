package com.example.nianchen.normaluniversitytourgroup.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendFour;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.StrategyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/22.
 */
public class AttFragment extends Fragment{
    private List<FriendFour> friends = new ArrayList<FriendFour>();
    private StrategyAdapter myadapter;
    private ListView lv;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attractions, container, false);
        getdata();
        findview();
        myadapter=new StrategyAdapter(friends,getActivity());
        lv.setAdapter(myadapter);
        return view;
    }
    public void findview(){
        lv=(ListView)view.findViewById(R.id.att);
        Log.e("find","run");
    }
    public void getdata(){
        friends.add(new FriendFour(R.drawable.a1,"抱犊寨：我省著名旅游景点。。。。"));
        friends.add(new FriendFour(R.drawable.a2,"天宫寺：我省著名旅游景点。。。。"));
        Log.e("getdata","run");
    }
    public void onPause(){
        super.onPause();
        friends.clear();
    }
}
