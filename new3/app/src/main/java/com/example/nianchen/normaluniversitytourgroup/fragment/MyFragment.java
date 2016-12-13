package com.example.nianchen.normaluniversitytourgroup.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nianchen.normaluniversitytourgroup.MyteamActivity;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.hyphenate.chat.EMClient;

/**
 * Created by nianchen on 2016/11/21.
 */
public class MyFragment extends Fragment{
    private View view;
    private Button exitbtn;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my, container, false);
        findview();
        btn=(Button)view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getActivity(), MyteamActivity.class);
                startActivity(i);
            }
        });
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        return view;
    }
    public void findview(){
        exitbtn=(Button)view.findViewById(R.id.exit);
    }
    public void exit(){
        EMClient.getInstance().logout(true);
        Toast.makeText(getActivity(),"退出成功",Toast.LENGTH_LONG).show();
        getActivity().finish();
    }
}
