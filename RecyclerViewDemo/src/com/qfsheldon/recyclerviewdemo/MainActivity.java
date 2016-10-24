package com.qfsheldon.recyclerviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RecyclerView recyclerView = null;
	private MyAdapter myAdapter = null;
	private List<String> list = null;

	private void initData() {
		list = new ArrayList<String>();
		for (int i = 0; i < 300; i++) {
			list.add("这是第" + i + "条数据");
		}
	}

	private void initCtrl() {
		myAdapter = new MyAdapter(this);
	}

	private void initView() {
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		LayoutManager layoutManager = new LinearLayoutManager(this);
//		LayoutManager layoutManager = new GridLayoutManager(this, 3);
		recyclerView.setLayoutManager(layoutManager);
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
			public void onClick(int position) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), list.get(position),Toast.LENGTH_SHORT).show();
//				myAdapter.removeItem(position);
				myAdapter.addItem(position);
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
