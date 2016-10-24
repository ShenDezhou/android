package com.qianfeng.day08_listviewbaseadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listView;
	private MyAdapter adapter;
	private List<Map<String, Object>>list = new ArrayList<Map<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        adapter=new MyAdapter(list, this);
        listView.setAdapter(adapter);
        initData();
        
    }
    //初始化ListView的数据源
	private void initData() {
		String[] newsSource = {"人民网","新华网","搜狐网","新浪网","环球日报","凤凰网"};
		String[] newsTitle = {"习近平出访非洲","俄罗斯军机被土耳其击落","科比宣布下赛季退役","叙利亚局势依然紧张","巴黎市区遭到恐怖分子袭击","广州恒大夺得亚冠冠军"};
		String[] newsTime = {"2015-12-10 10:30","2015-12-10 10:30","2015-12-10 11:30","2015-12-10 10:34","2015-12-10 10:12","2015-12-10 12:40"};
		int[] newsPicture = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6};
		
		for (int i = 0; i < newsPicture.length; i++) {
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("source",newsSource[i]);
			map.put("title", newsTitle[i]);
			map.put("time", newsTime[i]);
			map.put("imageId", newsPicture[i]);
			list.add(map);
		}
		
	}


}
