package com.qf.recycleviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RecyclerView recyclerView = null;
	private MyAdapter myAdapter = null;

	private ListView listView=null;
	private List<String> list = null;

	private void initData() {
		list = new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			list.add("这是第" + i + "条");
		}
	}

	private void initCtrl() {
		myAdapter = new MyAdapter(this);
	}

	private void initView() {
		recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		MyItemAnimator myItemAnimator=new MyItemAnimator();
		myItemAnimator.setRemoveDuration(200);
		recyclerView.setItemAnimator(myItemAnimator);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initCtrl();

		recyclerView.setAdapter(myAdapter);
		myAdapter.setList(list);
		myAdapter.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public void onChildChickListener(View view, int position,
					ViewHolder viewHolder) {
				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "" + position,
//						Toast.LENGTH_SHORT).show();
				myAdapter.removeItem(position);
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
