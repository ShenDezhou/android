package com.qf.bluetoothdemo;

import java.io.IOException;
import java.util.Set;

import android.os.Bundle;
import android.R.array;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ClientSelectActivity extends Activity {

	private ListView listView = null;
	private ArrayAdapter<String> arrayAdapter = null;
	private Set<BluetoothDevice> bluetoothDevices = null;

	public static BluetoothSocket bluetoothSocket = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_select);
		listView = (ListView) findViewById(R.id.listView);
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.item,
				R.id.textView_item);

		

		bluetoothDevices = MainActivity.bluetoothAdapter.getBondedDevices();

		for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
			arrayAdapter.add(bluetoothDevice.getName()
					+ bluetoothDevice.getAddress() + "已配对");
		}
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view
						.findViewById(R.id.textView_item);
				String data = textView.getText() + "";
				for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
					if (data.equals(bluetoothDevice.getName()
							+ bluetoothDevice.getAddress() + "已配对")) {
						try {
							bluetoothSocket = bluetoothDevice
									.createRfcommSocketToServiceRecord(MainActivity.uuid);
							Intent intent = new Intent(
									ClientSelectActivity.this,
									ClientActivity.class);
							startActivity(intent);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_select, menu);
		return true;
	}

}
