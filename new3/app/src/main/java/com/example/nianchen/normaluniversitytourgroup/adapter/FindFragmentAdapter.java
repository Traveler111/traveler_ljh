package com.example.nianchen.normaluniversitytourgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendOne;
import com.example.nianchen.normaluniversitytourgroup.BaseClass.Myfriendzzx;
import com.example.nianchen.normaluniversitytourgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/24.
 */
public class FindFragmentAdapter extends BaseAdapter {
    private List <Myfriendzzx> friends;
    private Context c;
    private ImageView img;
    private TextView name;
    private TextView desc;

    public FindFragmentAdapter(Context c, List<Myfriendzzx> friends) {
        this.c = c;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.size();//获取长度
    }

    @Override
    public Object getItem(int position) {//获取一条
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {//获取一条的id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null ){
            convertView= LayoutInflater.from(c).inflate(R.layout.array_item_one,null);//jiazaibujv
        }
        img=(ImageView) convertView.findViewById(R.id.hearder1);
        img.setImageResource(friends.get(position).getImg());//fuzhi
        name=(TextView) convertView.findViewById(R.id.name1);
        name.setText(friends.get(position).getName().toString());

        return convertView;
    }
}
