package com.example.nianchen.normaluniversitytourgroup.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nianchen.normaluniversitytourgroup.BaseClass.FriendTwo;
import com.example.nianchen.normaluniversitytourgroup.R;
import com.example.nianchen.normaluniversitytourgroup.page_activity.GroupActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

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
    private ImageView img1;
    private TextView name;
    private TextView desc;
    private ImageView imgs;
    private String imgurl;
    private String mid;
    private ListView view1;
    public int scrollStates;
//    Handler h=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.i("11111111",msg.arg1+"");
//            Log.i("22222222", view1.getCount()+"");
//            ImageView img1=(ImageView)view1.getChildAt(msg.arg1).findViewById(R.id.left);
//            if(img1!=null)
//                img1.setImageDrawable((Drawable) msg.obj);
//        }
//    };


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

    private class ViewHolder{
        private TextView brandEnNameTv;
        private TextView brandChNameTv;
        private String followCheckBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null ){
            convertView= LayoutInflater.from(c).inflate(R.layout.array_item_two,null);//jiazaibujv

        }
        convertView.setTag(position);
        ProgressBar pb = (ProgressBar)convertView.findViewById(R.id.grid_progressBar);
        pb.setVisibility(View.VISIBLE);

        img=(ImageView) convertView.findViewById(R.id.left);
//        img.setImageResource(friends.get(position).getLeft());//fuzhi
        name=(TextView) convertView.findViewById(R.id.top);
        desc=(TextView)convertView.findViewById(R.id.bottom);
        imgs=(ImageView) convertView.findViewById(R.id.right);


            name.setText(friends.get(position).getTop().toString());
            desc.setText(friends.get(position).getBottom().toString());
            imgs.setImageResource(friends.get(position).getRight());
//        img.setImageResource(friends.get(position).getRight());
            img.setImageResource(R.mipmap.ic_launcher);
            imgurl = friends.get(position).getLeft();
            getimg(imgurl, position, friends.get(position).getMid(), convertView);

        return convertView;
    }

        private void getimg(final String url1, final int postion, final String tittle, final View convertView) {
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();



                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams rp2 = new RequestParams();
                    rp2.put("url", tittle);
                    client.get(url1, rp2, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                //创建工厂对象
                                BitmapFactory bitmapFactory = new BitmapFactory();
                                //工厂对象的decodeByteArray把字节转换成Bitmap对象
                                Bitmap bitmap = bitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                                //设置图片
//                                imageView.setImageBitmap(bitmap);
//                                Message msg = new Message();
//                                msg.obj = bitmap;
//                                msg.arg1 = postion;
                                if(scrollStates== AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                                    img1=(ImageView) view1.getChildAt(postion-view1.getFirstVisiblePosition()).findViewById(R.id.left);
                                }


                               img1.setImageBitmap(bitmap);

                                ProgressBar pb = (ProgressBar)convertView.findViewById(R.id.grid_progressBar);
                                pb.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              byte[] responseBody, Throwable error) {
                            error.printStackTrace();
                        }

                        @Override
                        public void onProgress(long bytesWritten, long totalSize) {
                            super.onProgress(bytesWritten, totalSize);
//                            int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
//                            // 上传进度显示
//                            progress.setProgress(count);
//                            Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
                        }
                    });

            }
//        }.start();
//    }

}
