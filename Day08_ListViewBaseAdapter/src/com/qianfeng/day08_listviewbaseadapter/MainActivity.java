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
    //��ʼ��ListView������Դ
	private void initData() {
		String[] newsSource = {"������","�»���","�Ѻ���","������","�����ձ�","�����"};
		String[] newsTitle = {"ϰ��ƽ���÷���","����˹���������������","�Ʊ���������������","�����Ǿ�����Ȼ����","���������⵽�ֲ�����Ϯ��","���ݺ�����ǹڹھ�"};
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
