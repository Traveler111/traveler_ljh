package com.example.nianchen.normaluniversitytourgroup.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.page_activity.CyclingActivity;
import com.example.nianchen.normaluniversitytourgroup.page_activity.FootActivity;
import com.example.nianchen.normaluniversitytourgroup.page_activity.GalleryActivity;
import com.example.nianchen.normaluniversitytourgroup.page_activity.GroupActivity;
import com.example.nianchen.normaluniversitytourgroup.page_activity.OppositeActivity;
import com.example.nianchen.normaluniversitytourgroup.page_activity.StrategyActivity;
import com.example.nianchen.normaluniversitytourgroup.page_activity.ToolsActivity;
import com.example.nianchen.normaluniversitytourgroup.page_activity.TravelersActivity;

public class HomeFragment extends Fragment {
    private ViewFlipper flipper;//滚动图
    private LinearLayout group;//组团
    private LinearLayout travelers;//驴友会
    private LinearLayout strategy;//游记攻略
    private LinearLayout opposite;//
    private LinearLayout chat;
    private LinearLayout gallery;//图库
    private LinearLayout foot;//徒步
    private LinearLayout cycling;
    private LinearLayout tools;
    private int[] resId = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4};
    private View view;
    private Intent i = new Intent();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        GetId();//获取界面控件
        Flipper();//滚动图
        Onclick();//监听触发事件

        return view;
    }
    /**
     * 获取控件
     */
    private void GetId() {
        flipper = (ViewFlipper) view.findViewById(R.id.flipper);
        group = (LinearLayout) view.findViewById(R.id.zutuan);
        travelers = (LinearLayout) view.findViewById(R.id.luyouhui);
        strategy = (LinearLayout) view.findViewById(R.id.strategy);//游记攻略
        opposite = (LinearLayout) view.findViewById(R.id.opposite_sexs);
        chat = (LinearLayout) view.findViewById(R.id.chat);
        gallery = (LinearLayout) view.findViewById(R.id.gallery);//图库
        foot = (LinearLayout) view.findViewById(R.id.foot);
        cycling = (LinearLayout) view.findViewById(R.id.clcying);
        tools = (LinearLayout) view.findViewById(R.id.tools);
    }

    /**
     * 监听触发事件
     */
    private void Onclick() {
        /**
         * 组团
         */
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), GroupActivity.class);
                startActivity(i);
            }
        });
        /**
         * 驴友会
         */
        travelers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), TravelersActivity.class);
                startActivity(i);
            }
        });
        /**
         * 游记攻略
         */
        strategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), StrategyActivity.class);
                startActivity(i);
            }
        });
        opposite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), OppositeActivity.class);
                startActivity(i);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            public ImageButton image_home;
            public ImageButton image_mes;
            private MesFragment mMes;
            android.app.FragmentManager fm = getFragmentManager();
            android.app.FragmentTransaction transaction = fm.beginTransaction();
            @Override
            public void onClick(View v) {
                image_mes =(ImageButton) view.findViewById(R.id.image_mes) ;
                image_home = (ImageButton) view.findViewById(R.id.image_home) ;
                mMes = new MesFragment();
                transaction.replace(R.id.contaner, mMes);
//                image_home.setImageResource(R.drawable.home1);
//                image_mes.setImageResource(R.drawable.mess2);
                transaction.commit();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), GalleryActivity.class);
                startActivity(i);
            }
        });
        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), FootActivity.class);
                startActivity(i);
            }
        });
        cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), CyclingActivity.class);
                startActivity(i);
            }
        });
        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(getActivity(), ToolsActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * 滚动图
     */
    private void Flipper() {
        /**
         * 动态导入的方式为ViewFlipper加入子View
         */
        for (int i = 0; i < resId.length; i++) {
            flipper.addView(getImageView(resId[i]));

        }
        /**
         * 为ViewFlipper去添加动画效果
         */
        flipper.setInAnimation(getActivity(), R.anim.left_in);
        flipper.setOutAnimation(getActivity(), R.anim.left_out);
        flipper.setFlipInterval(2000);
        flipper.startFlipping();

    }
    private ImageView getImageView(int resId){
        ImageView image = new ImageView(getActivity());
        image.setBackgroundResource(resId);
        return image;
    }
}

