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
 * ListView�ķ�ҳ����
 * ListView���ͷ��ͼ��β����ͼ
 * ListView���ض���(�ö�)
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
        //��ʼ���ؼ�
        initView();
        
        //�����������ݣ�ͬʱˢ��ListView
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
		// ��listView����ͷ��ͼ��β��ͼ
		// ����1:��̬����view
	    // ����2��ͨ���������������䲼��
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.picture);
		//LayoutParams Ϊandroid.widget.AbsListView���µ�
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ScaleType.FIT_XY);
		
		View footerView = inflater.inflate(R.layout.footerview_listview, null);
		
		// �÷���������setAdapter֮ǰִ��
		listView.addHeaderView(imageView);
		listView.addFooterView(footerView);
		listView.setAdapter(adapter);
		listView.setEmptyView(textView_empty);
		// ʵ�ַ�ҳ.������Ļ����������
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
	//���ض���
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
