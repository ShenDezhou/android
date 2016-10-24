package com.rock.androidnetdemo;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rock.androidnetdemo.http.HttpResponse;
import com.rock.androidnetdemo.http.HttpUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Volley的使用：
 * Volley的核心RequestQueue
 * RequestQueue实例化Volley.newRequestQueue(Context) Volley会根据实际运行环境生成不同的网络请求
 * API 8 及以下 它HttpClient构建请求 以上的 会通过HttpUrlConnection 来构建网络请求
 * 
 * 使用的时候需要向RequestQueue中添加请求Request
 * Request 默认提供三种实现 StringRequest ImageRequest JsonObjectRequest
 * 
 * 实例化一个StringRequest
 * 里面有四个参数： ① 请求方式（GET，POST...） ② 请求的地址URL
 * 				  ③ 我们请求成功的回调监听    ④ 请求错误的回调监听
 * 
 * 将我们的Request添加到请求队列中
 * 
 * 我们需要对RequestQueue进行一个简单地优化
 * 优化的方式就是 使RequestQueue变成单例 
 * 我们自定义Application，在onCreate中对请求队列进行初始化
 * 
 * Application使用需要注意： 继承系统的Application，我们需要在清单文件中进行注册
 * 
 * 对请求成功的接口和请求错误的接口进行一个简单地包装
 * 是通过自定义一个抽象类，抽象类包含两个请求成功的接口以及请求错误的接口
 * 并且提供两个接口的内部实现 
 * 定义两个抽象方法，分别用来接收请求成功和请求错误的返回值
 * 
 * 定义成抽象的原因： 我们需要服务器的反馈，需要根据不同的结果进行下一步的操作
 * 
 * 
 *
 */


public class VolleyActivity extends Activity implements OnClickListener {

	private static final String TAG = VolleyActivity.class.getSimpleName();
	
	private Button volleyGet;
	
	private Button httpUtilsGet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		//
		volleyGet = (Button) this.findViewById(R.id.volley_get);
		//
		volleyGet.setOnClickListener(this);
		//
		httpUtilsGet = (Button) this.findViewById(R.id.volley_util);
		//
		httpUtilsGet.setOnClickListener(this);
	}
	/**
	 * 在页面销毁的时候，将页面通过TAG发送的所有请求取消掉
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		HttpUtils.cancleByTag(TAG);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.volley_get:
			// Volley核心,请求队列
			RequestQueue queue = Volley.newRequestQueue(this);
			
			String url = "http://192.168.57.1:9999/user/list?currPage=1&pageSize=10";
			
			StringRequest stringRequest = new StringRequest(url, new Listener<String>() {
				// 请求成功的回调
				@Override
				public void onResponse(String result) {
					// 主线程中
					Log.e(TAG, "result--" + result);
					
				}
			}, new ErrorListener() {
				// 请求错误的回调
				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					Log.e(TAG, "error--" + error.getMessage());
				}
			}){
				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					// 传递参数，用Map来进行包装
					
					
					return super.getParams();
				}
			};
			// 将请求添加到请求队列中
			queue.add(stringRequest);
			
			break;
		case R.id.volley_util:
			
			String url1 = "http://192.168.57.1:9999/user/list?currPage=1&pageSize=10";
			
			HttpUtils.getByTag(url1,TAG,new HttpResponse() {
				
				@Override
				public void onSucceed(String result) {
					// TODO Auto-generated method stub
					Log.e(TAG, "result--"+result);
					Log.e(TAG, "threadName--" + Thread.currentThread().getName());
				}
				
				@Override
				public void onError(VolleyError error) {
					// TODO Auto-generated method stub
					Log.e(TAG, "error--"+error.getMessage());
				}
			});
			break;
		}
		
	}
	
	
	
	
}
