package com.qf.servicedemo;

import com.qf.servicedemo.BindService.MyBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends Activity {

	private MyBinder myBinder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}

	private int a = 0;

	public void onClick(View view) {
		Intent intent = new Intent(this, BindService.class);
		bindService(intent, new MyServiceConnection(), Context.BIND_AUTO_CREATE);

	}

	public void onClick1(View view) {
		// Intent intent=new Intent(this, BindService.class);
		// bindService(intent, new MyServiceConnection(),
		// Context.BIND_AUTO_CREATE);
		TextView textView = (TextView) view;
		a++;
		textView.setText(myBinder.getInt(a) + "");

	}

	class MyServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myBinder = (MyBinder) service;
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

	}

}
