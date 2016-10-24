package com.qianfeng.litchinewspager.asynctask;

import java.util.List;

import com.qianfeng.litchinewspager.adapter.MyAdapter;
import com.qianfeng.litchinewspager.bean.News;
import com.qianfeng.litchinewspager.helpr.HttpClientHelpr;
import com.qianfeng.litchinewspager.helpr.MyJsonUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<String, Void, List<News>> {

	private Context context;
	private List<News>list;
	private MyAdapter adapter;
	private int page=0;
	private ProgressDialog dialog;
	
	public MyAsyncTask(Context context, List<News>list,MyAdapter adapter, int page) {
		super();
		this.context = context;
		this.list = list;
		this.adapter = adapter;
		this.page = page;
		dialog=new ProgressDialog(context);
		dialog.setMessage("亲，正在努力加载中...");
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// 只有当加载第一页的时候才让ProgressDialog显示，否则不用显示。因为footerView中有提示加载的ProgressBar
		if (page==1) {
			dialog.show();
		}
	}

	@Override
	protected List<News> doInBackground(String... params) {
		String jsonString = HttpClientHelpr.loadTextFromURL(params[0]);
		if (jsonString!=null) {
			List<News> list = MyJsonUtils.jsonStringToList(jsonString);
			return list;
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<News> result) {
		super.onPostExecute(result);
		if (result!=null) {
			list.addAll(result);
			adapter.notifyDataSetChanged();
		}else {
			Toast.makeText(context, "网络异常，加载失败！", Toast.LENGTH_SHORT).show();
		}
		
		dialog.dismiss();
	}
	
	

	
	
}
