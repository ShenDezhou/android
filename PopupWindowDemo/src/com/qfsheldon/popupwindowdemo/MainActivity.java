package com.qfsheldon.popupwindowdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {

	private PopupWindow popupWindow = null;
	private LinearLayout layout = null;
	private View contentView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow,
				null);
		layout = (LinearLayout) contentView.findViewById(R.id.linearLayout);
		popupWindow = new PopupWindow(contentView,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		if (!popupWindow.isShowing()) {
			// 如果popupwindow没显示出来，就show，在最下面，偏移0,0。
			popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
		} else {
			// 如果popupwindow显示出来了，就把他弄消失。
			popupWindow.dismiss();
		}
	}

	public void onPopupWindowClick(View view){
		Toast.makeText(this, ""+layout.indexOfChild(view), Toast.LENGTH_SHORT).show();
	}
}
