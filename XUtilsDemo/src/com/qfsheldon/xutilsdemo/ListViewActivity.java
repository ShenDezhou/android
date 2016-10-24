package com.qfsheldon.xutilsdemo;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class ListViewActivity extends Activity {

	private BitmapUtils bitmapUtils = null;
	private ListView listView = null;

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
	}

	private MyAdapter myAdapter = null;

	private void initCtrl() {
		bitmapUtils = new BitmapUtils(this);
		myAdapter = new MyAdapter(this, bitmapUtils);

	}

	private List<String> list = null;

	private void initData() {
		list = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			list.add("https://www.baidu.com/img/bd_logo1.png");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		initView();
		initData();
		initCtrl();
		listView.setAdapter(myAdapter);
		myAdapter.setData(list);
		//这里bitmaputils会自己检测listview的滚动速度，来决定是否加载图片。
		//listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_view, menu);
		return true;
	}

}
