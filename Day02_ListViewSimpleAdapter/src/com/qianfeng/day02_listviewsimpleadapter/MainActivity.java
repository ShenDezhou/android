package com.qianfeng.day02_listviewsimpleadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private ListView listView;
	private SimpleAdapter adapter;
	private List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	private int[] imagesId = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g};
	private String [] name = {"汉风西游","怪物猎人","圣斗士星矢","暗黑金刚","天天酷跑","天天妞妞","功夫熊猫"};
	private String [] count = {"1238万次","1438万次","238万次","838万次","1118万次","1738万次","938万次",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.listView);
        adapter=new SimpleAdapter(MainActivity.this, list, R.layout.item_layout,new String[]{"ImagesId","Name","Count"} , new int[]{R.id.imageView_image,R.id.textView_name,R.id.textView_count});
        listView.setAdapter(adapter);
        
        //初始化数据源
        initData();
    }


    private void initData() {
		for (int i = 0; i < imagesId.length; i++) {
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("ImagesId", imagesId[i]);
			map.put("Name", name[i]);
			map.put("Count", count[i]);
			list.add(map);
		}
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
