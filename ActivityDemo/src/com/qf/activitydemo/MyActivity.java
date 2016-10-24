package com.qf.activitydemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

	private TextView textView = null;

	private void initView() {
		textView = (TextView) findViewById(R.id.textView_my_itemNo);
	}

	// public static int itemNo=-1;
	// private MyApp myApp=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initView();
		// itemNo=3;
		// myApp=(MyApp)getApplication();
		// myApp.setItemNo(3);
		// getIntent()
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int itemNo = bundle.getInt("itemNo", -1);
		textView.setText(itemNo + "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}

	// int flag=0;
	// 它的返回值表示是否消费事件。
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// back
			Toast.makeText(this, "按了back了", Toast.LENGTH_SHORT).show();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
