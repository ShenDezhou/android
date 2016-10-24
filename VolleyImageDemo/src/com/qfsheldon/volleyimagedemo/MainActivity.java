package com.qfsheldon.volleyimagedemo;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RequestQueue requestQueue=null;
	private ImageView imageView=null;
	private void initView(){
		imageView=(ImageView) findViewById(R.id.imageView_main);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
		
		requestQueue=Volley.newRequestQueue(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onClick(View view){
		//第三个参数和第四个参数是宽高，第五个参数是图片像素点的格式，都是二次采样的参数
		ImageRequest imageRequest=new ImageRequest("http://www.baidu.com/img/bd_logo.png", new Listener<Bitmap>() {
			//返回结果正常，也就是请求成功
			@Override
			public void onResponse(Bitmap response) {
				// TODO Auto-generated method stub
				imageView.setImageBitmap(response);
			}
		}, 100, 100, Config.RGB_565, new ErrorListener() {
			//请求失败
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "图片请求失败", Toast.LENGTH_SHORT).show();
			}
		});
		requestQueue.add(imageRequest);
		
		
		
		
	}
	
	
	
}
