package com.example.nianchen.normaluniversitytourgroup.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendOne;
import com.example.nianchen.normaluniversitytourgroup.BaseClass.Myfriendzzx;
import com.example.nianchen.normaluniversitytourgroup.MainActivity;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.MesFragmentMesAdapter;
import com.example.nianchen.normaluniversitytourgroup.adapter.MesFragmentContactAdapter;
import com.example.nianchen.normaluniversitytourgroup.page_activity.ChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/22.
 */
public class MesFragment extends Fragment {
    private List<FriendOne> friends1 = new ArrayList<FriendOne>();
    private List<FriendOne> friends2 = new ArrayList<FriendOne>();
    private View view;
    private LinearLayout chat;
    private ListView list11;
    private MesFragmentMesAdapter myadapter;
    private ListView list12;
    private MesFragmentContactAdapter myadapter1;
    private ArrayList<Myfriendzzx> friends = new ArrayList<>();
    private Handler getfriendhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Myfriendzzx> friends1 = new ArrayList<>();
            List<String> lists = (List<String>) msg.obj;
            for (int i = 0; i < lists.size(); i++) {
                friends1.add(new Myfriendzzx(lists.get(i), R.drawable.loginhead));
                Log.e("" + lists.get(i), "get");
            }
            friends.clear();
            friends.addAll(friends1);
            myadapter1.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        chat = (LinearLayout) view.findViewById(R.id.chat);
        Tabhosts();
        getViews();
        //  getdata1();
        // getdata2();
        getfriendlist();
        //  myadapter = new MesFragmentMesAdapter(getActivity(),friends1);
        myadapter1 = new MesFragmentContactAdapter(friends,getActivity());
       // list11.setAdapter(myadapter);
        list12.setAdapter(myadapter1);
        list12.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ina=new Intent(getActivity(), ChatActivity.class);
                ina.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                ina.putExtra(EaseConstant.EXTRA_USER_ID,friends.get(position).getName().toString().trim());
                startActivity(ina);
            }
        });
        return view;
    }

    private void getdata2() {
        friends2.add(new FriendOne(R.drawable.a1, "李佳航", "人是个神马东西"));
        friends2.add(new FriendOne(R.drawable.a2, "李佳航", "人是个神马东西"));
        friends2.add(new FriendOne(R.drawable.a3, "李佳航", "人是个神马东西"));
        friends2.add(new FriendOne(R.drawable.a4, "李佳航", "人是个神马东西"));
        friends2.add(new FriendOne(R.drawable.a1, "李佳航", "人是个神马东西"));
    }

    private void getViews() {
        list11 = (ListView) view.findViewById(R.id.mes_list);
        list12 = (ListView) view.findViewById(R.id.mes_list1);
    }

    private void getdata1() {
        friends1.add(new FriendOne(R.drawable.a2, "李佳航", "人是个神马东西"));
        friends1.add(new FriendOne(R.drawable.a2, "李佳航", "人是个神马东西"));
        friends1.add(new FriendOne(R.drawable.a2, "李佳航", "人是个神马东西"));
        friends1.add(new FriendOne(R.drawable.a2, "李佳航", "人是个神马东西"));
        friends1.add(new FriendOne(R.drawable.a2, "李佳航", "人是个神马东西"));
    }

    private void Tabhosts() {
        TabHost tabHost = (TabHost) view.findViewById(R.id.tabhost);
        tabHost.setup();
        TabWidget tabWidget = tabHost.getTabWidget();
        TabHost.TabSpec tab001 = tabHost.newTabSpec("tab001")
                .setIndicator("消息")
                .setContent(R.id.tab001);
        tabHost.addTab(tab001);
        TabHost.TabSpec tab002 = tabHost.newTabSpec("tab002")
                .setIndicator("联系人")
                .setContent(R.id.tab002);
        tabHost.addTab(tab002);
        if (getId() == R.id.liner_mes) {
            tabHost.addTab(tab001);
            tabHost.addTab(tab002);
        } else if (getId() == R.id.chat) {
            tabHost.addTab(tab002);
            tabHost.addTab(tab001);
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void getfriendlist() {
        Thread th = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Message msg = new Message();
                    msg.obj = usernames;
                    getfriendhandler.sendMessage(msg);
                    Log.e("getfriendlist", "run");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();
    };
}
