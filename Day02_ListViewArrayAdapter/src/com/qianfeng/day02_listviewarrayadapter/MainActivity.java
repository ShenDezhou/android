package com.qianfeng.day02_listviewarrayadapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView listView;
	private List<String>list=new ArrayList<String>();
	private ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        initData();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Object object = parent.getItemAtPosition(position);
//				Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
				
//				String str = adapter.getItem(position);
				
				String str = list.get(position);
				Intent intent=new Intent(MainActivity.this, MyActivity.class);
				intent.putExtra("Country", str);
				startActivity(intent);
			}
		});
        //如果长按点击事件onItemClick 和setOnItemLongClickListener都会响应 返回true 只让长按处理
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
				return true;
			}
		});
    }


    private void initData() {
		list.add("美国");
		list.add("中国");
		list.add("英国");
		list.add("法国");
		list.add("俄罗斯");
		list.add("印度");
		list.add("叙利亚");
		list.add("澳大利亚");
		list.add("马来西亚");
		list.add("埃及");
		list.add("菲律宾");
		list.add("越南");
		list.add("韩国");
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
