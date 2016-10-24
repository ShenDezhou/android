package com.rock.androidnetdemo.http;

import com.android.volley.Request.Method;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;

public class HttpUtils {

	private static RequestQueue queue;
	
	public static void init(Context context){
		// 初始化Volley
		queue = Volley.newRequestQueue(context);
	}
	
	
	public static void get(String url,HttpResponse response){
		// StringRequest实例化 三个参数① url请求地址， ② 请求成功的回调 ③ 请求错误的回调
		StringRequest request = new StringRequest(url, response.getResponseListener() , response.getErrorListener());
		// 将请求添加到请求队列中
		queue.add(request);
	
	}
	
	public static void getByTag(String url,String tag,HttpResponse response){
		// 不想要已经缓存的网络请求结果，就先取消掉，然后重新实例化去请求
		queue.cancelAll(tag);
		// StringRequest实例化 三个参数① url请求地址， ② 请求成功的回调 ③ 请求错误的回调
		StringRequest request = new StringRequest(url, response.getResponseListener() , response.getErrorListener());
		// 添加标记
		request.setTag(tag);
		// 将请求添加到请求队列中
		queue.add(request);
	
	}
	
	public static void post(String url,HttpResponse response,final Map<String, String> params){
		// Post的StringRequest构建 四个参数 ① 请求方式 ② 请求的url地址 ③ 我们请求成功的回调  ④ 请求错误的回调
		// 重写了StringRequest的getParams方法，将我们传递进来的Map返回
		StringRequest request = new StringRequest(Method.POST, url, response.getResponseListener(), response.getErrorListener()){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		// 添加到Request中
		queue.add(request);
		
	}
	// 根据TAG取消所有请求
	public static void cancleByTag(String tag){
		queue.cancelAll(tag);
	}
	
	
}
