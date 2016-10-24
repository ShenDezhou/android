package com.rock.androidnetdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rock.androidnetdemo.adapter.ItemAdapter;
import com.rock.androidnetdemo.http.HttpUtil;
import com.rock.androidnetdemo.model.Model;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListViewActivity extends Activity implements OnClickListener {

	private static final String TAG = ListViewActivity.class.getSimpleName();
	
	private Button getData;
	
	private ListView mListView;
	
	private ItemAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		initView();
		setUpView();
	}
	
	private void initView(){
		//
		getData = (Button) this.findViewById(R.id.get_data);
		//
		getData.setOnClickListener(this);
		//
		mListView = (ListView) this.findViewById(R.id.mlistview);
		// 两个参数，第一个参数 Context对象， 第二个参数 数据源
		adapter = new ItemAdapter(this, null);
		//
		mListView.setAdapter(adapter);
	}
	
	private void setUpView(){
		getData();
	}

	private void getData(){
		new AsyncTask<String, Void, String>(){

			@Override
			protected String doInBackground(String... params) {
				HttpEntity entity = HttpUtil.getHttpClient(params[0]);
				try {
					String result = EntityUtils.toString(entity);
					return result;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e(TAG, "doInbackground--null");
				return null;
				
				
			}
			@Override
			protected void onPostExecute(String result) {
				Log.e(TAG, "result--" + result);
				if (result != null) {
					try {
						JSONArray jsonArray = new JSONObject(result).getJSONArray("data");
						
						List<Model> data = new ArrayList<Model>();
						// 遍历我们的JSONArray
						for (int i = 0; i < jsonArray.length() ;i++){
							JSONObject obj = jsonArray.getJSONObject(i);
							Model model = new Model();
							model.setUsername(obj.getString("username"));
							model.setId(obj.getString("id"));
							data.add(model);
						}
						
						adapter.addRes(data);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			
		}.execute("http://192.168.57.1:9999/user/list?currPage=1&pageSize=10");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.get_data:
			getData();
			break;

		}
		
	}
	
}
