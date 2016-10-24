package com.qf.bluetoothdemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.qf.bluetoothdemo.ServerActivity.WriteThread;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ClientActivity extends Activity {

	private TextView textView = null;

	private EditText editText = null;

	private DataInputStream datainputStream = null;

	private DataOutputStream dataoutputStream = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);
		editText = (EditText) findViewById(R.id.editText);
		textView = (TextView) findViewById(R.id.textView);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ClientSelectActivity.bluetoothSocket.connect();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							ClientActivity.this.setTitle("链接成功");
						}
					});

					datainputStream = new DataInputStream(
							ClientSelectActivity.bluetoothSocket
									.getInputStream());
					dataoutputStream = new DataOutputStream(
							ClientSelectActivity.bluetoothSocket
									.getOutputStream());
					new Thread(new ReadThread()).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void append(final String data) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				textView.append(data);
			}
		});
	}

	class ReadThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					String data = datainputStream.readUTF();
					data = ClientSelectActivity.bluetoothSocket
							.getRemoteDevice().getName() + "说：" + data + "\n";
					append(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public void send(View view) {
		new Thread(new WriteThread()).start();
	}

	class WriteThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {

				dataoutputStream.writeUTF(editText.getText() + "");
				append(MainActivity.bluetoothAdapter.getName() + "说："
						+ editText.getText() + "\n");

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						editText.setText("");

					}
				});

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}

}
