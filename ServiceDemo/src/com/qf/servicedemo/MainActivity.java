package com.qf.servicedemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private Intent intent =null;
	private int a=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onClick(View view){
		
		intent=new Intent(this, MyService.class);
		Bundle bundle=new Bundle();
		bundle.putInt("itemNo", a++);
		intent.putExtras(bundle);
		startService(intent);
	}
	public void onClick1(View view){
		
		intent=new Intent(this, MyService.class);
		
		Bundle bundle=new Bundle();
		bundle.putInt("itemNo", a++);
		intent.putExtras(bundle);
		stopService(intent);
	}
	

}
