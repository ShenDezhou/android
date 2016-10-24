package com.qianfeng.day08_listviewbaseadapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 自定义适配器
 * 自定义类继承BaseAdapter 必须重写其中四个方法
 *
 */
public class MyAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;

	public MyAdapter(List<Map<String, Object>> list, Context context) {
		super();
		this.list = list;
		this.context=context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
			holder.iv_picture=(ImageView) convertView.findViewById(R.id.picture);
			holder.tv_title=(TextView) convertView.findViewById(R.id.title);
			holder.tv_source=(TextView) convertView.findViewById(R.id.source);		
			holder.tv_time=(TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		//设置数据
		Map<String,Object> map = list.get(position);
		holder.tv_title.setText((String) map.get("title"));
		holder.iv_picture.setImageResource((Integer) map.get("imageId"));
		holder.tv_source.setText((String) map.get("source"));
		holder.tv_time.setText((String) map.get("time"));
		
		return convertView;
	}
	// 定义存储自定义控件的引用类
	class ViewHolder{
		private ImageView iv_picture;
		private TextView tv_title;
		private TextView tv_source;
		private TextView tv_time;
	}

}
