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
public class MesFragmentContactAdapter extends BaseAdapter {
    private ArrayList<Myfriendzzx> friends1=new ArrayList<>();
    private Context c;
    private ImageView img;
    private TextView name;
    private TextView desc;

    public MesFragmentContactAdapter(ArrayList<Myfriendzzx> friends1, Context c) {
        this.friends1 = friends1;
        this.c = c;
    }

    @Override
    public int getCount() {
        return friends1.size();
    }

    @Override
    public Object getItem(int position) {
        return friends1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            convertView= LayoutInflater.from(c).inflate(R.layout.array_item_one,null);
        }
        img = (ImageView) convertView.findViewById(R.id.hearder1);
        img.setImageResource(friends1.get(position).getImg());
        name = (TextView) convertView.findViewById(R.id.name1);
        name.setText(friends1.get(position).getName().toString());

        return convertView;
    }
}
