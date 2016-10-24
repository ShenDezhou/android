package com.qfsheldon.pulltorefreshdemo;

import java.util.ArrayList;
import java.util.List;

import com.qfsheldon.pulltorefreshdemo.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.qfsheldon.pulltorefreshdemo.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.qfsheldon.pulltorefreshdemo.pulltorefresh.PullToRefreshListView;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private PullToRefreshListView pullToRefreshListView = null;
	private List<String> list = null;
	private ListView listView = null;
	private ArrayAdapter<String> adapter = null;
	private SoundPool soundPool=null;
	private int soundID=0;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
			pullToRefreshListView.onRefreshComplete();
			//
			soundPool.play(soundID, 1f, 1f, 0, 0, 1);
		};
	};

	private void initView() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listView_main);
		listView = pullToRefreshListView.getRefreshableView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		soundPool=new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundID=soundPool.load(getApplicationContext(), R.raw.sina, 8);
		
		list = new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			list.add("这是第" + i + "条数据");
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, list);
		listView.setAdapter(adapter);

		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						list.clear();
						for (int i = 200; i < 400; i++) {
							list.add("这是第" + i + "条数据");
						}
						handler.sendMessage(Message.obtain());
					}
				}).start();

				
			}
		});
		pullToRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// TODO Auto-generated method stub
				List<String> list1=new ArrayList<String>();
				for (int i = 200; i < 400; i++) {
					list1.add("这是第" + i + "条数据");
				}
				list.addAll(list1);
				adapter.notifyDataSetChanged();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
