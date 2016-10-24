package com.qf.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import com.qf.interf.ItemInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter {

	private List<ItemInterface> list = null;

	private Context context = null;
	
	
	public MyBaseAdapter(Context context){
		this.context=context;
		this.list=new ArrayList<ItemInterface>();
	}
	
	public void setList(List<ItemInterface> list){
		this.list=list;
		notifyDataSetChanged();
	}
	public void addList(List<ItemInterface> list){
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	
	

	// 获取每个item的view的类型
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		int n = list.get(position).getItemType();

		// 0~(n-1)
		// n 数组越界异常
		return n - 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int itemType = getItemViewType(position);
		ViewHolder1 viewHolder1 = null;
		ViewHolder2 viewHolder2 = null;
		// 创建view
		if (convertView == null) {
			switch (itemType) {
			case 0:
				viewHolder1 = new ViewHolder1();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item1, parent, false);
				viewHolder1.textView1 = (TextView) convertView
						.findViewById(R.id.textView_item1);
				convertView.setTag(viewHolder1);
				break;
			case 1:
				viewHolder2 = new ViewHolder2();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item2, parent, false);
				viewHolder2.textView1 = (TextView) convertView
						.findViewById(R.id.textView1_item2);
				viewHolder2.textView2 = (TextView) convertView
						.findViewById(R.id.textView2_item2);
				convertView.setTag(viewHolder2);
				break;
			default:
				break;
			}

		} else {
			switch (itemType) {
			case 0:
				viewHolder1 = (ViewHolder1) convertView.getTag();
				break;
			case 1:
				viewHolder2 = (ViewHolder2) convertView.getTag();
				break;
			default:
				break;
			}
		}

		// 设置数据
		switch (itemType) {
		case 0:
			viewHolder1.textView1.setText(((Po) list.get(position)).getTitle());
			break;
		case 1:
			viewHolder2.textView1.setText(((Po1) list.get(position))
					.getTitle1());
			viewHolder2.textView2.setText(((Po1) list.get(position))
					.getTitle2());
			break;

		default:
			break;
		}

		return convertView;
	}

	class ViewHolder1 {
		TextView textView1 = null;
	}

	class ViewHolder2 {
		TextView textView1 = null;
		TextView textView2 = null;
	}

}
