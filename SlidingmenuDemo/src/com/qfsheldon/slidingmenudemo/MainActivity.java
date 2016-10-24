package com.qfsheldon.slidingmenudemo;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private SlidingMenu slidingMenu = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取slidingmenu的对象
		slidingMenu = new SlidingMenu(this);
		// 从左边划进来
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 设置slidingmenu的布局
		slidingMenu.setMenu(R.layout.slidingmenu);
		// //设置slidingmenu的第二个布局
		// slidingMenu.setSecondaryMenu(R.layout.activity_main);
		// 设置slidingmenu的显示方式
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置菜单显示宽度：
		slidingMenu.setBehindWidth(300); // 单位：px
		// 划进来的效果
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);

		// //把抽屉打开
		// slidingMenu.showMenu();
		// slidingMenu.close

		// 设置菜单与内容边缘的阴影效果
		slidingMenu.setShadowWidth(1);
		slidingMenu.setShadowDrawable(getResources().getDrawable(
				R.drawable.ic_launcher));
		
		
		slidingMenu.setOnOpenListener(new OnOpenListener() {
			
			@Override
			public void onOpen() {
				// TODO Auto-generated method stub
				Log.i("OnOpenListener", "执行了");
			}
		});
		slidingMenu.setOnOpenedListener(new OnOpenedListener() {
			
			@Override
			public void onOpened() {
				// TODO Auto-generated method stub
				Log.i("OnOpenedListener", "执行了");
			}
		});
		slidingMenu.setOnCloseListener(new OnCloseListener() {
			
			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				Log.i("OnCloseListener", "执行了");
			}
		});
		slidingMenu.setOnClosedListener(new OnClosedListener() {
			
			@Override
			public void onClosed() {
				// TODO Auto-generated method stub
				Log.i("OnClosedListener", "执行了");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		// 调用此方法，动态判断抽屉的状态（打开-关闭），然后去执行（关闭-打开）。
		slidingMenu.toggle();
	}

}
