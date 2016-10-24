package com.qfsheldon.windowmanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static WindowManager windowManager;
	private WindowManager.LayoutParams lp;
	/**
	 * 用于添加到悬浮窗当中的。注意，不要重复添加
	 */
	private static TextView txtInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Context applicationContext = getApplicationContext();
		if (windowManager == null) {
			windowManager = (WindowManager) applicationContext
					.getSystemService(WINDOW_SERVICE);
		}
		// 如何删除悬浮窗
		if (txtInfo != null) {
			// 删除悬浮窗
			windowManager.removeView(txtInfo);
			txtInfo = null;
		} else {// 当前悬浮窗没有创建，就进行初始化

			// 准备 悬浮窗内容, 使用 Application Context 创建
			txtInfo = new TextView(applicationContext);
			txtInfo.setText("123458876");
			// txtInfo.setText("宜: 开发，编码\n忌: 迟到, 文件名缓存");

			// txtInfo.setTextColor(Color.RED);

			
			// TextView 支持带有单位的 尺寸 例如 10sp
			txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

			// 以下需要准备 添加到 桌面悬浮的设置项

			lp = new WindowManager.LayoutParams();

			// type 属性，控制悬浮窗显示的方式和类型，
			// 例如：TYPE_APPLICATION 只在应用程序内部显示
			// TYPE_SYSTEM_OVERLAY 可以实现系统桌面的悬浮
			// TYPE_SYSTEM_ALERT 控制可以移动
			lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
					| WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

			// 所有 TYPE_SYSTEM 级别的悬浮窗，如果希望原来的或者应用程序可以进行操作
			// 那么必须设置 flag 属性，并且标注，不使用焦点。

			// FLAG_NOT_FOCUSABLE 代表这个悬浮窗不获取焦点
			lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_FULLSCREEN;

			// x 代表当前的悬浮窗显示在界面上的哪一个位置
			// 通常 x 取值范围 从 0 到 屏幕宽度
			lp.x = 10;

			// y 代表当前的悬浮窗显示在界面上的哪一个位置
			// y的取值范围 从 0 到 屏幕高度，
			lp.y = 50;

			// 比重，用于设置悬浮窗的起始位置
			lp.gravity = Gravity.LEFT | Gravity.TOP;

			// 宽高会影响窗口的大小，默认全屏，因此必须设置尺寸才可以。
			lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

			// 设置悬浮窗背景显示方式，如果不设置，默认就是黑色

			lp.format = PixelFormat.TRANSPARENT;

			// addView 添加悬浮窗的时候，一定要使用 WindowManager.LayoutParams
			// 使用这个类型，能够控制悬浮窗的各种效果。
			windowManager.addView(txtInfo, lp);

			// //////// 设置悬浮窗点击的事件监听

			txtInfo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(applicationContext,
							MainActivity.class);

					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);

					applicationContext.startActivity(intent);
				}
			});

			// //////// TextView 添加 触摸事件，注意是内部类，不要用this了
			txtInfo.setOnTouchListener(new View.OnTouchListener() {

				private WindowManager.LayoutParams myLP = lp;

				private int lastX, lastY;
				/**
				 * ACTION_DOWN 发生的时间
				 */
				private long lastDownTime;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					boolean ret = false;

					int action = event.getAction();

					switch (action) {
					case MotionEvent.ACTION_DOWN:
						lastDownTime = System.currentTimeMillis();
						String s = txtInfo.getText().toString();

						Toast.makeText(applicationContext, s, Toast.LENGTH_LONG)
								.show();

						lastX = (int) (event.getRawX());
						lastY = (int) (event.getRawY());
						ret = true;
						break;

					case MotionEvent.ACTION_MOVE:

						// 触摸事件 就有了 x, y

						// 获取手机屏幕上的位置，不算控件的，因为控件上 getX, getY 是相对的
						// 因为悬浮窗坐标是按照手机屏幕尺寸来设置的。
						float x = event.getRawX();
						float y = event.getRawY();

						int ix = (int) (x - lastX);
						int iy = (int) (y - lastY);

						// 设置悬浮窗的新位置
						myLP.x += ix;
						myLP.y += iy;

						lastX = (int) x;
						lastY = (int) y;

						// 设置完位置，需要更新窗口
						windowManager.updateViewLayout(txtInfo, myLP);

						break;
					case MotionEvent.ACTION_UP:
						long ct = System.currentTimeMillis();

						if (ct - lastDownTime <= 300) {
							// TODO 触发 onClick的点击
							txtInfo.performClick();
						}

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
