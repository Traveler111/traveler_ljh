package com.example.nianchen.normaluniversitytourgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendFour;
import com.example.nianchen.normaluniversitytourgroup.R;

import java.util.List;

/**
 * Created by nianchen on 2016/11/28.
 */
public class StrategyAdapter extends BaseAdapter {
    private List<FriendFour>friends;
    private Context c;
    private ImageView image;
    private TextView tails;

    public StrategyAdapter(List<FriendFour> friends, Context c) {
        this.friends = friends;
        this.c = c;
    }


    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            convertView= LayoutInflater.from(c).inflate(R.layout.array_item_four,null);
        }
        image = (ImageView)convertView.findViewById(R.id.imag);
        image.setImageResource(friends.get(position).getImage());
        tails = (TextView)convertView.findViewById(R.id.tails);
        tails.setText(friends.get(position).getTails().toString());
        return convertView;
    }
}
