package com.qf.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

//被启动的service
public class MyService extends Service {


	// 被启动的service它不使用这个方法
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	// 它是在创建的时候回调
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i("MyService", "onCreate");

		super.onCreate();
	}

	// 它是在启动的时候回调
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		Bundle bundle=intent.getExtras();
		int a=bundle.getInt("itemNo");
		Log.i("MyService", "onStartCommand" + ":" + a);
		if(a==3){
			stopSelf();
		}
		
		return super.onStartCommand(intent, flags, startId);
	}

	// 它是在销毁的时候回调
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("MyService", "onDestroy");
		super.onDestroy();
	}

}
