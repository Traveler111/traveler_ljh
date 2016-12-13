package com.example.nianchen.normaluniversitytourgroup.BaseClass;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.nianchen.normaluniversitytourgroup.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by zhangzhixin on 2016/12/5.
 */
public class FriendReciver extends BroadcastReceiver{
    private AlertDialog dia;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String name=intent.getStringExtra("name");
        String reason=intent.getStringExtra("reason");
        Log.e("broad","receive");
        AlertDialog.Builder adb=new AlertDialog.Builder(context);
        View view=LayoutInflater.from(context).inflate(R.layout.findfriend,null);
        adb.setView(view);
        TextView nametv=(TextView)view.findViewById(R.id.username);
        nametv.setText(name);
        TextView reasontv=(TextView)view.findViewById(R.id.reason);
        reasontv.setText(reason);
        adb.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(name);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        });
        adb.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    EMClient.getInstance().contactManager().declineInvitation(name);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        });

        dia=adb.create();
        dia.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        dia.show();
    }
}
