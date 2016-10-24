package com.qf.listviewdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qf.interf.ItemInterface;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView listView = null;
	private MyBaseAdapter myBaseAdapter = null;
	private List<ItemInterface> list = null;
	private Random random = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView);
		myBaseAdapter = new MyBaseAdapter(this);
		listView.setAdapter(myBaseAdapter);
		list = new ArrayList<ItemInterface>();
		random=new Random();
		for (int i = 0; i < 200; i++) {
			int n = random.nextInt(2);
			switch (n) {
			case 0:
				Po po=new Po();
				po.setTitle("这是第"+i+"条数据");
				list.add(po);
				break;
			case 1:
				Po1 po1=new Po1();
				po1.setTitle1("这是第"+i+"条数据");
				po1.setTitle2("这是第"+i+"条");
				list.add(po1);
				break;
			default:
				break;
			}
		}
		myBaseAdapter.setList(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
