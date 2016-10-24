package com.qianfeng.litchinewspager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.litchinewspager.adapter.MyAdapter;
import com.qianfeng.litchinewspager.asynctask.MyAsyncTask;
import com.qianfeng.litchinewspager.bean.News;

/**
 * ListView的分页加载
 * ListView添加头视图和尾部视图
 * ListView返回顶部(置顶)
 */
public class MainActivity extends Activity {
	private String urlString = "http://litchiapi.jstv.com/api/GetFeeds?column=0&PageSize=10&pageIndex=";
	private ListView listView;
	private List<News>list = new ArrayList<News>();
	private MyAdapter adapter;
	private int page = 1;
	private boolean isBottom = false;
	private LayoutInflater inflater;
	private TextView textView_empty;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        
        //加载网络数据，同时刷新ListView
        loadNetWorkData();
        
    }

	private void loadNetWorkData() {
		new MyAsyncTask(this, list, adapter, page).execute(urlString+page);
	}

	private void initView() {
		inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		listView=(ListView) findViewById(R.id.listView);
		textView_empty=(TextView) findViewById(R.id.textView_empty);
		
		adapter=new MyAdapter(MainActivity.this, list);
		// 给listView设置头视图和尾视图
		// 做法1:动态创建view
	    // 做法2：通过布局填充器泵填充布局
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.picture);
		//LayoutParams 为android.widget.AbsListView包下的
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ScaleType.FIT_XY);
		
		View footerView = inflater.inflate(R.layout.footerview_listview, null);
		
		// 该方法必须在setAdapter之前执行
		listView.addHeaderView(imageView);
		listView.addFooterView(footerView);
		listView.setAdapter(adapter);
		listView.setEmptyView(textView_empty);
		// 实现分页.利用屏幕滚动监听器
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (isBottom && scrollState == SCROLL_STATE_IDLE) {
					page++;
					loadNetWorkData();
					
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				isBottom = (firstVisibleItem + visibleItemCount == totalItemCount);
			}
		});
		
	}
	//返回顶部
	public void clickButton(View view){
		switch (view.getId()) {
		case R.id.toTop:
			listView.setSelection(0);
			break;

		default:
			break;
		}
	}

    
}
