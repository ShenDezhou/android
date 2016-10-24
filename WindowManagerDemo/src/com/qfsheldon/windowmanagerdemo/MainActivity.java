package com.qfsheldon.windowmanagerdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private WindowManager windowManager = null;
	private Context context = null;
	private WindowManager.LayoutParams lp = null;
	private static TextView textView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();

		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		lp = new LayoutParams();
		// 设置成在屏幕悬浮窗的效果了。overlay使得你可以移动它
		lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
				| WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

		// 不获取焦点，不全屏。
		lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;

		// 设置位置
		lp.x = 10;
		lp.y = 50;

		lp.gravity = Gravity.LEFT | Gravity.TOP;

		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		// 悬浮窗一般都是透明效果的，因为怕影响观看桌面的应用
		lp.format = PixelFormat.TRANSPARENT;

		if (textView == null) {
			textView = new TextView(context);
			// 设计给textView也没有用，因为一会这个textView会交给windowmanager来管理
			// textView.setLayoutParams(lp);
			textView.setText("112345789");
			windowManager.addView(textView, lp);
		} else {
			windowManager.removeView(textView);
			textView = null;

		}
		if (textView != null) {
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, WelcomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			textView.setOnTouchListener(new OnTouchListener() {

				private WindowManager.LayoutParams mlp = lp;
				private int lastX;
				private int lastY;
				private long lastDownTime;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					boolean ret = false;

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						lastDownTime = System.currentTimeMillis();
						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();
						ret = true;
						break;
					case MotionEvent.ACTION_MOVE:
						float x = event.getRawX();
						float y = event.getRawY();
						int ix = (int) (x - lastX);
						int iy = (int) (y - lastY);
						mlp.x += ix;
						mlp.y += iy;
						lastX = (int) x;
						lastY = (int) y;
						windowManager.updateViewLayout(textView, mlp);
						break;

					case MotionEvent.ACTION_UP:
						long ct=System.currentTimeMillis();
						if(ct-lastDownTime<300){
							//触发点击
							textView.performClick();
						}
						break;

					default:
						break;
					}

					return ret;
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
