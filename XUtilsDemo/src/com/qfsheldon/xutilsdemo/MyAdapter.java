package com.qfsheldon.xutilsdemo;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {

	private BitmapUtils bitmapUtils = null;
	List<String> list = null;
	Context context = null;

	
	
	public void setData(List<String> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public MyAdapter(Context context,BitmapUtils bitmapUtils) {
		this.context = context;
		list = new ArrayList<String>();
		this.bitmapUtils=bitmapUtils;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_listview, parent, false);
		}
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.imageView_item);
		imageView.setImageResource(R.drawable.ic_launcher);
		// 请求网络
		bitmapUtils.display(imageView, list.get(position));

		return convertView;
	}

}
