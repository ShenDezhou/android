package com.qf.threaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button button = null;
	// 句柄
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (msg.arg1 == 0) {
					button.setTextColor(0xff000000);
					button.setClickable(true);
					button.setText("重新获取");
				} else {
					button.setText("还剩" + msg.arg1 + "秒");
				}

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		button.setTextColor(0xff333333);
		button.setClickable(false);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Looper.prepare();
				//
				// //可以实例handler
				// Handler handler=new Handler(){
				// @Override
				// public void handleMessage(Message msg) {
				// // TODO Auto-generated method stub
				// super.handleMessage(msg);
				// }
				// };
				//
				// Looper.loop();
				
				
				
				
				for (int i = 5; i >= 0; i--) {
					// 是一个不使用的方式
					// Message message=new Message();
					Message message = Message.obtain();
					message.what = 1;
					message.arg1 = i;
					handler.sendMessage(message);

					sleep(1000);
				}
			}
		}).start();
	}

	private void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
