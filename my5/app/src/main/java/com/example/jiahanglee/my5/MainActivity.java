package com.example.jiahanglee.my5;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private static String abc;
    private Button select,upload;
    private ImageView img;
    private EditText edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inint();
//        select.setOnClickListener(abc);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent1,1);

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 =new Intent();
//                intent1.putExtra("q",abc);

//                startActivity(new Intent(MainActivity.this,newd.class));
                   up_load(abc);
            }
        });
    }
    public void inint(){
        select = (Button)findViewById(R.id.Select);
        upload = (Button)findViewById(R.id.Upload);
        img= (ImageView)findViewById(R.id.Img);
        edit= (EditText)findViewById(R.id.Edit);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            Uri uri = data.getData();

            abc=GalleryUtil.getPath(MainActivity.this,uri);
            edit.setText(abc);
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                img.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("wofu","onActivityResult已运行");
    }

    public void up_load(String path) {
        if (TextUtils.isEmpty(path.trim())) {
            Toast.makeText(this, "上次文件路径不能为空", Toast.LENGTH_LONG).show();
        } else {
            Log.e("dfdfdf",path);
            //异步的客户端对象
            AsyncHttpClient client = new AsyncHttpClient();
            //指定url路径
            String url = "http://jiahanglee.cn/blog/upLoad";
            //封装文件上传的参数
            RequestParams params = new RequestParams();
            //根据路径创建文件
            File file = new File(path);
            try {
                //放入文件
                params.put("profile_picture", file);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("文件不存在----------");
            }
            //执行post请求
            client.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      byte[] responseBody) {
                    if (statusCode == 200) {
                        Toast.makeText(getApplicationContext(), "上次成功", Toast.LENGTH_LONG)
                                .show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      byte[] responseBody, Throwable error) {
                    error.printStackTrace();
                }
            });

        }

    }
}
