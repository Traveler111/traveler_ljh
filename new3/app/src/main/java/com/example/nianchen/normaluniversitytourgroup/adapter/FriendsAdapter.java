package com.example.nianchen.normaluniversitytourgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendThree;
import com.example.nianchen.normaluniversitytourgroup.R;

import java.util.List;

/**
 * Created by nianchen on 2016/11/28.
 */
public class FriendsAdapter extends BaseAdapter {
    private List<FriendThree> friends;
    private Context c;
    private ImageView image;
    private TextView name;
    private TextView personality;
    private ImageView picture;
    private TextView mess;

    public FriendsAdapter(List<FriendThree> friends, Context c) {
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
            convertView = LayoutInflater.from(c).inflate(R.layout.array_item_three,null);
        }
        image = (ImageView)convertView.findViewById(R.id.image);
        image.setImageResource(friends.get(position).getImage());
        name = (TextView)convertView.findViewById(R.id.name);
        name.setText(friends.get(position).getName().toString());
        personality = (TextView)convertView.findViewById(R.id.personality);
        personality.setText(friends.get(position).getPersonality().toString());
        picture = (ImageView)convertView.findViewById(R.id.picture);
        picture.setImageResource(friends.get(position).getPicture());
        mess = (TextView)convertView.findViewById(R.id.mess);
        mess.setText(friends.get(position).getMess().toString());
        return convertView;
    }
}
