package com.example.nianchen.normaluniversitytourgroup.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendOne;
import com.example.nianchen.normaluniversitytourgroup.BaseClass.Myfriendzzx;
import com.example.nianchen.normaluniversitytourgroup.MainActivityDitu;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.FindAdapter;
import com.example.nianchen.normaluniversitytourgroup.adapter.FindFragmentAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/22.
 */
public class FindFragment extends EaseConversationListFragment {
    private Button btn;
    private List<Myfriendzzx> friends=new ArrayList<Myfriendzzx>();
    private List<FriendOne>friends1 = new ArrayList<FriendOne>();
    private View view;
    private ListView lv;
    private FindFragmentAdapter myadapter;
    private SearchView search;
    private String findresult;
    private FindAdapter myadapter1;
    private Handler getfriendhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List <Myfriendzzx>friends1=new ArrayList<>();
            List <String> lists= (List<String>) msg.obj;
            for (int i = 0; i < lists.size()-1; i++) {
                if(lists.get(i).equals(lists.get(i+1))){

                }
                else if(i+1!=lists.size()-1&&lists.get(i)!=(lists.get(i+1))){
                    friends1.add(new Myfriendzzx(lists.get(i), R.drawable.loginhead));
                    Log.e("" + lists.get(i), "get");
                }
                else {
                    friends1.add(new Myfriendzzx(lists.get(i), R.drawable.loginhead));
                    friends1.add(new Myfriendzzx(lists.get(i+1), R.drawable.loginhead));
                }

            }
            friends.clear();
            friends.addAll(friends1);
            myadapter.notifyDataSetChanged();
        }
    };
    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final String str=(String )msg.obj;
            AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
            View view=getActivity().getLayoutInflater().inflate(R.layout.findfriend,null);
            adb.setView(view);
            adb.setTitle("寻找朋友");
            TextView name=(TextView)view.findViewById(R.id.username);
            name.setText(str);
            adb.setPositiveButton("加为好友", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        EMClient.getInstance().contactManager().addContact(str, "做个朋友吧");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
            adb.setNegativeButton("取消",null);
            adb.create().show();
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);
//        ListView list = (ListView) view.findViewById(R.id.findlist);
//        List<Map<String,Object>> listItems1 = new ArrayList<Map<String,Object>>();
//        for (int i=0;i<name1.length;i++){
//            HashMap<String, Object> listItem1=new HashMap<String, Object>();
//            listItem1.put("header1",imagesId1[i]);
//            listItem1.put("name1",name1[i]);
//            listItem1.put("desc1",desc1[i]);
//            listItems1.add(listItem1);
//        }
//        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),listItems1,
//                R.layout.array_item_one,
//                new String[]{"name1","hearder1","desc1"},
//                new int[]{R.id.name1 ,R.id.hearder1, R.id.desc1});
//        list.setAdapter(simpleAdapter);
        getdata();
        findview();
        getfriendlist();
        searchfriend();
        myadapter=new FindFragmentAdapter(getActivity(),friends);
        myadapter1 = new FindAdapter(friends1,getActivity());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getActivity(), MainActivityDitu.class);
                startActivity(i);
            }
        });
        lv.setAdapter(myadapter);
        lv.setAdapter(myadapter1);

        return view;
    }
    public void findview(){
        lv=(ListView)view.findViewById(R.id.findlist);
        btn=(Button)view.findViewById(R.id.btn);
        search=(SearchView)view.findViewById(R.id.search);
        Log.e("find","run");
    }
    public void getdata(){
        friends1.add(new FriendOne(R.drawable.lei,"雷达加朋友","添加身边的朋友"));
        friends1.add(new FriendOne(R.drawable.jia1,"面对面加群","与身边的朋友进入同一个群聊"));
        friends1.add(new FriendOne(R.drawable.sao,"扫一扫","扫描二维码名片"));
        friends1.add(new FriendOne(R.drawable.shou,"手机联系人","邀请通讯录中的好友"));
        friends1.add(new FriendOne(R.drawable.gong,"公众号","获取更多资源和服务"));
        Log.e("getdata","run");
    }
    public void searchfriend(){
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                if(query==null){
                    Toast.makeText(getActivity(),"好友不可为空",Toast.LENGTH_LONG).show();
                    return false;
                }
                AsyncHttpClient client=new AsyncHttpClient();
                String str="http://123.207.228.232/blog/findfriend";
                RequestParams params=new RequestParams();
                params.put("username",query);
                client.get(str, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        byte [] mybytes=bytes;
                        findresult=new String (mybytes);
                        Message msg=new Message();
                        msg.obj=query;
                        myhandler.sendMessage(msg);

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    public void getfriendlist(){
        Thread th=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Message msg=new Message();
                    msg.obj=usernames;
                    getfriendhandler.sendMessage(msg);
                    Log.e("getfriendlist","run");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }
    public void onPause(){
        super.onPause();
        friends.clear();
        friends1.clear();
    }
}
