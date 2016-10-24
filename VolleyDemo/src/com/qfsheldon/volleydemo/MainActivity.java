package com.qfsheldon.volleydemo;

import org.apache.http.protocol.ResponseContent;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RequestQueue requestQueue = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取requestQueue实例
		requestQueue = Volley.newRequestQueue(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view){
//		//定义网络请求
//		StringRequest stringRequest=new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
//			//表示请求成功的回调
//			@Override
//			public void onResponse(String response) {
//				// TODO Auto-generated method stub
//				Log.i("baidu", response);
//			}
//		}, new Response.ErrorListener() {
//			//表示请求失败的回调
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
//			}
//		});
//		//开启网路请求
//		requestQueue.add(stringRequest);
		
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest("http://m2.qiushibaike.com/article/list/suggest?page=1", null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				Log.i("qiubai", response.toString());
			}

		
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
			}
		});
		requestQueue.add(jsonObjectRequest);
		
		
	}
}
