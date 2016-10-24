package com.qf.activitydemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	// 做一些界面初始化的工作
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MainActivity", "onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 这个方法是activity被使用的第一个特征
	// 设置一些界面的值
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "onStart");
		super.onStart();

	}

	// activity，在第二次或第二次以后启动它，都会回调此方法
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "onRestart");
		super.onRestart();
	}

	// 此方法可以描述为，Activity丧失用户焦点的第一个特征
	//
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "onPause");
		super.onPause();
	}

	// 暂停回调结束后，就会回调它
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "onStop");
		super.onStop();
	}

	// 作为销毁的第一个特征
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "onDestroy");
		super.onDestroy();
	}

	// 作为显示出来的第一个特征
	// 设置界面的值
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("MainActivity", "onResume");
		super.onResume();
	}

	public void onClick(View view) {
		Intent intent = new Intent(MainActivity.this, MyActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("itemNo", 3);

		intent.putExtras(bundle);
		startActivity(intent);
	}

}
