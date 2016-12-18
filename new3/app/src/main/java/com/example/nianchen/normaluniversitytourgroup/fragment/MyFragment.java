package com.example.nianchen.normaluniversitytourgroup.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nianchen.normaluniversitytourgroup.MainActivityFabu;
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
    private TextView number;
    private ProgressDialog mDialog;

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
                mDialog = new ProgressDialog(getActivity());
                mDialog.setMessage("正在退出，请稍后...");
                mDialog.show();
                exit();
            }
        });
        number.setText(EMClient.getInstance().getCurrentUser().toString().trim());
        return view;
    }
    public void findview(){
        exitbtn=(Button)view.findViewById(R.id.exit);
        number=(TextView)view.findViewById(R.id.number);
    }
    public void exit(){
        EMClient.getInstance().logout(true);
        mDialog.dismiss();
        Toast.makeText(getActivity(),"退出成功",Toast.LENGTH_LONG).show();
        getActivity().finish();
    }
}
