package com.example.jiahanglee.tututu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static android.R.id.progress;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imag);

        String url1 = "http://jiahanglee.cn/blog/downFile";

//第一种方法 之 下载显示

//        AsyncHttpClient client = new AsyncHttpClient();
//        String[] allowedContentTypes = new String[]{"image/png", "image/jpeg"};
//        RequestParams rp2 = new RequestParams();
//        rp2.put("Title", "a");
//        client.get(url1, new BinaryHttpResponseHandler(allowedContentTypes) {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers,
//                                  byte[] binaryData) {
//                String tempPath = Environment.getExternalStorageDirectory()
//                        .getPath() + "/temp.jpg";
//                // TODO Auto-generated method stub
//                // 下载成功后需要做的工作
////                progress.setProgress(0);
//                //
//                Log.e("binaryData:", "共下载了：" + binaryData.length);
//                //
//
//                Bitmap bmp = BitmapFactory.decodeByteArray(binaryData, 0,
//                        binaryData.length);
//                imageView.setImageBitmap(bmp);
//
//
//                    Toast.makeText(MainActivity.this, "下载成功\n" + tempPath,
//                            Toast.LENGTH_LONG).show();
//                    Log.e("sdsd",tempPath);
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  byte[] binaryData, Throwable error) {
//                // TODO Auto-generated method stub
//                Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_LONG).show();
//            }
//
//
//
//            @Override
//            public void onRetry(int retryNo) {
//                // TODO Auto-generated method stub
//                super.onRetry(retryNo);
//                // 返回重试次数
//            }
//
//        });
        //第一种方法结束
//第二种方法实验
        AsyncHttpClient client= new AsyncHttpClient();
        RequestParams rp2 = new RequestParams();
        rp2.put("url", "c");
        client.get(url1,rp2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    //创建工厂对象
                    BitmapFactory bitmapFactory = new BitmapFactory();
                    //工厂对象的decodeByteArray把字节转换成Bitmap对象
                    Bitmap bitmap = bitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                    //设置图片
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
//第二种方法实验结束
    }

}
