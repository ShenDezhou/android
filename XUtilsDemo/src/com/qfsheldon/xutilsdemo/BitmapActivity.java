package com.qfsheldon.xutilsdemo;

import java.io.File;

import com.lidroid.xutils.BitmapUtils;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class BitmapActivity extends Activity {

	private ImageView imageView = null;
	private BitmapUtils bitmapUtils = null;

	private void initView() {
		imageView = (ImageView) findViewById(R.id.imageView_main);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitmap);
		initView();
		bitmapUtils = new BitmapUtils(this);
	}

	// 资产

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bitmap, menu);
		return true;
	}

	public void onClick(View view) {
//
//		bitmapUtils
//				.display(
//						imageView,
//						Environment
//								.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//								+ File.separator + "ic_launcher.png");
		
		bitmapUtils.display(imageView, "assets/img/ic_launcher.png");
		
		
		// // 加载网络图片
		// bitmapUtils.display(testImageView,
		// "http://bbs.lidroid.com/static/image/common/logo.png");
		//
		// // 加载本地图片(路径以/开头， 绝对路径)
		// bitmapUtils.display(testImageView, "/sdcard/test.jpg");
		//
		// // 加载assets中的图片(路径以assets开头)
		// bitmapUtils.display(testImageView, "assets/img/wallpaper.jpg");

	}

}
