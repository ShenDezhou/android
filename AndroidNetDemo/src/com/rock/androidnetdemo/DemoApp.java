package com.rock.androidnetdemo;

import com.rock.androidnetdemo.http.HttpUtils;

import android.app.Application;
import android.content.Context;
/**
 * 如果是自己定义的Application
 * 我们需要在清单文件中进行注册
 *
 */
public class DemoApp extends Application{
	
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		// 初始化我们的网络请求工具
		HttpUtils.init(this);
	}
	
	public static Context getApp(){
		return context;
	}

}
