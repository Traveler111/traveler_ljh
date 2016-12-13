package com.example.nianchen.normaluniversitytourgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendFive;
import com.example.nianchen.normaluniversitytourgroup.R;

import java.util.List;

/**
 * Created by nianchen on 2016/11/29.
 */
public class GalleryAdapter extends BaseAdapter {
    private List<FriendFive> friends;
    private Context c;
    private ImageView image;

    public GalleryAdapter(List<FriendFive> friends, Context c) {
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
        if(convertView == null){
            convertView = LayoutInflater.from(c).inflate(R.layout.array_item_five,null);
        }
        image = (ImageView) convertView.findViewById(R.id.imag);
        image.setImageResource(friends.get(position).getImage());
        return convertView;
    }
}
