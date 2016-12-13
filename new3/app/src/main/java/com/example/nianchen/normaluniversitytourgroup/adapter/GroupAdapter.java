package com.example.nianchen.normaluniversitytourgroup.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendTwo;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by nianchen on 2016/11/24.
 */
public class GroupAdapter extends BaseAdapter {
    private List <FriendTwo> friends;
    private Context c;
    private ImageView img;
    private TextView name;
    private TextView desc;
    private ImageView imgs;
    private String imgurl;
    private String mid;
    private ListView view1;

    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("11111111",msg.arg1+"");
            Log.i("22222222", view1.getCount()+"");
            ImageView img1=(ImageView)view1.getChildAt(msg.arg1).findViewById(R.id.left);
            if(img1!=null)
                img1.setImageDrawable((Drawable) msg.obj);
        }
    };


    public GroupAdapter(Context c, List<FriendTwo> friends, ListView view1) {
        this.c = c;
        this.friends = friends;
        this.view1=view1;
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
            convertView= LayoutInflater.from(c).inflate(R.layout.array_item_two,null);//jiazaibujv
        }

        img=(ImageView) convertView.findViewById(R.id.left);
//        img.setImageResource(friends.get(position).getLeft());//fuzhi
        imgurl=friends.get(position).getLeft();
        getimg(imgurl,position);
        name=(TextView) convertView.findViewById(R.id.top);
        name.setText(friends.get(position).getTop().toString());
        desc=(TextView)convertView.findViewById(R.id.bottom);
        desc.setText(friends.get(position).getBottom().toString());
        imgs=(ImageView) convertView.findViewById(R.id.right);
        imgs.setImageResource(friends.get(position).getRight());
        return convertView;
    }

    private void getimg(final String url1, final int postion) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {


                    URL url=new URL(url1);
                    InputStream in=url.openStream();
                    Drawable draw=Drawable.createFromStream(in,"image.png");
                    Message msg=new Message();
                    msg.obj=draw;
                    msg.arg1=postion;
                    h.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
