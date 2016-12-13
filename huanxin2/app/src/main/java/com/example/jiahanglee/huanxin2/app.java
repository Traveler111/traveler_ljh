package com.example.jiahanglee.huanxin2;

import android.app.Application;
import android.content.Context;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.tencent.tauth.Tencent;

/**
 * Created by jiahang Lee on 2016/11/24.
 */

public class app extends Application {
    public static Context applicationContext;
    private static app instance;
    protected EMMessageListener messageListener = null;

    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        EaseUI.getInstance().init(this, options);
    }
    public static app getInstance() {
        return instance;
    }
}
