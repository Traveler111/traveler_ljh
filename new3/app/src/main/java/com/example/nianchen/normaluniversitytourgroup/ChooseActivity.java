package com.example.nianchen.normaluniversitytourgroup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nianchen.normaluniversitytourgroup.adapter.UploadImageAdapter;
import com.example.nianchen.normaluniversitytourgroup.utils.ImageUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ChooseActivity extends BaseActivity {
	private EditText edit;
	private TextView textView;
//	public  static String[] abc = new String[2];
	public  static List<String> abc= new ArrayList<String>();
	/**
	 * 需要上传的图片路径  控制默认图片在最后面需要用LinkedList
	 */
	private LinkedList<String> dataList = new LinkedList<String>();
	/**
	 * 图片上传GridView
	 */
	private GridView uploadGridView;
	/**
	 * 图片上传Adapter
	 */
	private UploadImageAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
//		findViewById(R.id.btn_change_fragment).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(ChooseActivity.this,ChooseFragmentActivity.class));
//			}
//		});
		uploadGridView = (GridView) findViewById(R.id.grid_upload_pictures);
		edit = (EditText)findViewById(R.id.EditDetal);
		textView = (TextView)findViewById(R.id.tv);
		dataList.addLast(null);// 初始化第一个添加按钮数据
		adapter = new UploadImageAdapter(this, dataList);
		uploadGridView.setAdapter(adapter);
		uploadGridView.setOnItemClickListener(mItemClick);
		uploadGridView.setOnItemLongClickListener(mItemLongClick);

		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("dfdfdfdf",edit.getText().toString());
//				up_load(edit.getText().toString());
				for(int k=0;k<abc.size();k++)
				{
					up_load(abc.get(k));
				}
				dataList.clear();
				uploadGridView = (GridView) findViewById(R.id.grid_upload_pictures);
				edit = (EditText)findViewById(R.id.EditDetal);
				textView = (TextView)findViewById(R.id.tv);
				dataList.addLast(null);// 初始化第一个添加按钮数据
				adapter = new UploadImageAdapter(ChooseActivity.this, dataList);
				uploadGridView.setAdapter(adapter);
				uploadGridView.setOnItemClickListener(mItemClick);
				uploadGridView.setOnItemLongClickListener(mItemLongClick);
				edit.setText("");
				abc.clear();
			}
		});
	}
	
	/**
	 * 上传图片GridView Item单击监听
	 */
	private OnItemClickListener mItemClick = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			if(parent.getItemAtPosition(position) == null){ // 添加图片
				//showPictureDailog();//Dialog形式
				showPicturePopupWindow();//PopupWindow形式
			}
		}
	};
	/**
	 * 点击事件
	 */
	/**
	 * 上传图片GridView Item长按监听
	 */
	private OnItemLongClickListener mItemLongClick = new OnItemLongClickListener(){

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
									   int position, long id) {
			if(parent.getItemAtPosition(position) != null){ // 长按删除
				dataList.remove(parent.getItemAtPosition(position));
				adapter.update(dataList); // 刷新图片
			}
			return true;
		}
	};
	
	@Override
	protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK){
			String imagePath = "";
			if(data != null && data.getData() != null){//有数据返回直接使用返回的图片地址
				imagePath = ImageUtils.getFilePathByFileUri(this, data.getData());
//		这里有疑问		abc[i]=imagePath;
				Log.e("dfdfdfdfdfdfdfd","有数据返回的----图库");
				edit.setText(getFileName(imagePath));
				abc.add(imagePath);

			}else{//无数据使用指定的图片路径
				imagePath = mImagePath;
				edit.setText(getFileName(imagePath));
				abc.add(imagePath);

				Log.e("dfdfdfdfdfdfdfd","没有数据返回的----相机");
			}

			dataList.addFirst(imagePath);
			adapter.update(dataList); // 刷新图片
		}
	}
	public void up_load(String path) {

		if (TextUtils.isEmpty(path.trim())) {
			Toast.makeText(this, "上次文件路径不能为空", Toast.LENGTH_LONG).show();
		} else {

			//
			final ProgressDialog dialog = new ProgressDialog(this);// ****************************************************
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
			dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
			dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
			dialog.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的
			dialog.setTitle("提示");
			dialog.setMax(100);
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			dialog.setMessage("上传进程");
			dialog.show();
			//*****************************************************************************
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
		/////////////////		//该处获取用户换新 id
				params.put("username",1);
				params.put("imageurl","C:\\Users\\Administrator\\Desktop\\upload\\"+getFileName(path));

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
						Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT)
								.show();
						/////////////////////////////////////
						dialog.setProgress(100);

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

					int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
					// 上传进度显示
					dialog.setProgress(count);

					Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
				}
				
			});

		}

	}
public void dialog(){
	final ProgressDialog dialog = new ProgressDialog(this);
	dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
	dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
	dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
	dialog.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的
	dialog.setTitle("提示");
	dialog.setMax(100);
	dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
	dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
	dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
	dialog.setMessage("这是一个水平进度条");
	dialog.show();
}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		abc.clear();
	}


	public String getFileName(String pathandname){

		int start=pathandname.lastIndexOf("/");
		int end=pathandname.lastIndexOf(".");
		if(start!=-1 && end!=-1){
			return pathandname.substring(start+1,end);
		}else{
			return null;
		}

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
