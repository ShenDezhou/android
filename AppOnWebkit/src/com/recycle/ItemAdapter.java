package com.recycle;

import java.util.ArrayList;
import java.util.List;

import com.net.model.ModelMeeting;
import com.tsingdata.zxingdemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
	// 数据源
	private List<ModelMeeting> data;
	// 布局导入器
	private LayoutInflater inflater;
	// 构造函数
	public ItemAdapter(Context context,List<ModelMeeting> data) {
		// 实例化布局导入器
		inflater = LayoutInflater.from(context);
		//
		if (data == null) {
			this.data = new ArrayList<ModelMeeting>();
		}else{
			this.data = data;
		}
	}
	// 用来添加新数据,一般用来首次填充数据，或是上拉加载
	public void addRes(List<ModelMeeting> data){
		// 新添加的数据源不是空的 添加
		if (data != null) {
			this.data.addAll(data);
			// 通知数据源更新
			notifyDataSetChanged();
		}
	}
	// 更新数据源
	public void updateRes(List<ModelMeeting> data){
		if (data != null) {
			// 首先清除以前的数据源
			this.data.clear();
			// 将新数据源添加进来
			this.data.addAll(data);
			// 通知适配器更新
			notifyDataSetChanged();
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data != null ? data.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View paramView, ViewGroup paramViewGroup) {
		ViewHolder holder = null;
		
		if (paramView == null) {
			
			paramView = inflater.inflate(R.layout.listview_item, null);
			
			holder = new ViewHolder();
			
			holder.name = (TextView) paramView.findViewById(R.id.name);
			
			//holder.pic = (ImageView) paramView.findViewById(R.id.pic);
			
			paramView.setTag(holder);
		}else{
			holder = (ViewHolder) paramView.getTag();
		}
		
		holder.name.setText(data.get(position).getUsername());
		
		return paramView;
	}
	
	private static class ViewHolder{
		
		TextView name;
		
		ImageView pic;
		
	}

}
