package com.example.nianchen.normaluniversitytourgroup.page_activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendFive;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianchen on 2016/11/23.
 */
public class GalleryActivity extends Activity{
    private List<FriendFive> friends = new ArrayList<FriendFive>();
    private GalleryAdapter myadpter;
    private ImageView gallery;
    private ImageView image;
    private GridView grid;
    private int[] imageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiu_gallery);
        getdata();
        getid();
        myadpter = new GalleryAdapter(friends, GalleryActivity.this);
        grid.setAdapter(myadpter);
        getTouch();
    }

    private void getTouch() {

    }

    private void getdata() {
        friends.add(new FriendFive(R.drawable.y0));
        friends.add(new FriendFive(R.drawable.y1));
        friends.add(new FriendFive(R.drawable.y2));
        friends.add(new FriendFive(R.drawable.y3));
        friends.add(new FriendFive(R.drawable.y4));
        friends.add(new FriendFive(R.drawable.y5));
        friends.add(new FriendFive(R.drawable.y6));
        friends.add(new FriendFive(R.drawable.y7));
        friends.add(new FriendFive(R.drawable.y8));
        friends.add(new FriendFive(R.drawable.y9));
        friends.add(new FriendFive(R.drawable.y0));
        friends.add(new FriendFive(R.drawable.y1));
        friends.add(new FriendFive(R.drawable.y2));
        friends.add(new FriendFive(R.drawable.y3));
        friends.add(new FriendFive(R.drawable.y4));
        friends.add(new FriendFive(R.drawable.y5));
        friends.add(new FriendFive(R.drawable.y6));
        friends.add(new FriendFive(R.drawable.y7));
        friends.add(new FriendFive(R.drawable.y8));
        friends.add(new FriendFive(R.drawable.y9));
        friends.add(new FriendFive(R.drawable.y0));
        friends.add(new FriendFive(R.drawable.y1));
        friends.add(new FriendFive(R.drawable.y2));
        friends.add(new FriendFive(R.drawable.y3));
        friends.add(new FriendFive(R.drawable.y4));
        friends.add(new FriendFive(R.drawable.y5));
        friends.add(new FriendFive(R.drawable.y6));
        friends.add(new FriendFive(R.drawable.y7));
        friends.add(new FriendFive(R.drawable.y8));
        friends.add(new FriendFive(R.drawable.y9));

    }

    private void getid() {
        grid = (GridView) findViewById(R.id.gallery);
        gallery = (ImageView)findViewById(R.id.imag);
        image = (ImageView)findViewById(R.id.mautos);
    }
    public void onPause(){
        super.onPause();
        friends.clear();
    }
}
