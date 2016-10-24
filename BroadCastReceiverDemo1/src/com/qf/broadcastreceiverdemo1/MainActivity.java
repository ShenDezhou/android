package com.qf.broadcastreceiverdemo1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

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

	public void onClick(View view) {
		Intent intent = new Intent("com.qf.broadcastreceiverdemo1.guangbo");
		Bundle bundle = new Bundle();
		bundle.putInt("iop", 123);
		intent.putExtras(bundle);
		// sendBroadcast(intent);
		sendOrderedBroadcast(intent, null);
	}

}
