package com.qf.bluetoothdemo;

import java.util.UUID;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	public static BluetoothAdapter bluetoothAdapter = null;
	public static String name = null;
	// 表示两个安卓设备链接
	public static UUID uuid = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		name = getPackageName();
		// 获取到蓝牙适配器
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter != null) {
			if (!bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.enable();
			}
			setTitle("存在蓝牙");
		}
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		// Server
		Intent intent = new Intent(this, ServerActivity.class);
		startActivity(intent);
	}

	public void onClick1(View view) {
		// Client
		Intent intent = new Intent(this, ClientSelectActivity.class);
		startActivity(intent);
	}

}
